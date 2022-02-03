package com.jumio.aiml

import io.confluent.ksql.api.client.{Client, ClientOptions}

object StreamFromAnotherStream extends App {

  val KSQLDB_SERVER_HOST = "localhost"
  val KSQLDB_SERVER_HOST_PORT = 8088

  val clientOptions = ClientOptions.create().setHost(KSQLDB_SERVER_HOST).setPort(KSQLDB_SERVER_HOST_PORT)
  val client = Client.create(clientOptions)

  client.executeStatement(
    """
      |create stream user_profile_pretty as
      |select firstname + ' '
      |+ ucase( lastname)
      |+ ' from ' + countrycode
      |+ ' has a rating of ' + cast(rating as varchar) + ' stars. '
      |+ case when rating < 2.5 then 'Poor'
      |       when rating between 2.5 and 4.2 then 'Good'
      |       else 'Excellent'
      |   end as description
      |from userprofile_two;
      |""".stripMargin
  )
}
