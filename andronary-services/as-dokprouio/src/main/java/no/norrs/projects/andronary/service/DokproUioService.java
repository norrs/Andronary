package no.norrs.projects.andronary.service;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.norrs.projects.andronary.service.utils.HttpUtil;
import no.norrs.projects.andronary.service.utils.iAndronaryService;
import org.apache.http.HttpResponse;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 *
 * @author rockj
 */
public class DokproUioService implements iAndronaryService {

    public enum Dicts implements no.norrs.projects.andronary.service.utils.Dictionary {

        nbNO, nnNO;
    };
    private Dicts selectedDict = null;
    private final String apiUrl = "http://www.dokpro.uio.no/perl/ordboksoek/ordbok.cgi";
    //?OPP=jul&begge=S%F8k+i+begge+ordb%F8kene&ordbok=begge&s=n
    //http://www.dokpro.uio.no/perl/ordboksoek/ordbok.cgi?OPP=jul&begge=S%F8k+i+begge+ordb%F8kene&ordbok=begge&s=n
    //http://www.dokpro.uio.no/perl/ordboksoek/ordbok.cgi?OPP=jul&bokmaal=S%F8k+i+Bokm%E5lsordboka&ordbok=bokmaal&alfabet=n&renset=j

    public DokproUioService() {
        selectedDict = Dicts.nbNO;
    }

    public DokproUioService(Dicts d) {
        selectedDict = d;
    }

    public boolean selectDictionary(no.norrs.projects.andronary.service.utils.Dictionary dict) {
        try {
            selectedDict = (Dicts) dict;
            return true;
        } catch (ClassCastException cce) {
            // Do "nothing"
        }
        return false;
    }

    public no.norrs.projects.andronary.service.utils.Dictionary getSelectedDictionary() {
        return selectedDict;
    }

    public no.norrs.projects.andronary.service.utils.Dictionary[] getDictionaries() {
        return Dicts.values();
    }

    public String getProvider() {
        return "Dokumentasjonsprosjektet/EDD (dokpro.uio.no)";
    }

    public HashMap<String, List<String>> lookup(String dict) {
        HashMap<String, List<String>> results = new HashMap<String, List<String>>();
        try {
            HttpResponse response;
            switch (selectedDict) {
                case nbNO: {
                    response = HttpUtil.GET(new URL(String.format("%s?OPP=%s&ordbok=%s&s=%s", "http://www.dokpro.uio.no/perl/ordboksoek/ordbok.cgi", dict, "bokmaal", "n")), null);
                    break;
                }
                case nnNO: {
                    response = HttpUtil.GET(new URL(String.format("%s?OPP=%s&ordbok=%s&s=%s", "http://www.dokpro.uio.no/perl/ordboksoek/ordbok.cgi", dict, "nynorsk", "n")), null);
                    break;
                }
                default:
                    return null;
            }
            String responseString = HttpUtil.readString(response.getEntity().getContent(), "ISO-8859-1");

            //System.out.println(responseString);
            Parser parser = new Parser(responseString);

            NodeList list = parser.parse(new AndFilter(new TagNameFilter("TR"), new HasAttributeFilter("valign", "top")));

            // refuses to find colspan 2        Tag (1665[0,1665],1694[0,1694]): TD align="left" colspan="2"   LAME
            // Problems with parser like finding attribute "colspan" etc, makes filtering annoying.
            // Apply ugly results hack begins here:
            //@todo GET A CLEANER SOLUTION ....


            Node[] nodes = list.toNodeArray();

            // Skipping first and 2 last results by the filter, static content not removed by filter.
            for (int i = 1; i < nodes.length - 2; i++) {
                Node test = nodes[i].getFirstChild().getFirstChild();

                if (test.getParent().getNextSibling().getFirstChild().getText().equals("TABLE")) {
                    LinkedList<String> dataToAdd = new LinkedList<String>(Arrays.asList(test.getParent().getNextSibling().toPlainTextString().split("&nbsp;&nbsp")));
                    String topic = dataToAdd.getFirst().trim();
                    dataToAdd.removeFirst();
                    results.put(topic, dataToAdd);
                }

            }

            //System.out.println("Results: " + results.size());
        } catch (URISyntaxException ex) {
            Logger.getLogger(DokproUioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserException ex) {
            Logger.getLogger(DokproUioService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DokproUioService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }

    /*
    public static void main(String[] args) {
    DokproUioService service = new DokproUioService();
    HashMap<String, List<String>> results = service.lookup("jul");

    Iterator i = results.entrySet().iterator();
    while (i.hasNext()) {
    Entry<String, List<String>> entry = (Entry<String, List<String>>) i.next();

    System.out.println(entry.getKey());
    for (String s : entry.getValue()) {
    System.out.println("-> " + s);
    }
    }


    }
     * 
     */
}
