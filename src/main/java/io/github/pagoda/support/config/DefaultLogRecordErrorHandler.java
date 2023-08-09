package io.github.pagoda.support.config;

import io.github.pagoda.support.aop.LogRecordOperationInvocationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author fdrama
 * date 2023年07月26日 17:25
 */
public class DefaultLogRecordErrorHandler implements LogRecordErrorHandler {

    private final Log logger = LogFactory.getLog(DefaultLogRecordErrorHandler.class);

    @Override
    public void handleLogRecordError(LogRecordOperationInvocationContext<?> context, RuntimeException exception) {
        logger.error("failed to record log for " + context.getMethod().getName() + " invocation", exception);
        throw exception;
    }
}
