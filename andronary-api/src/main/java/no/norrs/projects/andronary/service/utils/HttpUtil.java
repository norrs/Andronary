/**
Copyright 2010 Roy Sindre Norangshol <roy.sindre@norangshol.no>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package no.norrs.projects.andronary.service.utils;

import java.io.BufferedReader;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

/**
 * HttpUtil class based on HttpUtil at http://github.com/javaBin/androidito
 *
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public class HttpUtil {

    public static HttpResponse POST(final URL uri, List<NameValuePair> data) throws IOException {
        return POST(uri.toString(), data);
    }

    public static HttpResponse PUT(final URL uri, List<NameValuePair> data) throws IOException {
        return PUT(uri.toString(), data);
    }

    public static HttpResponse DEL(final URL uri) throws IOException {
        return DEL(uri.toString());
    }

    public static HttpResponse GET(URL url, UsernamePasswordCredentials creds) throws IOException, URISyntaxException {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(url.getHost(), url.getPort()),
                creds);

        HttpGet httpGet = new HttpGet(url.toURI());

        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("User-Agent", "Andronary/0.1");
        HttpResponse response;

        return httpClient.execute(httpGet);
    }

    public static HttpResponse POST(String uri, List<NameValuePair> data) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(uri);
        //httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.addHeader("User-Agent", "Andronary/0.1");
        httpPost.addHeader("Connection", "close");
        StringEntity e = new StringEntity(data.get(0).getValue(), HTTP.UTF_8);
        //httpPost.setEntity(new UrlEncodedFormEntity(data));
        httpPost.setEntity(e);
        HttpResponse response;

        return httpClient.execute(httpPost);

        //return response.getStatusLine().getStatusCode();


        /*HttpEntity entity = response.getEntity();
        return entity.getContent();*/

    }

    public static HttpResponse PUT(String uri, List<NameValuePair> data) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPost = new HttpPut(uri);
        //httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
        httpPost.addHeader("User-Agent", "Andronary/0.1");
        httpPost.addHeader("Connection", "close");
        StringEntity e = new StringEntity(data.get(0).getValue(), HTTP.UTF_8);
        //httpPost.setEntity(new UrlEncodedFormEntity(data));
        httpPost.setEntity(e);
        HttpResponse response;

        return httpClient.execute(httpPost);
        //return response.getStatusLine().getStatusCode();
        /*HttpEntity entity = response.getEntity();
        return entity.getContent();*/

    }

    public static HttpResponse DEL(String uri) throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(uri);
        //httpPost.addHeader("Accept", "application/json");
        httpDelete.addHeader("Content-Type", "application/json; charset=utf-8");
        httpDelete.addHeader("User-Agent", "Andronary/0.1");
        httpDelete.addHeader("Connection", "close");
        return httpClient.execute(httpDelete);

    }

    public static String readString(InputStream inputStream, String encoding) throws IOException {
        if (encoding == null || encoding.length() < 1) {
            encoding = "UTF-8";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }
}
