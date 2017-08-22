package com.example


import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.util.{Tool, ToolRunner}

object BytesCounter extends Configured with Tool{

  def main(args: Array[String]): Unit = {
    println("Starting program")
    val res = ToolRunner.run(new Configuration(), BytesCounter, args)
    println("Closing program with res: "+res)
    System.exit(res)
  }

  override def run(args: Array[String]): Int = {
    val conf = getConf
//    we must take 2 parameters from args -- input & output directories
    if(args.length != 2){
      println("Args len is incorrect: "+args.length)
      return 2
    }

    val job = Job.getInstance(conf)
    job.setJarByClass(getClass)
    job.setJobName("ByteCount")

    println("inputfile: "+ args(0))
    println("outputfile: "+ args(1))

    FileInputFormat.addInputPath(job, new Path(args(0)))
    FileOutputFormat.setOutputPath(job, new Path(args(1)))

    job.setMapperClass(classOf[BytesCounterMapper])
    job.setReducerClass(classOf[BytesCounterReducer])

    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[IntWritable])

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[BytesMetric])

    job.waitForCompletion(true).compareTo(true)
  }
}
