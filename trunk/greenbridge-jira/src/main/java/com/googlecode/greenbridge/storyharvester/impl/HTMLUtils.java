/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.storyharvester.impl;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;

import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.MutableAttributeSet;
import org.apache.commons.lang.StringEscapeUtils;

public class HTMLUtils {
  private HTMLUtils() {}

  public static List<String> extractText(Reader reader) throws IOException {
    final ArrayList<String> list = new ArrayList<String>();

    ParserDelegator parserDelegator = new ParserDelegator();
    ParserCallback parserCallback = new ParserCallback() {
      public void handleText(final char[] data, final int pos) {
        String dataStr = new String(data);
        dataStr = StringEscapeUtils.escapeJava(dataStr);
        list.add(dataStr);
      }
      public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) { }
      public void handleEndTag(Tag t, final int pos) {  }
      public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) { }
      public void handleComment(final char[] data, final int pos) { }
      public void handleError(final java.lang.String errMsg, final int pos) { }
    };
    parserDelegator.parse(reader, parserCallback, true);
    return list;
  }

}

