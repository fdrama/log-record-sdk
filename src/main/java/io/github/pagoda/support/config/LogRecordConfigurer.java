package io.github.pagoda.support.config;

import org.springframework.lang.Nullable;

/**
 * @author fdrama
 * date 2023年07月26日 17:34
 */
public interface LogRecordConfigurer {


    @Nullable
    default LogRecordResolver logResolver() {
        return null;
    }


    @Nullable
    default LogRecordErrorHandler errorHandler() {
        return null;
    }
}
