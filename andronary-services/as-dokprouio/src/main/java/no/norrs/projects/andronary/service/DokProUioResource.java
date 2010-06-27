package no.norrs.projects.andronary.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import no.norrs.projects.andronary.service.DokproUioService.Dicts;
import no.norrs.projects.andronary.service.utils.Dictionary;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author rockj
 */
@Path("/rest")
@Produces({"application/json"})
public class DokProUioResource {

    protected DokproUioService service;

    public DokProUioResource() {
        service = new DokproUioService(Dicts.nbNO);
    }

    @GET
    @Path("/languages")
    //@Produces({"application/json"})
    public String getDictionaries() throws JSONException {
        Dicts[] dicts = (Dicts[]) service.getDictionaries();

        JSONObject root = new JSONObject();
        JSONArray dictionaries = new JSONArray();
        for (Dicts d : dicts) {
            JSONObject obj = new JSONObject();
            obj.put("language", d.name());
            dictionaries.put(obj);
        }
        root.put("languages", dictionaries);
        return root.toString();
    }

    @GET
    @Path("/lookup/{word}")
    public String lookup(@PathParam("word") String word) throws JSONException {
        return lookupHelper(word, service.getSelectedDictionary());
    }

    @GET
    @Path("/lookup")
    public String lookup(@QueryParam("word") String word, @QueryParam("dict") String dict) throws JSONException {
        return lookupHelper(word, dict);
    }

    protected String lookupHelper(String word, String dict) throws JSONException {
        return lookupHelper(word, Dicts.valueOf(dict));
    }

    protected String lookupHelper(String word, Dictionary dict) throws JSONException {
        JSONObject root = new JSONObject();
        JSONArray results = new JSONArray();

        service.selectDictionary(dict);

        HashMap<String, List<String>> serviceResults = service.lookup(word);
        Iterator i = serviceResults.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, List<String>> entry = (Entry<String, List<String>>) i.next();
            JSONObject oneResult = new JSONObject();
            oneResult.put("word", entry.getKey());
            String toBeAdded = "";
            for (String s : entry.getValue()) {
                toBeAdded = toBeAdded + s + "\n";
            }
            toBeAdded.substring(0, toBeAdded.length() - 3);
            oneResult.put("translation", toBeAdded);
            results.put(oneResult);
        }
        root.put("result", results);
        return root.toString();

    }

    @GET
    public String test() {
        return "LOL";
    }
}
