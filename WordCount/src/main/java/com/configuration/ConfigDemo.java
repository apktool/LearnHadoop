/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 9/8/17
 */

package com.configuration;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

public class ConfigDemo {
    public static void main(String[] args) {
        Path path = new Path("test.xml");

        Configuration conf = new Configuration();
        conf.addResource(path);

        int value1 = conf.getInt("height", 0);
        System.out.println(value1);

        String value2 = conf.get("color");
        System.out.println(value2);
    }
}
