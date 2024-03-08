package com.xiaohe.example.provider;

import com.xiaohe.example.common.service.UserService;
import com.xiaohe.myrpc.RpcApplication;
import com.xiaohe.myrpc.registry.LocalRegistry;
import com.xiaohe.myrpc.server.HttpServer;
import com.xiaohe.myrpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
