/**
 * Description
 * FileSystem basic operation
 *
 * @author hadoop
 * @version 0.1
 * @since 8/25/17
 */
package com.hadoop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

public class HdfsUtil {
    static FileSystem fs = null;
    @Before
    public static void init() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000/");
        fs = FileSystem.get(new URI("hdfs://localhost:9000/"), conf, "hadoop");
    }

    /**
     * Note:
     * Upload jdk-8u144-docs-all.zip to hdfs, And then rename it to aa.zip
     *
     * @throws Exception
     */

    @Test
    public static void upload() throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000/");

        FileSystem fs = FileSystem.get(conf);
        Path dst = new Path("hdfs://localhost:9000/user/hadoop/input/aa.zip");

        FSDataOutputStream os = fs.create(dst);
        FileInputStream is = new FileInputStream("/home/hadoop/Downloads/jdk-8u144-docs-all.zip");

        IOUtils.copy(is, os);
    }

    @Test
    public static void upload2() throws Exception, IOException {
        fs.copyFromLocalFile(
                new Path("/home/hadoop/Downloads/jdk-8u144-docs-all.zip"),
                new Path("hdfs://localhost:9000/user/hadoop/input/aa.zip")
        );
    }


    @Test
    public static void download() throws Exception {
        fs.copyToLocalFile(
                new Path("hdfs://localhost:9000/user/hadoop/input/sample.txt"),
                new Path("/home/hadoop")
        );
    }

    @Test
    public static void listFiles() throws IllegalArgumentException, IOException {

        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/"), true);

        while (files.hasNext()) {

            LocatedFileStatus file = files.next();
            Path filePath = file.getPath();
            String fileName = filePath.getName();
            System.out.println(fileName);

        }

        System.out.println("---------------------------------");

        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus status : listStatus) {

            String name = status.getPath().getName();
            System.out.println(name + (status.isDirectory() ? " is dir" : " is file"));
        }
    }

    @Test
    public static void mkdir() throws IOException {
        fs.mkdirs(new Path("/user/hadoop/temp"));
    }

    @Test
    public static void rm() throws IllegalArgumentException, IOException {
        fs.delete(new Path("/user/hadoop/temp"), true);
    }


    public static void main(String[] args) throws Exception {
        /*
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000/");

        FileSystem fs = FileSystem.get(conf);
        FSDataInputStream is = fs.open(new Path("/user/hadoop/input/sample.txt"));
        FileOutputStream os = new FileOutputStream("/home/hadoop/jdk7.tgz");
        IOUtils.copy(is, os);
        */

        init();
        upload2();
        download();
        mkdir();
        rm();
        listFiles();
    }
}
