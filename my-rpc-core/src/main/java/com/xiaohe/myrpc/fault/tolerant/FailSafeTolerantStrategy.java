package com.xiaohe.myrpc.fault.tolerant;

import com.xiaohe.myrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 静默处理 - 容错策略（忽略异常，记录日志后正常返回）
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理", e);
        return new RpcResponse();
    }
}
