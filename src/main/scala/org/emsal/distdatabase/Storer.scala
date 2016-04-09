package org.emsal.distdatabase

import akka.actor.{ActorLogging, Actor}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import com.typesafe.config._
import better.files._

/**
  * Created by em on 3/31/16.
  */
class Storer extends Actor with ActorLogging {
  import context._

  val cluster = Cluster(context.system)
  val conf = ConfigFactory.load()
  val outputFilename = conf.getString("node_consts.store_name")

  println(outputFilename)

  override def preStart(): Unit = cluster.subscribe(self, classOf[MemberEvent], classOf[UnreachableMember])
  override def postStop(): Unit = cluster.unsubscribe(self)

  def locked: Receive = {
    case StoreData(data) => {}
    case DataWritten(data) => unbecome()
    case GetData(id) => retrieveData(id)
    case _: MemberEvent => {}
  }

  def receive = {
    case StoreData(data) => storeData(data)
    case WritingData(path) => become(locked)
    case MemberUp(member) => {}
    case GetData(id) => retrieveData(id)
    case UnreachableMember(member) => {}
    case MemberRemoved(member, previousStatus) => {}
    case _: MemberEvent => {}
  }

  def lock(): Unit = {}

  def retrieveData(id: String): Unit = {
    val outputFile: File = File(outputFilename)
    val fileContents: String = outputFile.contentAsString
    val lines: List[String] = fileContents.split("\n").toList

    val entries: List[(String, String)] = lines.map((line: String) => {
      val splitLine: List[String] = lines.split(",").map((x: String) => x.trim)

      (splitLine(0), splitLine(1))
    })

    val entriesMap: Map[String, String] = entries.toMap

    entriesMap.get(id) match {
      case Some(data) => sender() ! FoundData(self.path, data)
      case None => sender() ! NothingFoundHere(self.path)
    }
  }

  def storeData(data: String): Unit = {
    sender() ! WritingData(self.path)
    val outputFile: File = File(outputFilename)
    outputFile << data
    sender() ! DataWritten(self.path)
  }
}
