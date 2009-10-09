/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager;

import com.googlecode.greenbridge.conversations.domain.ConversationTemplate;
import com.googlecode.greenbridge.conversations.domain.Project;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryan
 */
public class FreemarkerMindMapGenerator {

    public static final String FREEMIND_TEMPLATE      = "MindmapTemplate.ftl";
    private Configuration config;

    public FreemarkerMindMapGenerator() {
        config = new Configuration();
        config.setClassForTemplateLoading(FreemarkerMindMapGenerator.class, "/");
        config.setObjectWrapper(new DefaultObjectWrapper());
    }

    public void generateMindmap(MindMapParams params, Writer out) {

        Map root = new HashMap();
        root.put("params", params);
        
        Template t;
        try {
            t = config.getTemplate(FREEMIND_TEMPLATE);
            t.process(root, out);
        } catch (Exception ex) {
            Logger.getLogger(FreemarkerMindMapGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



}
