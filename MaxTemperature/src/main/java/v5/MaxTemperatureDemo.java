/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 9/8/17
 */

package v5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class MaxTemperatureDemo extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new MaxTemperatureDemo(), args);
        System.exit(exitCode);
    }

    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println(getClass().getSimpleName());
            System.err.println("Command error: command <input path> <output path>");
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        Configuration conf = getConf();
        conf.setBoolean("mapred.task.profile", true);
        conf.set("mapred.task.profile.params", "-agentlib:hprof=cpu=samples," + "heap=sites,depth=6,force=n,thread=y,verbose=n,file=%s");
        conf.set("mapred.task.profile.maps", "0-2");
        conf.set("mapred.task.profile.reduces", "");

        // Job job = Job.getInstance(conf, "Max Temperature");
        Job job = Job.getInstance(conf);
        job.setJobName("Max Temperature");

        job.setJarByClass(MaxTemperatureDemo.class);
        job.setMapperClass(MaxTemperatureMapper.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }
}
