/*
 * Copyright 2013-2019 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.github.zj.dreamly.im.coder;

import com.github.zj.dreamly.im.model.SentBody;
import com.github.zj.dreamly.im.model.proto.SentBodyProto;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebMessageDecoder extends SimpleChannelInboundHandler<Object> {

	private final static String URI = "ws://localhost:%d";

	private static final ConcurrentHashMap<String, WebSocketServerHandshaker> handShakerMap = new ConcurrentHashMap<>();

	private static final Logger LOGGER = LoggerFactory.getLogger(WebMessageDecoder.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws InvalidProtocolBufferException {

		if (msg instanceof FullHttpRequest) {
			try {
				handleHandshakeRequest(ctx, (FullHttpRequest) msg);
			}catch (Exception e){
				log.error("illegal Request, the error message: {}", e.getMessage());
			}

		}
		if (msg instanceof WebSocketFrame) {
			try {
				handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
			}catch (Exception e){
				log.error("illegal Request, the error message: {}", e.getMessage());
			}

		}
	}

	private void handleHandshakeRequest(ChannelHandlerContext ctx, FullHttpRequest req) {

		int port = ((InetSocketAddress) ctx.channel().localAddress()).getPort();

		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(String.format(URI, port), null, false);

		WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(req);

		handShakerMap.put(ctx.channel().id().asLongText(), handShaker);

		try {
			handShaker.handshake(ctx.channel(), req);
		}catch (Exception e){
			log.error("illegal Request, the error message: {}", e.getMessage());
		}

	}

	private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws InvalidProtocolBufferException {

		if (frame instanceof CloseWebSocketFrame) {
			handlerCloseWebSocketFrame(ctx, (CloseWebSocketFrame) frame);
			return;
		}
		if (frame instanceof PingWebSocketFrame) {
			handlerPingWebSocketFrame(ctx, (PingWebSocketFrame) frame);
			return;
		}

		handlerBinaryWebSocketFrame(ctx, (BinaryWebSocketFrame) frame);
	}

	private void handlerCloseWebSocketFrame(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
		WebSocketServerHandshaker handShaker = handShakerMap.get(ctx.channel().id().asLongText());
		handShaker.close(ctx.channel(), frame.retain());
	}

	private void handlerPingWebSocketFrame(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
		ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
	}

	private void handlerBinaryWebSocketFrame(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) throws InvalidProtocolBufferException {
		byte[] data = new byte[frame.content().readableBytes()];
		frame.content().readBytes(data);

		SentBodyProto.Model bodyProto = SentBodyProto.Model.parseFrom(data);
		SentBody body = new SentBody();
		body.setKey(bodyProto.getKey());
		body.setTimestamp(bodyProto.getTimestamp());
		body.putAll(bodyProto.getDataMap());
		ctx.fireChannelRead(body);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
		LOGGER.warn("Exception caught", cause);
	}
}
