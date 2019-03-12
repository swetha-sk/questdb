/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2019 Appsicle
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

package com.questdb.std;

public class NetworkFacadeImpl implements NetworkFacade {
    public static final NetworkFacade INSTANCE = new NetworkFacadeImpl();

    @Override
    public void abortAccept(long fd) {
        Net.abortAccept(fd);
    }

    @Override
    public long accept(long serverFd) {
        return Net.accept(serverFd);
    }

    @Override
    public boolean bindTcp(long fd, int address, int port) {
        return Net.bindTcp(fd, address, port);
    }

    @Override
    public boolean bindTcp(long fd, CharSequence ipv4Address, int port) {
        return Net.bindTcp(fd, ipv4Address, port);
    }

    @Override
    public int close(long fd) {
        return Net.close(fd);
    }

    @Override
    public void configureNoLinger(long fd) {
        Net.configureNoLinger(fd);
    }

    @Override
    public int configureNonBlocking(long fd) {
        return Net.configureNonBlocking(fd);
    }

    @Override
    public int connect(long fd, long sockaddr) {
        return Net.connect(fd, sockaddr);
    }

    @Override
    public void freeSockAddr(long socketAddress) {
        Net.freeSockAddr(socketAddress);
    }

    @Override
    public long getPeerIP(long fd) {
        return Net.getPeerIP(fd);
    }

    @Override
    public void listen(long serverFd, int backlog) {
        Net.listen(serverFd, backlog);
    }

    @Override
    public int recv(long fd, long buffer, int bufferLen) {
        return Net.recv(fd, buffer, bufferLen);
    }

    @Override
    public int send(long fd, long buffer, int bufferLen) {
        return Net.send(fd, buffer, bufferLen);
    }

    @Override
    public int errno() {
        return Os.errno();
    }

    @Override
    public long sockaddr(int address, int port) {
        return Net.sockaddr(address, port);
    }

    @Override
    public int sendTo(long fd, long ptr, int len, long socketAddress) {
        return Net.sendTo(fd, ptr, len, socketAddress);
    }

    @Override
    public long socketTcp(boolean blocking) {
        return Net.socketTcp(blocking);
    }

    @Override
    public long socketUdp() {
        return Net.socketUdp();
    }

    @Override
    public boolean bindUdp(long fd, int port) {
        return Net.bindUdp(fd, 0, port);
    }

    @Override
    public boolean join(long fd, CharSequence bindIPv4Address, CharSequence groupIPv4Address) {
        return Net.join(fd, bindIPv4Address, bindIPv4Address);
    }

    @Override
    public boolean join(long fd, int bindIPv4, int groupIPv4) {
        return Net.join(fd, bindIPv4, bindIPv4);
    }

    @Override
    public long sockaddr(CharSequence address, int port) {
        return Net.sockaddr(address, port);
    }

    @Override
    public int setMulticastInterface(long fd, CharSequence address) {
        return Net.setMulticastInterface(fd, Net.parseIPv4(address));
    }

    @Override
    public int setMulticastInterface(long fd, int ipv4Address) {
        return Net.setMulticastInterface(fd, ipv4Address);
    }

    @Override
    public int setMulticastLoop(long fd, boolean loop) {
        return Net.setMulticastLoop(fd, loop);
    }

    @Override
    public int parseIPv4(CharSequence ipv4Address) {
        return Net.parseIPv4(ipv4Address);
    }

    @Override
    public int setReusePort(long fd) {
        return Net.setReusePort(fd);
    }

    @Override
    public int setTcpNoDelay(long fd, boolean noDelay) {
        return Net.setTcpNoDelay(fd, noDelay);
    }
}