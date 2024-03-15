package com.xiaohe.example.provider;

import com.xiaohe.example.common.service.UserService;
import com.xiaohe.myrpc.RpcApplication;
import com.xiaohe.myrpc.config.RegistryConfig;
import com.xiaohe.myrpc.config.RpcConfig;
import com.xiaohe.myrpc.model.ServiceMetaInfo;
import com.xiaohe.myrpc.registry.LocalRegistry;
import com.xiaohe.myrpc.registry.Registry;
import com.xiaohe.myrpc.registry.RegistryFactory;
import com.xiaohe.myrpc.server.tcp.VertxTcpServer;

/**
 * 简易服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
