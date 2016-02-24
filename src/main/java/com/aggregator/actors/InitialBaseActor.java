package com.aggregator.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * Author: Yuliia Vovk
 * Date: 22.02.16
 * Time: 22:42
 */
@Named("InitialBaseActor")
@Scope("prototype")
public class InitialBaseActor extends UntypedActor {

    @Autowired
    @Qualifier("dbSaverActor")
    private ActorRef childDbSaverActor;

    @Override
    public void onReceive(Object msg) throws Exception {
        childDbSaverActor.tell(msg, getSelf());
    }
}
