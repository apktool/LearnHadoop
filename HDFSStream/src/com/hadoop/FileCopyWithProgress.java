package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BufferedFSInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.mortbay.jetty.HttpParser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Description
 * Copy local file to hdfs, and then show the progress while the file is being copied
 *
 * @author hadoop
 * @version 0.1
 * @since 8/25/17
 */
public class FileCopyWithProgress {
    public static void main(String[] args) throws IOException {
        String localSrc = args[0];
        String dstSrc = args[1];

        InputStream in = new BufferedInputStream(new FileInputStream(localSrc));

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dstSrc), conf);

        OutputStream out = fs.create(new Path(dstSrc), () -> System.out.print("."));

        IOUtils.copyBytes(in, out, 4096, true);
    }
}