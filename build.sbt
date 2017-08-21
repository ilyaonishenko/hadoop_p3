name := "hadoop_p3"

version := "1.0"

scalaVersion := "2.12.3"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.12.3",
  "org.apache.hadoop" % "hadoop-common" % "2.8.1",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.7.4",
  "org.apache.mrunit" % "mrunit" % "1.1.0" % "test" classifier "hadoop2",
  "org.scalatest" % "scalatest_2.12" % "3.0.3" % "test"
)