package com.example

import java.lang

import org.apache.hadoop.io.{FloatWritable, IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

import scala.collection.JavaConverters._

class BytesCounterReducer extends Reducer[Text, IntWritable, Text, BytesMetric] {

  override def reduce(
      key: Text,
      values: lang.Iterable[IntWritable],
      context: Reducer[Text, IntWritable, Text, BytesMetric]#Context): Unit = {
    val list = values.iterator().asScala.map(_.get).toList

    val sum = list.sum
    val avg: Float = list.length match {
      case 0 => 0L
      case len: Int => sum.toFloat / len
    }
    context.write(key, generateBytesMetric(avg, sum))
  }


  def generateBytesMetric(avg: Float, sum: Int): BytesMetric =
    BytesMetric(new FloatWritable(avg),new IntWritable(sum))
}
