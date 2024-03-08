package com.xiaohe.example.consumer;

import com.xiaohe.example.common.model.User;
import com.xiaohe.example.common.service.UserService;
import com.xiaohe.myrpc.config.RpcConfig;
import com.xiaohe.myrpc.proxy.ServiceProxyFactory;
import com.xiaohe.myrpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);

    }
}
