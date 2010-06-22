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
package no.norrs.projects.andronary.activities;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import no.norrs.projects.andronary.Globals;
import no.norrs.projects.andronary.R;
import no.norrs.projects.andronary.SearchResult;

/**
 *
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public class SearchResultListView extends ListActivity {

    static final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

    public SearchResultListView() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        SharedPreferences settings = getSharedPreferences(Globals.PREFS, 0);
        List<SearchResult> results = (List<SearchResult>) getIntent().getSerializableExtra(Globals.SEARCHRESULTS);
        setTitle(String.format("[%s] Lookup results for %s ", settings.getString(Globals.DICT, null), (String) getIntent().getSerializableExtra(Globals.SEARCHWORD)));
        setContentView(R.layout.search_result_list_view);

        list.clear();

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.search_result_row_view,
                new String[]{"word", "translation"},
                new int[]{R.id.text1, R.id.text2});

        for (SearchResult sr : results) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("translation", sr.getTranslation());
            temp.put("word", sr.getWord());
            list.add(temp);
        }

        setListAdapter(adapter);


    }
}
