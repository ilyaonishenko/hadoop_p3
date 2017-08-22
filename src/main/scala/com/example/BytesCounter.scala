package com.example


import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, LongWritable, SequenceFile, Text}
import org.apache.hadoop.mapreduce.{Counters, Job}
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, SequenceFileOutputFormat}
import org.apache.hadoop.util.{Tool, ToolRunner}

object BytesCounter extends Configured with Tool {

  def main(args: Array[String]): Unit = {
    println("Starting program")
    val res = ToolRunner.run(new Configuration(), BytesCounter, args)
    println("Closing program with res: " + res)
    System.exit(res)
  }

  override def run(args: Array[String]): Int = {
    val conf = getConf
    //    we must take 2 parameters from args -- input & output directories
    if (args.length != 2) {
      println("Args len is incorrect: " + args.length)
      return 2
    }

    conf.setBoolean("mapreduce.output.fileoutputformat.compress", true)
    conf.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.SnappyCodec")

    val job = Job.getInstance(conf)
    job.setJarByClass(getClass)
    job.setJobName("ByteCount")

    println("inputfile: " + args(0))
    println("outputfile: " + args(1))

    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))

    job.setMapperClass(classOf[BytesCounterMapper])
    job.setReducerClass(classOf[BytesCounterReducer])

    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[IntWritable])

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[BytesMetric])

    SequenceFileOutputFormat.setOutputCompressionType(job, SequenceFile.CompressionType.BLOCK)


    val res = job.waitForCompletion(true).compareTo(true)

    println("Some words about counters:")
    val counters = job.getCounters
    counters.forEach { group =>
      group.forEach { counter =>
        println(s"${counter.getDisplayName} --- ${counter.getName} --- ${counter.getValue}")
      }
    }
    res
  }
}
