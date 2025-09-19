package pt.rafap.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory

abstract class WithLogger {
    protected val logger: Logger = LoggerFactory.getLogger(this::class.java.simpleName) as Logger

    open fun setLogLevel(level: Level) {
        logger.level = level
    }
}
