package io.github.pagoda.support.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author fdrama
 * date 2023年07月26日 17:27
 */
public class DefaultLogRecordResolver implements LogRecordResolver {

    private final Log logger = LogFactory.getLog(DefaultLogRecordResolver.class);

    @Override
    public void resolveLog(LogRecordDetail recordDetail) {
        logger.info(recordDetail);
    }
}
