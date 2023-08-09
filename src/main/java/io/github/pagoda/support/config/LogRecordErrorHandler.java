package io.github.pagoda.support.config;

import io.github.pagoda.support.aop.LogRecordOperationInvocationContext;

/**
 * @author fdrama
 * date 2023年07月26日 17:18
 */
public interface LogRecordErrorHandler {

    /**
     * 处理日志记录错误
     * @param context
     * @param exception
     */
    void handleLogRecordError(LogRecordOperationInvocationContext<?> context, RuntimeException exception);
}
