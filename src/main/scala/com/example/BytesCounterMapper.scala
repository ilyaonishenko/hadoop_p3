package com.example

import com.example.Browsers.Browser
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

import scala.util.Try

case class Pair(ip: String, bytes: Option[Int], browser: Browser)

class BytesCounterMapper extends Mapper[LongWritable, Text, Text, IntWritable]{

  override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
    val line = value.toString
    val pair: Pair = parseLine(line)
    pair.bytes match {
      case Some(b) => context.write(new Text(pair.ip), new IntWritable(b))
      case _ =>
    }
    context.getCounter("Browsers", pair.browser.getName).increment(1L)
  }

  def parseLine(line: String): Pair = {
    val array = line.split(" ")
    Pair(array(0), parseInt(array(9)), parseBrowser(array(11)))
  }

  def parseInt(maybeInt: String): Option[Int] = Try(maybeInt.toInt).toOption

  def parseBrowser(stringWithBrowser: String): Browser =
    Browsers.values
      .find(v => stringWithBrowser.contains(v.getName))
      .getOrElse(Browsers.Unknown)
}
