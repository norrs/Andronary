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
package no.norrs.projects.andronary.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.norrs.projects.andronary.DirectoryListener;
import no.norrs.projects.andronary.SearchResult;
import no.norrs.projects.andronary.parsers.DictonaryParser;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONException;

/**
 *
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public class DictLookupTask extends AsyncTask<String, Void, List<SearchResult>> {

    DirectoryListener dl = null;

    public DictLookupTask(DirectoryListener dl) {
        this.dl = dl;
    }

    @Override
    protected List<SearchResult> doInBackground(String... lookup) {
        try {
            System.out.println(String.format("%s : %s : %s : %s : %s", lookup[0], lookup[1], lookup[2], lookup[3], lookup[4]));
            if (lookup[0] == null) {
                dl.showError(new Exception("Press menu and configure up webservice.."), null);
                return null;
            }
            UsernamePasswordCredentials creds = null;
            if (lookup[1] != null && lookup[2] != null) {
                creds = new UsernamePasswordCredentials(lookup[1], lookup[2]);
            }
            DictonaryParser dp = new DictonaryParser();
            return (List<SearchResult>) dp.lookup(Uri.parse(String.format("%s/lookup?word=%s&dict=%s", lookup[0], lookup[3].trim(), lookup[4])), creds);
        } catch (IOException ex) {
            Logger.getLogger(DictLookupTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DictLookupTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DictLookupTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }

    @Override
    protected void onPostExecute(List<SearchResult> result) {
        if (result == null) {
            return;
        }
        dl.lookupResults(result);
    }
}
