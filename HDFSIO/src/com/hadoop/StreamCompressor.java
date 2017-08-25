/**
 * Description
 * Compress data
 *
 * @author apktool
 * @version 0.1
 * @since 8/25/17
 */

package com.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.IOException;
import java.net.URI;

public class StreamCompressor {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        String codecClassname = args[0];
        String input = args[1];

        Class<?> codecClass = Class.forName(codecClassname);

        Configuration conf = new Configuration();
        CompressionCodec codec = (CompressionCodec)
                ReflectionUtils.newInstance(codecClass, conf);

        FileSystem fs = FileSystem.get(URI.create(input), conf);

        FSDataInputStream in = null;
        CompressionOutputStream out = null;

        try {
            in = fs.open(new Path(input));
            Path op = new Path(input.substring(0, input.lastIndexOf("."))+ codec.getDefaultExtension());
            out = codec.createOutputStream(fs.create(op));
            IOUtils.copyBytes(in, out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
            out.finish();
        }
    }
}
