package com.example

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mrunit.mapreduce.{MapDriver, MapReduceDriver, ReduceDriver}
import org.junit.Test
import org.scalatest.junit.JUnitSuite

import scala.collection.JavaConverters._

class BytesCounterMapperReducerTest extends JUnitSuite {

  @Test def testMapper(): Unit = {
    val mapper = new BytesCounterMapper
    val mapDriver = MapDriver.newMapDriver(mapper)
    mapDriver.withInput(new LongWritable(), new Text("ip1 - - [24/Apr/2011:04:18:54 -0400] \"GET /~strabal/grease/photo1/T97-4.jpg HTTP/1.1\" 200 6244 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\""))
    mapDriver.withOutput(new Text("ip1"), new IntWritable(6244))
    mapDriver.runTest()
  }

  @Test def testReducer(): Unit = {
    val reducer = new BytesCounterReducer
    val pairs = new IntWritable(10) :: new IntWritable(30) :: new IntWritable(20) :: Nil
    val reduceDriver = ReduceDriver.newReduceDriver(reducer)
    reduceDriver.withInput(new Text("ip42"), pairs.asJava)
    reduceDriver.withOutput(new Text("ip42"), new Text("20.0, 60"))
    reduceDriver.runTest()
  }

  @Test def testMapReduce(): Unit = {
    val mapper = new BytesCounterMapper
    val reducer = new BytesCounterReducer
    val mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer)
    mapReduceDriver.withInput(new LongWritable(), new Text("ip1 - - [24/Apr/2011:04:18:54 -0400] \"GET /~strabal/grease/photo1/T97-4.jpg HTTP/1.1\" 200 6244 \"-\" \"Mozilla/5.0 (compatible; YandexImages/3.0; +http://yandex.com/bots)\""))
    mapReduceDriver.withOutput(new Text("ip1"), new Text("6244.0, 6244"))
    mapReduceDriver.runTest()
  }
}
