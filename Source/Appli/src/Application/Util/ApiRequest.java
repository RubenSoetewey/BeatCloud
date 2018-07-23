package Application.Util;
import java.util.List;
import java.util.ArrayList;
/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class ApiRequest {
    public static final String apiUrl = "https://beatcloud.herokuapp.com";

    public static String sendPost(String url, List<NameValuePair> arguments){
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);

            // Print out the response message
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex){
            System.out.println(ex);
        }
        return null;
    }

    public static String auth(String username, String password){

        List<NameValuePair> arguments = new ArrayList<>();
        arguments.add(new BasicNameValuePair("username", username));
        arguments.add(new BasicNameValuePair("password", password));

        return ApiRequest.sendPost(apiUrl + "/account/auth", arguments);
    }

    public static void main(String[] args) {
    }

}