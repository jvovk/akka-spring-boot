package com.aggregator.configuration;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 16:12
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@ComponentScan("com.aggregator")
public class AppContextBoot  {

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String... args) {
        SpringApplication.run(AppContextBoot.class, args);
    }

    @Bean
    public MongoOperations mongoDbFactory() throws Exception {
        return new MongoTemplate(new MongoClient(
                environment.getProperty("spring.data.mongodb.host")),
                "spring.data.mongodb.database");
    }

    @Bean(name = "notificationsExecutor")
    public ExecutorService messageExecutorService(@Value("${app.executor.size}") Integer executorSize) {
        return Executors.newFixedThreadPool(executorSize);
    }

    @Bean(destroyMethod = "shutdown")
    @Lazy(false)
    public ActorSystem provideActorSystem() {
        ActorSystem system = ActorSystem.create("akka-weather-actor");
        // initialize the application context in the Akka Spring Extension
        SpringExtension.SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }

    @Lazy(false)
    @Bean(name = "initBaseActor")
    public ActorRef provideParentActorRef() {
        return provideActorSystem().actorOf(SpringExtension.SpringExtProvider
                .get(provideActorSystem()).props("InitialBaseActor"), "parent-actor");
    }

    @Lazy(false)
    @Bean(name = "dbSaverActor")
    public ActorRef provideChildDbActorRef() {
        return provideActorSystem().actorOf(SpringExtension.SpringExtProvider
                .get(provideActorSystem()).props("DbSaverActor"), "child-db-saver-actor");
    }

    @Lazy(false)
    @Bean(name = "dbAggregatorActor")
    public ActorRef provideChildDbAggregatorActorRef() {
        return provideActorSystem().actorOf(SpringExtension.SpringExtProvider
                .get(provideActorSystem()).props("DbAggregatorActor"), "child-db-aggregator-actor");
    }
}
