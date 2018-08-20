import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * The HEAD method is identical to GET except that the server MUST NOT 
 * return a message-body in the response. The meta-information contained 
 * in the HTTP headers in response to a HEAD request SHOULD be identical to 
 * the information sent in response to a GET request. This method can be 
 * used for obtaining meta-information about the entity implied by the 
 * request without transferring the entity-body itself. 
 *
 * This method is often used for testing hypertext links for validity, 
 * accessibility, and recent modification.
 *
 * - Source www.w3.org
 */
public class HeadRequest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpHead httphead = new HttpHead("http://finance.google.com/finance/info?client=ig&q=NASDAQ:GOOG");
        System.out.println("Requesting : " + httphead.getURI());

        try {

            HttpResponse response = httpclient.execute(httphead);
            System.out.println("Protocol : " + response.getProtocolVersion());
            System.out.println("Status code : " + response.getStatusLine().getStatusCode());
            System.out.println("Status line : " + response.getStatusLine());

            Header [] headers = response.getAllHeaders();
            for (Header header : headers) {
                System.out.println(" --> " + header.getName() + ":" + header.getValue());
            }

            System.out.println("\n\nResponse : ");
            if (response.getEntity() != null) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } else {
                System.out.println("As expected no response body for HEAD request");
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

}