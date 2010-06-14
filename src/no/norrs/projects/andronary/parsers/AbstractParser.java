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
package no.norrs.projects.andronary.parsers;

import android.net.Uri;
import java.net.URISyntaxException;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.auth.UsernamePasswordCredentials;

import static no.norrs.projects.andronary.utils.HttpUtil.*;

/**
 * AbstractParser class based on HttpUtil at http://github.com/javaBin/androidito
 * 
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public abstract class AbstractParser {

    public AbstractParser() {
    }

    public String readURI(Uri uri, UsernamePasswordCredentials creds) throws IOException, JSONException, URISyntaxException {
        InputStream inputStream = GET(uri, creds).getEntity().getContent();
        final String feedData = readString(inputStream);
        inputStream.close();
        return feedData;
    }

    protected String readString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        return stringBuilder.toString();
    }
}
