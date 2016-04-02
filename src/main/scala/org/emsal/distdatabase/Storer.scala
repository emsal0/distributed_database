package org.emsal.distdatabase

import akka.actor.{ActorLogging, Actor}
import com.typesafe.config._
import better.files._

/**
  * Created by em on 3/31/16.
  */
class Storer extends Actor with ActorLogging {

  val conf = ConfigFactory.load()
  val outputFilename = conf.getString("node_consts.store_name")

  println(outputFilename)

  def receive = {
    case StoreData(data) => {
      storeData(data)
    }
  }

  def storeData(data: String): Unit = {
    val outputFile: File = File(outputFilename)
    outputFile << data
    sender() ! DataWritten
  }
}
