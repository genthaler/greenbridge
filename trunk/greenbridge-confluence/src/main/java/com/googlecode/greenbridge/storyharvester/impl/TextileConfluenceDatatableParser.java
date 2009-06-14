/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.java.textilej.parser.Attributes;
import net.java.textilej.parser.DocumentBuilder;
import net.java.textilej.parser.DocumentBuilder.BlockType;
import net.java.textilej.parser.DocumentBuilder.SpanType;

/**
 *
 * @author ryan
 */
public class TextileConfluenceDatatableParser extends DocumentBuilder {

    boolean tableFound = false;
    boolean headerFound = false;
    BlockType currentBlock;
    List<String> cellHeaderNames = new ArrayList<String>();
    int currentColumn = 0;
    Datatable datatable;
    Map<String,String> currentRow;

    public TextileConfluenceDatatableParser() {
        datatable = new Datatable();
        datatable.setDatatableProperties(cellHeaderNames);
        List<Map<String,String>> dataset = new ArrayList<Map<String, String>>();
        datatable.setDatatable(dataset);
    }


    public Datatable getDatatable() {
        if (tableFound) return datatable;
        else return null;
    }

    @Override
    public void beginBlock(BlockType type, Attributes attributes) {
        currentBlock = type;
        if (type.equals(BlockType.TABLE)) {
            tableFound = true;
        }
        if (type.equals(BlockType.TABLE_ROW)) {
            if (headerFound) {
                currentRow = new HashMap<String, String>();
                datatable.getDatatable().add(currentRow);
                currentColumn = 0;
            }
        }
        if (type.equals(BlockType.TABLE_CELL_HEADER)) {
            headerFound = true;
        }
        if (type.equals(BlockType.TABLE_CELL_NORMAL)) {
        }
    }

    @Override
    public void endBlock() {
        if (currentBlock.equals(BlockType.TABLE_CELL_NORMAL)) {
            currentColumn++;
        }
    }

    @Override
    public void characters(String text) {
        if(currentBlock.equals(BlockType.TABLE_CELL_HEADER)) {
            cellHeaderNames.add(text.trim());
        }
        if(currentBlock.equals(BlockType.TABLE_CELL_NORMAL)) {
            String columnName = cellHeaderNames.get(currentColumn);
            String content = text.trim().replace("\\", "");
            currentRow.put(columnName, content);
        }
    }


    /*
     *
     * Rest of methods we ignore
     *
     */

    @Override
    public void beginDocument() {}

    @Override
    public void endDocument() {}

    @Override
    public void beginSpan(SpanType type, Attributes attributes) {}

    @Override
    public void endSpan() {}

    @Override
    public void beginHeading(int level, Attributes attributes) {}

    @Override
    public void endHeading() {}

    @Override
    public void entityReference(String entity) {}

    @Override
    public void image(Attributes attributes, String url) {}

    @Override
    public void link(Attributes attributes, String hrefOrHashName, String text) {}

    @Override
    public void imageLink(Attributes linkAttributes, Attributes imageAttributes, String href, String imageUrl) {}

    @Override
    public void acronym(String text, String definition) {}

    @Override
    public void lineBreak() {}

    @Override
    public void charactersUnescaped(String literal) {}

}
