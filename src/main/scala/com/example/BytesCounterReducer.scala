package com.example

import java.lang

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

import scala.collection.JavaConverters._

class BytesCounterReducer extends Reducer[Text, IntWritable, Text, Text] {

  override def reduce(
      key: Text,
      values: lang.Iterable[IntWritable],
      context: Reducer[Text, IntWritable, Text, Text]#Context): Unit = {
    val list = values.iterator().asScala.map(_.get).toList

    val sum = list.sum
    val avg: Long = list.length match {
      case 0 => 0
      case len: Int => sum / len
    }
    context.write(key, new Text(avg.toString + "," + sum.toString))
  }
}
