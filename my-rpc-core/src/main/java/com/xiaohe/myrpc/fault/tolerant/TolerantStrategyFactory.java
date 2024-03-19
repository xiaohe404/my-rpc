package com.xiaohe.myrpc.fault.tolerant;

import com.xiaohe.myrpc.fault.retry.NoRetryStrategy;
import com.xiaohe.myrpc.fault.retry.RetryStrategy;
import com.xiaohe.myrpc.spi.SpiLoader;

/**
 * 容错策略工厂（用于获取容错策略对象）
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
