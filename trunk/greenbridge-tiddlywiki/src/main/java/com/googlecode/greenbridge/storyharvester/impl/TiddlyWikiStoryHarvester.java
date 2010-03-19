/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import com.googlecode.greenbridge.storyharvester.ScenarioNarrative;
import com.googlecode.greenbridge.storyharvester.StoryHarvester;
import com.googlecode.greenbridge.storyharvester.StoryNarrative;
import com.googlecode.greenbridge.util.JavaLanguageSupport;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ryan
 */
public class TiddlyWikiStoryHarvester implements StoryHarvester {

    private String tiddlyWikiFile;
    private String tiddlyWikiRemoteURL;

    @Override
    public List<StoryNarrative> gather() throws Exception {
        URL url = getSourceWikiURL();
        System.out.println("Story url: " + url.toString());
        List<Element> divs = getDivs(getSourceWikiURL());
        Map<String,StoryHolder> storyTitleToElement = convertToStoryHolderMap(divs);
        List<StoryNarrative> narratives = covnvertToStoryNarratives(storyTitleToElement);
        return narratives;
    }

    protected URL getSourceWikiURL() throws MalformedURLException {
        URL url = null;
        try {
            url = new URL(tiddlyWikiFile);
        } catch (Exception e) {
            url = new URL("file:/" + tiddlyWikiFile);
        }
        return url;

    }

    protected String getLinkWikiURL()  {
        if (tiddlyWikiRemoteURL != null) return tiddlyWikiRemoteURL;
        try {
            URL url = new URL(tiddlyWikiFile);
            return tiddlyWikiFile;
        } catch (Exception e) {
            try {
                URL url = new URL("file://" + tiddlyWikiFile);
                return url.toURI().toURL().toString();
            } catch (Exception ex) {
                return "file://" + tiddlyWikiFile;
            }
            
        }

    }



    private List<StoryNarrative> covnvertToStoryNarratives(Map<String, StoryHolder> storyTitleToElement) {
        List<StoryNarrative> storyNarratives = new ArrayList<StoryNarrative>();
        for (StoryHolder storyHolder : storyTitleToElement.values()) {
            StoryNarrative narrative = new StoryNarrative();
            narrative.setId(safeID(storyHolder.getName()));
            narrative.setStoryNarrative(parseStoryNarrative(storyHolder.getSource()));
            narrative.setVersion(getChangeCount(storyHolder.getSource()));
            narrative.setLinkName("See Scenario on the Wiki");
            narrative.setLinkUrl(getLinkWikiURL() + "#" + storyHolder.getName());
            narrative.setScenarios(harvestScenarios(storyHolder, narrative));
            storyNarratives.add(narrative);
        }
        return storyNarratives;
    }


    protected List<ScenarioNarrative> harvestScenarios(StoryHolder storyHolder, StoryNarrative story)  {
        List <ScenarioNarrative> scenarios = new ArrayList<ScenarioNarrative>();
        List<Element> sourceScenarios = storyHolder.getScenarios();
        for (Element sourceScenario : sourceScenarios) {
            ScenarioNarrative scenario = harvestScenario(sourceScenario);
            scenario.setStoryNarrative(story);
            scenarios.add(scenario);
        }
        return scenarios;
    }


    protected ScenarioNarrative harvestScenario(Element source) {
        ScenarioNarrative scenario = new ScenarioNarrative(parseStoryNarrative(source));
        String title = source.attributeValue("title");
        scenario.setId(safeID(title));
        scenario.setLinkName("See Scenario on the Wiki");
        scenario.setLinkUrl(getLinkWikiURL() + "#" + title);
        scenario.setVersion(getChangeCount(source));
        parsePossibleTable(scenario, source);
        return scenario;
    }

    protected void parsePossibleTable(ScenarioNarrative scenario, Element source) {
        String text = source.elementText("pre");
        String[] sections = text.split("----");
        if (sections.length == 0 || sections.length == 1) return;
        for (int i = 1; i < sections.length; i++) {
            String section = sections[i];
            try {
                List<String> tableCols = findDataTableColumns(section);

                if (tableCols != null && tableCols.size() > 0) {
                    scenario.setDatatableProperties(tableCols);
                    scenario.setDatatable(createDataTable(tableCols,section));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } // ignore. means no table
        }
    }


    protected List<Map<String,String>> createDataTable(final List<String> tableCols, String section) {
        List<Map<String,String>> rows = new ArrayList<Map<String, String>>();
        String[] rows_text = section.trim().split("\n");
        for (int i = 1; i < rows_text.length; i++) {
            String row_text = rows_text[i];
            String[] columns   = row_text.split("\\|");
            Map<String,String> row_map = new HashMap<String, String>();
            for (int j = 1; j < columns.length; j++) {
                String col_val = columns[j];
                
                String col_name = tableCols.get(j - 1);
                row_map.put(col_name, col_val);
            }
            rows.add(row_map);
        }
        return rows;
    }


    protected List<String> findDataTableColumns(String section) {
        String headerRow = section.trim().split("\n")[0];
        String[] columns   = headerRow.split("\\|");
        List<String> col_list = new ArrayList<String>(Arrays.asList(columns));
        // because the first col will always be empty
        col_list.remove(0);

        // Tiddlywiki tables can end in a h, this means header. This should be eliminated
        if (col_list.get(col_list.size() - 1).equals("h")) {
            col_list.remove(col_list.size() - 1);
        }
        return col_list;

    }





    protected int getChangeCount(Element source) {
        String changecount  = source.attributeValue("changecount");
        if (changecount == null) return 0;
        else return Integer.valueOf(changecount);
    }

    protected List<String> parseStoryNarrative(Element source) {
        String text = source.elementText("pre");
        String[] sections = text.split("----");
        return Arrays.asList(sections[0].split("\n"));
    }
    


    protected Map<String,StoryHolder> convertToStoryHolderMap(List<Element> divs) {
        Map<String,StoryHolder> storyTitleToElement = new HashMap<String, StoryHolder>();

        // first pass to collect all the stories
        for (Element div : divs) {
            String tags = div.attributeValue("tags");
            String title = div.attributeValue("title");
            if (tags != null && ArrayUtils.contains(tags.split(" "), "Story")) {
                StoryHolder holder = new StoryHolder();
                holder.setName(title);
                holder.setSource(div);
                storyTitleToElement.put(title, holder);
            }
        }

        // second pass we get all the scenarios
        for (Element div : divs) {
            String tags = div.attributeValue("tags");
            if (tags != null && ArrayUtils.contains(tags.split(" "), "Scenario")) {
                StoryHolder holder = findStoryForTags(tags, storyTitleToElement);
                if (holder != null) {
                    holder.getScenarios().add(div);
                }
            }
        }
        return storyTitleToElement;
    }


    protected StoryHolder findStoryForTags(String tags, Map<String,StoryHolder> storyTitleToElement) {
        String[] tagx = tags.split(" ");
        for (int i = 0; i < tagx.length; i++) {
            String tag = tagx[i];
            if (storyTitleToElement.containsKey(tag)) return storyTitleToElement.get(tag);
        }
        return null;
    }




    protected List<Element> getDivs(URL f) throws DocumentException {
        EntityResolver resolver = new EntityResolver() {
            public InputSource resolveEntity(String publicId, String systemId) {
                if ( publicId.equals( "-//W3C//DTD XHTML 1.0 Strict//EN" ) ) {
                    InputStream in = getClass().getResourceAsStream(
                        "/xhtml1-strict.dtd"
                    );
                    return new InputSource( in );
                }
                if ( publicId.equals( "-//W3C//ENTITIES Latin 1 for XHTML//EN" ) ) {
                    InputStream in = getClass().getResourceAsStream(
                        "/xhtml-lat1.ent"
                    );
                    return new InputSource( in );
                }
                if ( publicId.equals( "-//W3C//ENTITIES Symbols for XHTML//EN" ) ) {
                    InputStream in = getClass().getResourceAsStream(
                        "/xhtml-symbol.ent"
                    );
                    return new InputSource( in );
                }
                if ( publicId.equals( "-//W3C//ENTITIES Special for XHTML//EN" ) ) {
                    InputStream in = getClass().getResourceAsStream(
                        "/xhtml-special.ent"
                    );
                    return new InputSource( in );
                }
                return null;
            }
        };
        SAXReader reader = new SAXReader();
        reader.setIncludeExternalDTDDeclarations(false);
        reader.setValidation(false);
        reader.setEntityResolver( resolver );

        Document document =  reader.read(f);
        Element root = document.getRootElement();

        Element body = root.element("body");
        List<Element> divs = body.elements();
        for (Element div : divs) {
            String id = div.attributeValue("id");
            if (id != null && id.equals("storeArea")) {
                return div.elements();
            }
        }
        return new ArrayList<Element>();
    }

    protected String safeID(String title) {
        return JavaLanguageSupport.makeJavaIdentifier(title);
    }


    /**
     * @return the tiddlyWikiURL
     */
    public String getTiddlyWikiFile() {
        return tiddlyWikiFile;
    }

    /**
     * @param tiddlyWikiURL the tiddlyWikiURL to set
     */
    public void setTiddlyWikiFile(String tiddlyWikiFile) {
        this.tiddlyWikiFile = tiddlyWikiFile;
    }

    /**
     * @return the tiddlyWikiRemoteURL
     */
    public String getTiddlyWikiRemoteURL() {
        return tiddlyWikiRemoteURL;
    }

    /**
     * @param tiddlyWikiRemoteURL the tiddlyWikiRemoteURL to set
     */
    public void setTiddlyWikiRemoteURL(String tiddlyWikiRemoteURL) {
        this.tiddlyWikiRemoteURL = tiddlyWikiRemoteURL;
    }



}
