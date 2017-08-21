package com.example

import java.lang

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

import scala.collection.JavaConverters._

class BytesCounterReducer extends Reducer[Text, IntWritable, Text, Text] {

  override def reduce(
      key: Text,
      values: lang.Iterable[IntWritable],
      context: Reducer[Text, IntWritable, Text, Text]#Context): Unit = {
    val list = values.iterator().asScala.map(_.get).toList

    val sum = list.sum
    val avg: Float = list.length match {
      case 0 => 0L
      case len: Int => sum.toFloat / len
    }
    context.write(key, new Text(avg + ", " + sum))
  }
}
