package com.googlecode.greenbridge.conversations.web;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import org.springframework.web.servlet.View;

/**
 * A spring view that renders all the objects in the model to JSON.
 * @author ryan
 */
public class GenericJSONView implements View {

    public static final GenericJSONView SHARED_VIEW = new GenericJSONView();
    final private Object toSerialize;

    public GenericJSONView() {
        this.toSerialize = null;
    }

    /**
     * Use this constructor to serialize a particular object, instead of the whole
     * model map
     *
     * @param toSerialize
     */
    public GenericJSONView(Object toSerialize) {
        this.toSerialize = toSerialize;
    }

    @Override
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @Override
    public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object o = toSerialize != null ? toSerialize : model;
        JSON json = JSONSerializer.toJSON(o);
        json.write(response.getWriter());
    }
}

