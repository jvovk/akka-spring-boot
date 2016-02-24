package com.aggregator.kafka;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:32
 */
public interface IConsumer<T> {

    void submitMessage(T message);
}
