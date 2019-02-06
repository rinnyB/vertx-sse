name := "vertx-sse"

version := "1"

scalaVersion := "2.12.8"
Global / cancelable := true

fork in run := true
val vertxVersion = "3.6.2"
val junit5Version = "5.3.1"

libraryDependencies ++= Seq(

    "io.vertx" % "vertx-core" %  vertxVersion,
    "io.vertx" % "vertx-codegen" % vertxVersion,
    "io.vertx" % "vertx-web" % vertxVersion,

    "io.vertx" % "vertx-junit5" % vertxVersion % "test",
    "org.junit.jupiter" % "junit-jupiter-engine" % junit5Version % "test"
)

assemblyMergeStrategy in assembly := {
      case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
      case PathList("jni", xs @ _*)                      => MergeStrategy.discard
      case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
      case "application.conf"                            => MergeStrategy.concat
      case "unwanted.txt"                                => MergeStrategy.discard
      case PathList(ps @ _*) if ps.last endsWith ".json" => MergeStrategy.last
      case PathList(ps @ _*) if ps.last endsWith ".MF"   => MergeStrategy.discard
      case x => MergeStrategy.first
    }