/*
 * Copyright (c) 2014 The APN-PROXY Project
 *
 * The APN-PROXY Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.xx_dev.apn.socks.local;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import javax.net.ssl.SSLException;

public class PortForwardProxyBackendInitializer extends ChannelInitializer<SocketChannel> {
    private final Channel inboundChannel;

    public PortForwardProxyBackendInitializer(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    @Override
    public void initChannel(SocketChannel ch) throws SSLException {
        ch.pipeline().addLast(new FakeHttpClientDecoder());
        ch.pipeline().addLast(new FakeHttpClientEncoder());

        ch.pipeline().addLast(new PortForwardProxyBackendHandler(inboundChannel));
    }
}
