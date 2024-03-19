package com.xiaohe.myrpc.config;

import com.xiaohe.myrpc.fault.retry.RetryStrategy;
import com.xiaohe.myrpc.fault.retry.RetryStrategyKeys;
import com.xiaohe.myrpc.fault.tolerant.TolerantStrategy;
import com.xiaohe.myrpc.fault.tolerant.TolerantStrategyKeys;
import com.xiaohe.myrpc.loadbalancer.LoadBalancerKeys;
import com.xiaohe.myrpc.registry.EtcdRegistry;
import com.xiaohe.myrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "my-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;

    /**
     * 容错策略
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

}
