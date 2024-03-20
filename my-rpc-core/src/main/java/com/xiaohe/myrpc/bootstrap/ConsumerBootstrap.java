package com.xiaohe.myrpc.bootstrap;

import com.xiaohe.myrpc.RpcApplication;

/**
 * 服务消费者初始化
 */
public class ConsumerBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化
        RpcApplication.init();
    }
}
