package com.example.retrospect.keycloak.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

public class MyFatelExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyFatelExceptionStrategy.class);

    @Override
    public boolean isFatal(Throwable t) {
        if (t instanceof ListenerExecutionFailedException) {
            ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
            LOGGER.error("Failed to process inbound message from queue "
                    + lefe.getFailedMessage().getMessageProperties().getConsumerQueue() + "; failed message: "
                    + lefe.getFailedMessage(), t);
        }
        return true;
    }

}

