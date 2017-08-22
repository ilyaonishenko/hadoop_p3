package com.example

import java.io.{DataInput, DataOutput}

import org.apache.hadoop.io.{FloatWritable, IntWritable, Writable}

case class BytesMetric(average: FloatWritable, sum: IntWritable) extends Writable{

  def this() = this(new FloatWritable(), new IntWritable())

  override def readFields(in: DataInput): Unit = {
    average.readFields(in)
    sum.readFields(in)
  }

  override def write(out: DataOutput): Unit = {
    average.write(out)
    sum.write(out)
  }

  override def toString: String = s"${average.toString}, ${sum.toString}"

}