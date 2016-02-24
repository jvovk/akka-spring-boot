package com.aggregator.configuration;

import com.aggregator.converters.WeatherAvgNotificationConverter;
import com.aggregator.converters.WeatherNotificationConverter;
import com.aggregator.kafka.*;
import com.aggregator.model.WeatherAverageNotification;
import com.aggregator.model.WeatherNotification;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.util.*;
import java.util.function.Supplier;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:12
 */
@Configuration
public class KafkaConfig {

    @Autowired
    private Environment env;

    @Value("${threads.count:1}")
    private int threadCount;

    @Value("${metadata.broker.list}")
    private String brokerList;

    @Value("${zookeeper.connect}")
    private String zookeeperConnect;

    @Value("${weather.not.serializer.class}")
    private String weatherNotSerializer;

    @Value("${weather.average.not.serializer.class}")
    private String weatherAvgNotSerializer;

    @Value("${zookeeper.session.timeout.ms}")
    private int zookeeperSessionTimeout;

    @Value("${zookeeper.connection.timeout.ms}")
    private int zookeeperConnTimeout;

    @Value("${zookeeper.sync.time.ms}")
    private int zookeeperSyncTime;

    @Value("${auto.commit.interval.ms}")
    private int autoCommitInt;

    @Bean(name="weatherAvgNotConsumer")
    public IConsumer provideCommandUpdateConsumer() {
        return new WeatherAvgNotificationConsumer();
    }

    @Bean(name="weatherNotConsumer")
    public IConsumer provideCommandConsumer() {
        return new WeatherNotificationConsumer();
    }

    @Bean
    @Scope("prototype")
    public AbstractKafkaConsumer<WeatherAverageNotification> commandWeatherAvgNotConsumer() {
        return new WeatherAvgNotificationKafkaConsumer();
    }

    @Bean
    @Scope("prototype")
    public AbstractKafkaConsumer<WeatherNotification> commandWeatherNotConsumer() {
        return new WeatherNotificationKafkaConsumer();
    }

    @Bean(name = "weatherAvgNotProducer", destroyMethod = "close")
    @Lazy(false)
    public Producer<String, WeatherAverageNotification> notificationWeatherAvgProducer() {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", brokerList);
        properties.put("key.serializer.class", "kafka.serializer.StringEncoder");
        properties.put("serializer.class", weatherAvgNotSerializer);
        properties.put("partitioner.class", "kafka.producer.DefaultPartitioner");
        return new Producer<>(new ProducerConfig(properties));
    }

    @Bean(destroyMethod = "shutdown")
    @Lazy(false)
    public ConsumerConnector commandWeatherAvgNotConsumerConnector() {
        String groupId = "weather-avg-notification" + UUID.randomUUID().toString();
        return createAndSubscribe(groupId, "weather-avg-notification", this::commandWeatherAvgNotConsumer,
                new WeatherAvgNotificationConverter(new VerifiableProperties()));
    }

    @Bean(destroyMethod = "shutdown")
    @Lazy(false)
    public ConsumerConnector commandWeatherNotConsumerConnector() {
        String groupId = "weather-notification" + UUID.randomUUID().toString();
        return createAndSubscribe(groupId, "weather-notification", this::commandWeatherNotConsumer,
                new WeatherNotificationConverter(new VerifiableProperties()));
    }

    private <T> ConsumerConnector createAndSubscribe(String groupId, String topicName,
                                                     Supplier<AbstractKafkaConsumer<T>> consumerCreator, Decoder<T> decoder) {
        Properties properties = consumerSharedProps();
        properties.put("group.id", groupId);
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));

        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(topicName, threadCount);

        Map<String, List<KafkaStream<String, T>>> streams = connector.createMessageStreams(topicCountMap,
                new StringDecoder(new VerifiableProperties()), decoder);
        List<KafkaStream<String, T>> stream = streams.get(topicName);

        int thread = 0;
        for (final KafkaStream sm : stream) {
            AbstractKafkaConsumer<T> consumer = consumerCreator.get();
            consumer.subscribe(sm, thread);
            thread++;
        }
        return connector;
    }

    private Properties consumerSharedProps() {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeperConnect);
        props.put("zookeeper.session.timeout.ms", env.getProperty("zookeeper.session.timeout.ms"));
        props.put("zookeeper.connection.timeout.ms", env.getProperty("zookeeper.connection.timeout.ms"));
        props.put("zookeeper.sync.time.ms", env.getProperty("zookeeper.sync.time.ms"));
        props.put("auto.commit.interval.ms", env.getProperty("auto.commit.interval.ms"));
        return props;
    }
}
