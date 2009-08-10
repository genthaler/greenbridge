
package com.googlecode.greenbridge.conversations.web;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.manager.AppleChapterConversationDetails;
import com.googlecode.greenbridge.conversations.manager.ConversationManager;
import com.googlecode.greenbridge.conversations.manager.ConversationSearchResults;
import com.googlecode.greenbridge.conversations.manager.MediaTagManager;
import com.googlecode.greenbridge.conversations.manager.MediaTagSearchResults;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;


/**
 *
 * @author ryan
 */
@Controller
public class SearchController {

    private MediaTagManager mediaTagManager;

    public SearchController(MediaTagManager mediaTagManager) {
        this.mediaTagManager = mediaTagManager;
    }

    /**
     * View all conversations by a tag, with no tag group 
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchGeneralTag.do")
    public final String searchGeneralTag( HttpServletRequest request, ModelMap model,
            @RequestParam(value="tagName",required=false) String tagName,
            @RequestParam(value="page",required=false) Integer page,
            @RequestParam(value="limit",required=false) Integer limit) throws Exception {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 20;
        }

        MediaTagSearchResults results = mediaTagManager.searchForGlobalTags(tagName, page, limit);
        model.put("results", results);
        Pagination pagination = new Pagination(page, limit, results.getTotalMediaTagsInResults());
        model.put("pagination", pagination);
        return "search/results";
    }

    /**
     * View all conversations by a tag, with no tag group
     */
    @RequestMapping(method = RequestMethod.GET, value = "/searchGroupTag.do")
    public final String searchGroupTag( HttpServletRequest request, ModelMap model,
            @RequestParam(value="tagName",required=false) String tagName,
            @RequestParam(value="tagGroupName",required=false) String tagGroupName,
            @RequestParam(value="page",required=false) Integer page,
            @RequestParam(value="limit",required=false) Integer limit) throws Exception {

        MediaTagSearchResults results = mediaTagManager.searchForGroupTags(tagName, tagGroupName, page, limit);
        model.put("results", results);
        Pagination pagination = new Pagination(page, limit, results.getTotalMediaTagsInResults());
        model.put("pagination", pagination);
        return "search/results";
    }



    
    /**
     * list all tags
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tag/list.do")
    public final String list(@RequestParam("name") long tagName, HttpServletRequest request, ModelMap model) {

        return "tag/list";
    }






}
