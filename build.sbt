name := "ksqldb_test"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.jumio.aiml")

libraryDependencies ++= Seq(
  "io.confluent.ksql" % "ksqldb-api-client" % "6.2.1",
  "io.confluent.ksql" % "ksqldb-examples" % "6.2.1",
  "io.confluent" % "kafka-avro-serializer" % "6.2.1"
)

resolvers ++= Seq(
  "confluent" at "https://packages.confluent.io/maven/",
  "jitpack" at "https://jitpack.io"
)