import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnection {
        private final String USER_AGENT = "Mozilla/5.0";

        public static void main(String[] args) throws Exception {

            HttpURLConnection http = new HttpURLConnection();

            System.out.println("Testing 1 - Send Http GET request");
            http.sendGet();

        }

        // HTTP GET request
        private void sendGet() throws Exception {

            String url = "https://ir.ebaystatic.com/rtm/0/RTMS/Image/8542_EN_ROW_MBG_300x130_.jpg";

            URL obj = new URL(url);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("HEAD");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

        }
}
