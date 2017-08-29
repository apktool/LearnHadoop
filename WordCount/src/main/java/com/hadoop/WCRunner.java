package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.ClassNotFoundException;


public class WCRunner{
	public static void main(String[] argv) throws IOException, InterruptedException, ClassNotFoundException{
		Configuration con = new Configuration();
		Job wcjob = Job.getInstance();
		wcjob.setJarByClass(WCRunner.class);

		wcjob.setMapperClass(WCMapper.class);
		wcjob.setReducerClass(WCReducer.class);

		wcjob.setCombinerClass(WCReducer.class);

		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(LongWritable.class);

		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(LongWritable.class);

		FileInputFormat.setInputPaths(wcjob, new Path("hdfs://localhost:9000/user/hadoop/input"));
		FileOutputFormat.setOutputPath(wcjob, new Path("hdfs://localhost:9000/user/hadoop/output"));

		wcjob.waitForCompletion(true);
	}

}
