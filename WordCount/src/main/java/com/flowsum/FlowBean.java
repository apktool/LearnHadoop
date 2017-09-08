package com.flowsum;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Description
 * Created with IntelliJ IDEA.
 *
 * @author apktool
 * @version 0.1
 * @since 8/29/17
 */
public class FlowBean implements WritableComparable<FlowBean> {
    private String phoneNB;
    private long up_flow;
    private long d_flow;
    private long s_flow;

    public FlowBean(String phoneNB, long up_flow, long d_flow) {
        this.phoneNB = phoneNB;
        this.up_flow = up_flow;
        this.d_flow = d_flow;
        this.s_flow = up_flow + d_flow;
    }

    public String getPhoneDB() {
        return phoneNB;
    }

    public void setPhoneDB(String phoneNB) {
        this.phoneNB = phoneNB;
    }

    public long getUp_flow() {
        return up_flow;
    }

    public void setUp_flow(long up_flow) {
        this.up_flow = up_flow;
    }

    public long getD_flow() {
        return d_flow;
    }

    public void setD_flow(long d_flow) {
        this.d_flow = d_flow;
    }

    public long getS_flow() {
        return s_flow;
    }

    public void setS_flow(long s_flow) {
        this.s_flow = s_flow;
    }

    @Override
    public String toString() {
        return "" + up_flow + "\t" + d_flow + "\t" + s_flow;
    }

    @Override
    public int compareTo(FlowBean o) {
        return s_flow > o.getS_flow() ? -1 : 1;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(phoneNB);
        dataOutput.writeLong(up_flow);
        dataOutput.writeLong(d_flow);
        dataOutput.writeLong(s_flow);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        phoneNB = dataInput.readUTF();
        up_flow = dataInput.readLong();
        d_flow = dataInput.readLong();
        s_flow = dataInput.readLong();
    }
}
