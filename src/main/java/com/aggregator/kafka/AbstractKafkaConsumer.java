package com.aggregator.kafka;

import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:32
 */
public abstract class AbstractKafkaConsumer<T> implements IConsumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractKafkaConsumer.class);

    @Async("notificationsExecutor")
    public void subscribe(KafkaStream a_stream, int a_threadNumber) {
        Thread.currentThread().setName("kafka-consumer-" + Thread.currentThread().getName());
        LOGGER.info("Kafka consumer started, thread {} ", a_threadNumber);
        for (MessageAndMetadata<String, T> anA_stream : (Iterable<MessageAndMetadata<String, T>>) a_stream) {
            T message = anA_stream.message();
            LOGGER.debug("Message arrived -> 'thread_name': {}, 'thread_number': {}, 'message': {}", Thread.currentThread().getName(), a_threadNumber, message);
            submitMessage(message);
        }
        LOGGER.info("Shutting down Thread: " + a_threadNumber);
    }

}
