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

import java.util.HashMap;
import java.util.List;

/**
 * 
 * 
 * @author Roy Sindre Norangshol <roy.sindre@norangshol.no>
 */
public interface AndronaryService {

    no.norrs.projects.andronary.service.utils.Dictionary[] getDictionaries();

    no.norrs.projects.andronary.service.utils.Dictionary getSelectedDictionary();

    HashMap<String, List<String>> lookup(String dict);

    boolean selectDictionary(no.norrs.projects.andronary.service.utils.Dictionary dictionary);

    String getProvider();
}
