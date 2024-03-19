package com.xiaohe.myrpc.fault.tolerant;

import com.xiaohe.myrpc.model.RpcResponse;

import java.util.Map;

/**
 * 故障恢复 - 容错策略（服务降级，Mock）
 */
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 暂未实现本地服务 Mock
        return null;
    }
}
