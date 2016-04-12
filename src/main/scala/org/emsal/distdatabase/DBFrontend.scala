package org.emsal.distdatabase

import akka.actor.Actor

import scala.io._

/**
  * Created by em on 11/04/16.
  */
object DBFrontend {

  def loop(): Unit = {
    val cmd: String = StdIn.readLine()



    loop()
  }

  def main(args: Array[String]): Unit = {

    loop()
  }

  class FrontendActor extends Actor {
    def receive = {
      case FoundData(path, data) => println(data)
    }
  }
}
