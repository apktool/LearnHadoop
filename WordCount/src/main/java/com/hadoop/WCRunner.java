package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.ClassNotFoundException;


public class WCRunner {
    public static void main(String[] argv) throws IOException, InterruptedException, ClassNotFoundException {
        BasicConfigurator.configure(); // solve the WARN with log4j

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "WordCount");

        job.setJarByClass(WCRunner.class);

        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);

        job.setCombinerClass(WCReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        FileInputFormat.setInputPaths(job, new Path("hdfs://localhost:9000/user/hadoop/input"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/user/hadoop/output"));

        job.waitForCompletion(true);
    }
}
