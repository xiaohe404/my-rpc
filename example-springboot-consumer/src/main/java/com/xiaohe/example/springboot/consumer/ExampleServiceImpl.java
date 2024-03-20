package com.xiaohe.example.springboot.consumer;

import com.xiaohe.example.common.model.User;
import com.xiaohe.example.common.service.UserService;
import com.xiaohe.myrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("xiaohe");
        User newUser = userService.getUser(user);
        System.out.println(newUser.getName());
    }
}
