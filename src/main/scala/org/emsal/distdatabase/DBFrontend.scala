package org.emsal.distdatabase

import akka.actor.{Props, ActorSystem, Actor}
import com.typesafe.config.ConfigFactory

import scala.io._

/**
  * Created by em on 11/04/16.
  */
object DBFrontend {

  def main(args: Array[String]): Unit = {
    val port = if (args.isEmpty) "0" else args(0)
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]")).withFallback(ConfigFactory.load())

    val system = ActorSystem("nodesystem")
    val frontend = system.actorOf(Props[FrontendActor], name = "frontend")


  }

}
