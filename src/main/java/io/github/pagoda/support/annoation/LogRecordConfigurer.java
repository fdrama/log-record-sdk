package io.github.pagoda.support.annoation;

import io.github.pagoda.support.config.LogRecordErrorHandler;
import io.github.pagoda.support.config.LogRecordResolver;
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
