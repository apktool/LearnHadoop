/**
 * Description
 * This program is used to be client.
 *
 * @author apktool
 * @version 0.1
 * @since 8/28/17
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LoginController {
    public static void main(String[] args) throws IOException {
        LoginServiceInterface proxy = RPC.getProxy(
                LoginServiceInterface.class, 1L,
                new InetSocketAddress("localhost", 10000),
                new Configuration()
        );

        String result = proxy.login("apktool", "123456");
        System.out.println(result);
    }
}
