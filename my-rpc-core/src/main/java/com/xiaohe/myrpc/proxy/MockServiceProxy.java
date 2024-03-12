package com.xiaohe.myrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mock 服务代理（JDK 动态代理）
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型，生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象（可自行完善默认值逻辑）
     *
     * @param type
     * @return
     */
    private Object getDefaultObject(Class<?> type) {
        // 基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            } else if (type == short.class) {
                return (short) 0;
            } else if (type == int.class) {
                return 0;
            } else if (type == long.class) {
                return 0L;
            }
        }
        // 对象类型
        return null;
    }

    /*public static void main(String[] args) {
        Faker faker = new Faker(Locale.CHINA);

        // 生成姓名
        String name = faker.name().fullName();
        System.out.println("姓名：" + name);

        // 生成地址
        String address = faker.address().fullAddress();
        System.out.println("地址：" + address);

        // 生成电子邮件
        String email = faker.internet().emailAddress();
        System.out.println("电子邮件：" + email);

        // 生成电话号码
        String phoneNumber = faker.phoneNumber().phoneNumber();
        System.out.println("电话号码：" + phoneNumber);

        // 生成日期
        String date = faker.date().birthday().toString();
        System.out.println("日期：" + date);

    }*/
}
