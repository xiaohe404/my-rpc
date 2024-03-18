package com.xiaohe.myrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.xiaohe.myrpc.RpcApplication;
import com.xiaohe.myrpc.config.RpcConfig;
import com.xiaohe.myrpc.constant.RpcConstant;
import com.xiaohe.myrpc.loadbalancer.LoadBalancer;
import com.xiaohe.myrpc.loadbalancer.LoadBalancerFactory;
import com.xiaohe.myrpc.model.RpcRequest;
import com.xiaohe.myrpc.model.RpcResponse;
import com.xiaohe.myrpc.model.ServiceMetaInfo;
import com.xiaohe.myrpc.registry.Registry;
import com.xiaohe.myrpc.registry.RegistryFactory;
import com.xiaohe.myrpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList =
                    registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            Map<String, Object> requestMap = new HashMap<>();
            // 将调用方法名（请求路径）作为负载均衡参数
            requestMap.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestMap, serviceMetaInfoList);

            // 发送 TCP 请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }
    }
}
