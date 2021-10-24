package com.company.logger;

import lombok.Getter;

import java.util.logging.Logger;

public class LoggerUtil {

    @Getter private static final Logger logger = Logger.getLogger("global");

}
