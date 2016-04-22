package org.emsal.distdatabase

import akka.actor.{ActorRef, Actor, ActorLogging}

/**
  * Created by em on 4/14/16.
  */
class FrontendActor extends Actor with ActorLogging {

  var backends = IndexedSeq.empty[ActorRef]
  var jobCounter = 0

  def receive = {
    case job: StoreData => {
      jobCounter = jobCounter + 1
      backends(jobCounter % backends.size).forward(job)
    }
    case StorerRegistration => {
      context.watch(sender())
      backends = backends :+ sender()
    }
  }
}
