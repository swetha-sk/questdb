/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2018 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.questdb.griffin.engine.table;

import com.questdb.cairo.map2.DirectMap;
import com.questdb.cairo.map2.RecordSink;
import com.questdb.cairo.sql.DataFrame;
import com.questdb.cairo.sql.DataFrameCursor;
import com.questdb.cairo.sql.Record;
import com.questdb.griffin.engine.LongTreeSet;
import com.questdb.std.Rows;

class LatestBySansIndexRecordCursor extends AbstractDataFrameRecordCursor {

    private final DirectMap map;
    private final RecordSink recordSink;
    private final LongTreeSet treeSet;
    private LongTreeSet.TreeCursor treeCursor;

    public LatestBySansIndexRecordCursor(DirectMap map, LongTreeSet treeSet, RecordSink recordSink) {
        this.map = map;
        this.treeSet = treeSet;
        this.recordSink = recordSink;
    }

    @Override
    public void close() {
        treeCursor = null;
        dataFrameCursor.close();
    }

    @Override
    public boolean hasNext() {
        return treeCursor.hasNext();
    }

    @Override
    public Record next() {
        long row = treeCursor.next();
        record.jumpTo(Rows.toPartitionIndex(row), Rows.toLocalRowID(row));
        return record;
    }

    @Override
    public void toTop() {
        treeCursor.toTop();
    }

    private void buildTreeMap() {
        treeSet.clear();
        map.clear();

        while (this.dataFrameCursor.hasNext()) {
            final DataFrame frame = this.dataFrameCursor.next();
            final int partitionIndex = frame.getPartitionIndex();
            final long rowLo = frame.getRowLo();
            final long rowHi = frame.getRowHi() - 1;

            record.jumpTo(frame.getPartitionIndex(), rowHi);
            for (long row = rowHi; row >= rowLo; row--) {
                record.setRecordIndex(row);
                map.withKey().putRecord(record, recordSink);
                if (map.createKey()) {
                    treeSet.put(Rows.toRowID(partitionIndex, row));
                }
            }
        }

        map.clear();
        this.treeCursor = treeSet.getCursor();
    }

    void of(DataFrameCursor dataFrameCursor) {
        this.dataFrameCursor = dataFrameCursor;
        this.record.of(dataFrameCursor.getTableReader());
        buildTreeMap();
    }
}