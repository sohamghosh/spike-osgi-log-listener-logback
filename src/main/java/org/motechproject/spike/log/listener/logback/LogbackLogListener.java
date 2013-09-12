package org.motechproject.spike.log.listener.logback;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogbackLogListener implements LogListener {

    @Override
    public void logged(LogEntry entry) {
        final String symbolicName = entry.getBundle().getSymbolicName();
        if (!"spike-log-service-test".equals(symbolicName)) {
            return;
        }
        // Cache
        Logger logger = LoggerFactory.getLogger(symbolicName);

        switch (entry.getLevel()) {
            case LogService.LOG_DEBUG:
                logger.debug(entry.getMessage());
                break;
            case LogService.LOG_INFO:
                logger.info(entry.getMessage());
                break;
            case LogService.LOG_WARNING:
                logger.warn(entry.getMessage());
                break;
            case LogService.LOG_ERROR:
                logger.error(entry.getMessage());
                break;
        }
    }

    @Autowired
    public void setLogReaderService(LogReaderService logReaderService) {
        logReaderService.addLogListener(this);
    }
}
