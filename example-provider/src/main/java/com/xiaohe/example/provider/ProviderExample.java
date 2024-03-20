package com.xiaohe.example.provider;

import com.xiaohe.example.common.service.UserService;
import com.xiaohe.myrpc.bootstrap.ProviderBootstrap;
import com.xiaohe.myrpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 简易服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {
        // 注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<?> serviceRegisterInfo =
                new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
