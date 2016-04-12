name := "distributed-database"

version := "1.0"

scalaVersion := "2.11.8"


resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
libraryDependencies ++= Seq (
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
  "com.typesafe.akka" % "akka-cluster_2.11" % "2.4.2",
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.11-M1",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.3",
  "com.github.tototoshi" %% "scala-csv" % "1.3.0",
  "com.github.pathikrit" %% "better-files" % "2.15.0"
)