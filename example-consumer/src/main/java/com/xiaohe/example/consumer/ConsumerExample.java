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
        RpcConfig rpc2 = ConfigUtils.loadConfigYml(RpcConfig.class, "rpc");
        System.out.println(rpc);
        System.out.println(rpc2);

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("xiaohe");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
        short number = userService.getNumber();
        System.out.println(number);
    }
}
