package org.emsal.distdatabase

import akka.actor.{Props, ActorSystem}

/**
  * Created by em on 4/1/16.
  */
object Node {

  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("nodesystem")

    val actor = sys.actorOf(Props[Storer], "actor1")

  }
}
