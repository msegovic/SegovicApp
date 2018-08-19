import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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



    public void validateUrl(String url) throws Exception {
        if (verifyUrl(url)){
            try{
                URL myURL = new URL(url);
                HttpURLConnection myConnection = (HttpURLConnection) myURL.openConnection();

                if (myConnection.getResponseCode()==URLStatus.HTTP_OK.getStatusCode()) {
                    succeededStatus = URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
                } else {
                    failedStatus = URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
                }

            } catch (Exception e) {
                System.out.print("For url- " + url+ "" +e.getMessage());
            }

        }else {
                incorrectURLS += 1;
        }
    }

    /*public void main(String[] args) {

        try {
            HTTPClient myClient = new HTTPClient();
            myClient.validateUrl();
            System.out.println(url);
            System.out.println("Valid URLS that have successfully connected :");
            System.out.println(myClient.succeededURLS);
            System.out.println("\n--------------\n\n");
            System.out.println("Broken URLS that did not successfully connect :");
            System.out.println(myClient.failedURLS);
        } catch (Exception e) {
            System.out.print(e.getMessage()); }
    }*/
}


// Possible implementation of more URLs to check, not just one, probably better

/*    public void validateUrl() throws Exception {
        Path filePath = Paths.get("src/url-list.txt");
        List<String> myURLArrayList = Files.readAllLines(filePath);

        myURLArrayList.forEach((String url) -> {
            if (verifyUrl(url)) {
                try{
                    URL myURL = new URL(url);
                    HttpURLConnection myConnection = (HttpURLConnection) myURL.openConnection();

                    if (myConnection.getResponseCode()==URLStatus.HTTP_OK.getStatusCode()) {
                        succeededStatus = URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
                        succeededURLS = succeededURLS + "\n" + url + "****** Status message is : "
                                + succeededStatus;
                    } else {
                        failedStatus = URLStatus.getStatusMessageForStatusCode(myConnection.getResponseCode());
                        failedURLS = failedURLS + "\n" + url + "****** Status message is : "
                                + failedStatus;
                    }

                } catch (Exception e) {
                    System.out.print("For url- " + url+ "" +e.getMessage());
                }

            }else {
                incorrectURLS += "\n" + url;
            }
        });
    }*/

