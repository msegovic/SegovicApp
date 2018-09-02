import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTTPClient {
    public boolean verifyUrl(String url) {

        // Regex for checking if the URL is valid
        String urlRegex = "^(http|https)://[-a-zA-Z0-9+&@#/%?=~_|,!:.;]*[-a-zA-Z0-9+@#/%=&_|]";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher m = pattern.matcher(url);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public int incorrectURLS = 0;
    public String succeededStatus;
    public String failedStatus;



    public Map<String, List<String>> validateUrl(String url) throws Exception {
        if (verifyUrl(url)){
            try{
                URL myURL = new URL(url);
                HttpURLConnection myConnection = (HttpURLConnection) myURL.openConnection();

                if (myConnection.getResponseCode()==URLStatus.HTTP_OK.getStatusCode()) {
                    succeededStatus = URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
                } else {
                    failedStatus = URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
                }

                // Store headers from the request into a map

                Map<String, List<String>> map = myConnection.getHeaderFields();
                for (Map.Entry<String, List<String>> entry : map.entrySet())
                {
                    System.out.print(entry.getKey() + ": " + entry.getValue() + "\n");
                }
                System.out.println();
                return map;

            } catch (Exception e) {
                System.out.print("For url- " + url+ "" +e.getMessage());
            }

        }else {
            incorrectURLS += 1;
        }
        return null;
    }

}
