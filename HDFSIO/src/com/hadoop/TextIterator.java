/**
 * Description
 * Iterating over the characters in the Text object
 * @author apktool
 * @version 0.1
 * @since 8/26/17
 */

package com.hadoop;

import org.apache.hadoop.io.Text;

import java.nio.ByteBuffer;

public class TextIterator {
    public static void main(String[] args) {
        Text t = new Text("abc");
        System.out.println(t);

        ByteBuffer buf = ByteBuffer.wrap(t.getBytes(),0, t.getLength());

        int cp;
        while(buf.hasRemaining() && (cp = Text.bytesToCodePoint(buf))!=-1){
            System.out.println(cp + " >-< "+Integer.toHexString(cp));
        }
    }
}
