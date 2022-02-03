package com.jumio.aiml

import io.confluent.ksql.datagen.DataGen

object TestDataGen extends App {
  DataGen.main(
    Array(
      "bootstrap-server=localhost:29092",
      "schema=/Users/rstoian/IdeaProjects/ksqldb_test/src/main/resources/ksql-course-master/datagen/userprofile.avro",
      "format=json",
      "topic=USERPROFILE_TWO",
      "key=userid",
      "msgRate=1",
      "iterations=5"
    )
  )
}
