package com.googlecode.greenbridge.conversations.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import org.springframework.web.servlet.View;


/**
 * A spring view that looks for a DojoDatastore in the model, and renders it to json.
 * @author ryan
 */
public class DojoDatastoreView implements View {

    public static final String KEY = "items";
    public static final DojoDatastoreView SHARED_VIEW = new DojoDatastoreView();

    private JsonConfig jsonConfig;

    public DojoDatastoreView() {
        jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(DojoDatastore.class, new JsonBeanProcessor() {
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                if (!(bean instanceof DojoDatastore)) {
                    return new JSONObject(true);
                }
                DojoDatastore ds = (DojoDatastore) bean;
                JSONObject root = new JSONObject();
                root.element("identifier", "key");
                List items = buildNameValues(ds);
                root.element(KEY, items);
                return root;
            }
        });
    }

    private List buildNameValues(DojoDatastore ds) {
        List items = new ArrayList();
        Map nameVales = ds.getNameValues();
        if (nameVales != null) {
            for (Iterator it = nameVales.keySet().iterator(); it.hasNext(); ) {
                Object key = it.next();
                JSONObject item = new JSONObject();
                item.put("key", key);
                item.element("value", nameVales.get(key));
                items.add(item);
            }
        }
        return items;
    }


    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSON json = JSONSerializer.toJSON(model.get(KEY), jsonConfig);
        json.write(response.getWriter());
        
    }

}

