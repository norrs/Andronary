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
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import no.norrs.projects.andronary.SearchResult;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public class DictonaryParser extends AbstractParser {

    public DictonaryParser() {
        super();
    }

    public Collection<String> dictonaries(Uri uri, UsernamePasswordCredentials creds) throws IOException, JSONException, URISyntaxException {
        return parseDictonariesGET(readURI(uri, creds));
    }

    public Collection<SearchResult> lookup(Uri uri, UsernamePasswordCredentials creds) throws IOException, JSONException, URISyntaxException {
        return parseLookupGET(readURI(uri, creds));
    }

    private Collection<SearchResult> parseLookupGET(String feedData) throws JSONException {
        JSONArray items = null;
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        System.out.println(feedData);
        if (feedData != null && feedData.startsWith("{")) {
            JSONObject j = new JSONObject(feedData);
            items = j.getJSONArray("result");
            j = null;
            for (int i = 0; i < items.length(); i++) {
                j = items.getJSONObject(i);
                results.add(new SearchResult(j.getString("translation").replaceAll("<br>", "\n"), j.getString("word")));
            }

        }
        return results;
    }

    private Collection<String> parseDictonariesGET(String feedData) throws JSONException {
        JSONArray items = null;
        ArrayList<String> results = new ArrayList<String>();

        System.out.println(feedData);
        if (feedData != null && feedData.startsWith("{")) {
            JSONObject j = new JSONObject(feedData);
            items = j.getJSONArray("languages");
            j = null;
            for (int i = 0; i < items.length(); i++) {
                j = items.getJSONObject(i);
                results.add(j.getString("language"));
            }

        }
        System.out.println(Arrays.toString(results.toArray()));
        return results;
    }
}
