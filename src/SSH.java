import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.*;

public class SSH {
    public String host;
    public boolean successfulConn;
    public void ConnectandExecute(String User, char[] Pass) {
        // WebChecker webChecker = new WebChecker();
        host = "192.168.5.50";
        String command = "systemctl status nginx";
        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            // Create a JSch session to connect to the server
            Session session = jsch.getSession(User, host, 22);
            session.setPassword(String.valueOf(Pass));
            session.setConfig(config);
            // Establish the connection
            session.connect();
            successfulConn = session.isConnected();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("Exit Status: "
                            + channel.getExitStatus());
                    break;
                }
                Thread.sleep(1000);
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}