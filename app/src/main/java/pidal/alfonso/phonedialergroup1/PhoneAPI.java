package pidal.alfonso.phonedialergroup1;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by alfonsopidal on 23/11/14.
 */
public class PhoneAPI {


    private String phoneNumber;

    public PhoneAPI(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getData() {

        String url = "https://metropolis-api-phone.p.mashape.com/analysis?telephone=";
        url = url + this.phoneNumber;

        StringBuilder stringBuilder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("X-Mashape-Key", "Iszt7FIyYhmshQ6ikMVTyJz9dZXOp1MMx3Djsn5EIXUGVdfIIf");

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if(statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content)
                );

                String line;
                while((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
            }
            else
            {
                Log.e("JSON", "Failed to connect/download JSON!");
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
