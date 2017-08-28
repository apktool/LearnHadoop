/**
 * Description
 * This program is used to be server
 *
 * @author apktool
 * @version 0.1
 * @since 8/28/17
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

public class Starter {
    public static void main(String[] args) throws IOException {
        RPC.Builder builder = new RPC.Builder(new Configuration());

        builder.setBindAddress("localhost");
        builder.setPort(10000);
        builder.setProtocol(LoginServiceInterface.class);
        builder.setInstance(new LoginServiceImpl());

        RPC.Server server = builder.build();
        server.start();
    }
}
