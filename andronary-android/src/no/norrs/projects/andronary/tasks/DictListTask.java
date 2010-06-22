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
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.norrs.projects.andronary.DirectoryListener;
import no.norrs.projects.andronary.parsers.DictonaryParser;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.json.JSONException;

/**
 *
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public class DictListTask extends AsyncTask<String, Void, String[]> {

    DirectoryListener dl = null;

    public DictListTask(DirectoryListener dl) {
        this.dl = dl;
    }

    @Override
    protected String[] doInBackground(String... lookup) {
        try {
            System.out.println(String.format("%s : %s : %s", lookup[0], lookup[1], lookup[2]));
            if (lookup[0] == null) {
                dl.showError(new Exception("Press menu and configure up webservice.."), null);
                return null;
            }

            UsernamePasswordCredentials creds = null;
            if (lookup[1] != null && lookup[2] != null) {
                creds = new UsernamePasswordCredentials(lookup[1], lookup[2]);
            }
            DictonaryParser dp = new DictonaryParser();
            Collection<String> results = dp.dictonaries(Uri.parse(String.format("%s/languages/", lookup[0])), creds);
            return results.toArray(new String[results.size()]);

        } catch (IOException ex) {
            Logger.getLogger(DictLookupTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DictLookupTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DictLookupTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;


    }

    protected void onPostExecute(String[] result) {
        if (result == null) {
            return;
        }
        dl.directoriesAvailable(result);
    }
}
