package com.xiaohe.myrpc.bootstrap;

import com.xiaohe.myrpc.RpcApplication;
import com.xiaohe.myrpc.config.RegistryConfig;
import com.xiaohe.myrpc.config.RpcConfig;
import com.xiaohe.myrpc.model.ServiceMetaInfo;
import com.xiaohe.myrpc.model.ServiceRegisterInfo;
import com.xiaohe.myrpc.registry.LocalRegistry;
import com.xiaohe.myrpc.registry.Registry;
import com.xiaohe.myrpc.registry.RegistryFactory;
import com.xiaohe.myrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {

    /**
     * 初始化
     *
     * @param serviceRegisterInfoList
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化
        RpcApplication.init();
        // RPC 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            Class<?> implClass = serviceRegisterInfo.getImplClass();
            // 本地注册
            LocalRegistry.register(serviceName, implClass);

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
