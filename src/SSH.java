import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.*;

public class SSH {
    public String host;
    public boolean successfulConn;
    public String error;
    public int channelStatus;
    public byte[] tmp;

    public void ConnectandExecute(String User, char[] Pass, String command){
        host = "192.168.5.50";
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
            tmp = new byte[2048];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 2048);
                    if (i < 0) {
                        break;
                    }
                    //System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    channelStatus = channel.getExitStatus();
                    break;
                }
                Thread.sleep(1000);
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            error = e.getMessage();
        }
    }
}