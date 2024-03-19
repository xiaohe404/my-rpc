package com.xiaohe.myrpc.fault.tolerant;

import cn.hutool.core.collection.CollUtil;
import com.xiaohe.myrpc.RpcApplication;
import com.xiaohe.myrpc.loadbalancer.LoadBalancer;
import com.xiaohe.myrpc.loadbalancer.LoadBalancerFactory;
import com.xiaohe.myrpc.model.RpcRequest;
import com.xiaohe.myrpc.model.RpcResponse;
import com.xiaohe.myrpc.model.ServiceMetaInfo;
import com.xiaohe.myrpc.server.tcp.VertxTcpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 故障转移 - 容错策略（服务节点转移）
 */
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        if (context.isEmpty()) {
            return null;
        }
        ServiceMetaInfo currentServiceMetaInfo = (ServiceMetaInfo) context.get("currentServiceMetaInfo");
        List<ServiceMetaInfo> serviceMetaInfoList =
                (List<ServiceMetaInfo>) context.get("serviceMetaInfoList");
        // 去除错误节点
        serviceMetaInfoList.remove(currentServiceMetaInfo);
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }
        // 再次调用
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");
        try {
            // 负载均衡
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("methodName", rpcRequest.getMethodName());
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(
                    RpcApplication.getRpcConfig().getLoadBalancer());
            ServiceMetaInfo newServiceMetaInfo = loadBalancer.select(requestMap, serviceMetaInfoList);
            // 发送请求
            return VertxTcpClient.doRequest(rpcRequest, newServiceMetaInfo);
        } catch (Exception ex) {
            throw new RuntimeException("调用失败");
        }
    }
}
