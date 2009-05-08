/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rsquared.annob.storyharvester.impl;

import java.util.List;
import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.markup.confluence.ConfluenceDialect;

/**
 *
 * @author ryan
 */
public class TextileConfluenceContentParseStrategy implements ConfluenceContentParseStrategy {

    DefaultConfluenceContentParseStrategy defaultDelegate;
    public TextileConfluenceContentParseStrategy() {
        defaultDelegate = new DefaultConfluenceContentParseStrategy();
    }

    @Override
    public List<String> getNarrative(String content) {
        return defaultDelegate.getNarrative(content);
    }

    @Override
    public Datatable getDatatable(String content) {
        TextileConfluenceDatatableParser datatableBuilder = new TextileConfluenceDatatableParser();
        MarkupParser parser = new MarkupParser(new ConfluenceDialect());
        parser.setBuilder(datatableBuilder);
        parser.parse(content);
        return datatableBuilder.getDatatable();
    }

}
