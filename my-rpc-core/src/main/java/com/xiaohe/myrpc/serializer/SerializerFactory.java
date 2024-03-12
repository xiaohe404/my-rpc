package com.xiaohe.myrpc.serializer;

import com.xiaohe.myrpc.spi.SpiLoader;

import java.util.Map;

/**
 * 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {

    // 引入volatile关键字，防止指令重排
    private volatile static Serializer serializer;

    private SerializerFactory() {
    }

    public static Serializer getInstance(String key) {
        // 双检锁模式
        if (serializer == null) {
            synchronized (Serializer.class) {
                if (serializer == null) {
                    SpiLoader.load(Serializer.class);
                    serializer = SpiLoader.getInstance(Serializer.class, key);
                }
            }
        }
        return serializer;
    }

//    static {
//        SpiLoader.load(Serializer.class);
//    }
//
//    /**
//     * 默认序列化器
//     */
//    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();
//
//    /**
//     * 获取实例
//     *
//     * @param key
//     * @return
//     */
//    public static Serializer getInstance(String key) {
//        return SpiLoader.getInstance(Serializer.class, key);
//    }

}
