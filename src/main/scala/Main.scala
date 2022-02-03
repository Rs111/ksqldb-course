package com.jumio.aiml

import io.confluent.ksql.api.client.{ Client, ClientOptions }
import org.reactivestreams

object Main extends App {
  val KSQLDB_SERVER_HOST = "localhost"
  val KSQLDB_SERVER_HOST_PORT = 8088

  val clientOptions = ClientOptions.create().setHost(KSQLDB_SERVER_HOST).setPort(KSQLDB_SERVER_HOST_PORT)
  val client = Client.create(clientOptions)

  // Get topics
  println(client.serverInfo().get())
  println(client.listTopics().get())
  println(client.listTables().get())
  println(client.listQueries().get())
  println(client.listStreams().get())
  //Thread.sleep(10000)


  // Create Stream (fails if run twice without replace)
//  client
//    .executeStatement("create or replace stream users_stream_two (name VARCHAR, countrycode VARCHAR) WITH (KAFKA_TOPIC='USERS', VALUE_FORMAT='DELIMITED');")
//    .get()
//
//  println(client.listStreams().get())

  // describe (table or stream)
  client.describeSource("userprofile_two").get()

  client.executeStatement(
    """
      |create stream user_profile_pretty as
      |select firstname || ' ' || ucase( lastname) || ' from ' || countrycode
      |from userprofile_two
      |""".stripMargin
  )

  // Drop stream
  client
    .executeStatement("drop stream if exists users_stream_two;")
  // marks topic for deletion by brokers
//  client
//    .executeStatement("drop stream if exists users_stream_two delete topic;")

  // create push query (push query is subscription query)
  //client.executeStatement("SET 'auto.offset.reset'='earliest';").get() doesn't work
  val res = client
    .executeQuery("select name, countrycode from users_stream emit changes limit 2;")
    .get()
  println(res)

  val res2 = client
    .executeQuery("select countrycode, count('*') as cnt from users_stream group by countrycode emit changes;")
    .get()
  println(res2)

//  client.executeStatement("UNSET 'auto.offset.reset';").get()
  client.close()
  println("done")
}
