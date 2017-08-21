package com.example

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import scala.util.Try


case class Pair(ip: String, bytes: Option[Int])

class BytesCounterMapper extends Mapper[LongWritable, Text, Text, IntWritable]{
  val one = new IntWritable(1)

  override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
    val pair: Pair = parseLine(value.toString)
    pair.bytes match {
      case Some(b) => context.write(new Text(pair.ip), new IntWritable(b))
      case _ =>
    }
  }

  def parseLine(line: String): Pair = {
    val array = line.split(" ")
    Pair(array(0), parseInt(array(9)))
  }

  def parseInt(maybeInt: String): Option[Int] = Try(maybeInt.toInt).toOption
}
