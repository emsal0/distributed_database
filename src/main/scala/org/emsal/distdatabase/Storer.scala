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

  val cluster = Cluster(context.system)
  val conf = ConfigFactory.load()
  val outputFilename = conf.getString("node_consts.store_name")

  println(outputFilename)

  override def preStart(): Unit = cluster.subscribe(self, classOf[MemberEvent], classOf[UnreachableMember])
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case StoreData(data) => storeData(data)
    case DataWritten => lock()
    case MemberUp(member) => {}
    case UnreachableMember(member) => {}
    case MemberRemoved(member, previousStatus) => {}
    case _: MemberEvent => {}
  }

  def lock(): Unit = {}


  def storeData(data: String): Unit = {
    sender() ! DataWritten
    val outputFile: File = File(outputFilename)
    outputFile << data
  }
}
