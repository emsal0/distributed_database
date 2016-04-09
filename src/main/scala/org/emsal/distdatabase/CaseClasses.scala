package org.emsal.distdatabase

import akka.actor.ActorPath

/**
  * Created by em on 4/1/16.
  */

case class StoreData(data: String)
case class DataWritten(actorPath: ActorPath)
case class WritingData(actorPath: ActorPath)
case class GetData(id: String)
case class FoundData(actorPath: ActorPath, data:String)
case class NothingFoundHere(actorPath: ActorPath)
