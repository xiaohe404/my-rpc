package com.xiaohe.myrpc.server.tcp;

import io.vertx.core.Handler;
import io.vertx.core.net.NetSocket;

/**
 * TCP 请求处理器
 */
public class TcpServerHandler implements Handler<NetSocket> {

    /**
     * 处理请求
     *
     * @param socket
     */
    @Override
    public void handle(NetSocket socket) {
        TcpBufferHandlerWrapper bufferHandlerWrapper =
                new TcpBufferHandlerWrapper(buffer -> {
            // 处理请求代码

        });
        socket.handler(bufferHandlerWrapper);
    }
}
