package com.googlecode.greenbridge.conversations.web;

import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Media;
import com.googlecode.greenbridge.conversations.domain.MediaTag;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.AppleChapterConversationDetails;
import com.googlecode.greenbridge.conversations.manager.ConversationManager;
import com.googlecode.greenbridge.conversations.manager.ConversationSearchResults;
import com.googlecode.greenbridge.conversations.manager.FreemindConversationDetails;
import com.googlecode.greenbridge.conversations.manager.TagUpdateDetails;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ryan
 */
@Controller
public class ConversationController {
    private final Logger log = Logger.getLogger(ConversationController.class);
    private static final String TAG_EXISTING = "existing";
    private static final String TAG_NEW      = "new";


    private ConversationManager conversationManager;
    private StoreMediaStrategy storeMedia;
    private StoreMediaStrategy freemindStore;
    private SimpleDateFormat sdf;
    private int defaultPageSize = 20;
    private int defaultOffset = 1;

    public ConversationController(ConversationManager conversationManager, StoreMediaStrategy storeMedia, StoreMediaStrategy freemingStore) {
        this.conversationManager = conversationManager;
        this.storeMedia = storeMedia;
        this.freemindStore = freemingStore;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }


    /**
     * View a conversation by an ID
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/list.do")
    public final String list(@RequestParam(value="page",required=false) Integer page,
            @RequestParam(value="limit",required=false) Integer limit, ModelMap model) throws Exception {
        if (limit == null || limit == 0) {
            limit = defaultPageSize;
        }
        if (page == null ) {
            page = defaultOffset;
        }
        ConversationSearchResults results = conversationManager.listAllConversations(page, limit);
        model.put("results", results);
        Pagination pagination = new Pagination(page, limit, results.getTotalConversationInResults());
        model.put("pagination", pagination);
        return "conversation/searchresults";
    }

    /**
     * View a conversation by an ID
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/view.do")
    public final String view(@RequestParam("id") String conversationId, HttpServletRequest request, ModelMap model) {
        Conversation conversation = conversationManager.findById(conversationId);
        return commonView(conversation, 0, model);
    }

    /**
     * View a conversation by an ID, start playback at a tag position
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/viewTime.do")
    public final String viewBookmark(@RequestParam("id") String conversationId, @RequestParam("time") long time,  ModelMap model) {
        Conversation conversation = conversationManager.findById(conversationId);
        List<MediaTag> tagsSelected = findMediaTagsAtTime(conversation, time);
        model.addAttribute("tagsSelected", tagsSelected);
        return commonView(conversation, time, model);
    }

    protected final String commonView(Conversation conversation, long startTime, ModelMap model) {
        model.addAttribute("conversation", conversation);
        model.addAttribute("startTime", startTime);
        model.addAttribute("media", conversation.getMedia().iterator().next());
        model.addAttribute("mediaTags", findAllMediaTags(conversation));
        return "conversation/view";
    }

    protected List<MediaTag> findAllMediaTags(Conversation c) {
        if (c.getMedia() == null || c.getMedia().isEmpty()) {
                return null;
        }
        Media m = (Media) c.getMedia().iterator().next();
        if (m.getMediaTags() == null || m.getMediaTags().isEmpty()) {
            return null;
        }
        return m.getMediaTags() ;
    }


    protected List<MediaTag> findMediaTagsAtTime(Conversation c, long time) {
        List<MediaTag> tags = new ArrayList<MediaTag>();
        List<MediaTag> allTags = findAllMediaTags(c);
        for (MediaTag mediaTag : allTags) {
            if (mediaTag.getStartTime() <= time && mediaTag.getEndTime() >= time) {
                tags.add(mediaTag);
            }
        }
        return tags;
    }


    /**
     * Show the upload form
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/upload/form.do")
    public final String uploadForm(HttpServletRequest request, ModelMap model) {
        return "conversation/uploadForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/conversation/upload/freemind.do")
    public final String uploadFreemind(HttpServletRequest request,
            @RequestParam(value="tagStartOffset",required=false) Integer tagStartOffset,
            @RequestParam(value="tagDuration",required=false) Integer tagDuration,
            ModelMap model) throws Exception {
        if(request instanceof MultipartHttpServletRequest){
                MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
                MultipartFile conversation_data = req.getFile("audio");
                String url = storeMedia.store(conversation_data);


                MultipartFile tags_xml = req.getFile("document");
                String freemindUrl = freemindStore.store(tags_xml);

                FreemindConversationDetails details = new FreemindConversationDetails();
                details.setFreemindUrl(freemindUrl);
                details.setFreemindXMLStream(new FileInputStream(freemindStore.getStoredReferenceAsFile(freemindUrl)));
                details.setMediaUrl(url);
                details.setTagStartOffset(tagStartOffset);
                details.setTagDuration(tagDuration);
                Conversation conversation = conversationManager.newConversation(details);
                return "redirect:/conversation/" + conversation.getId() ;

        }
        return "conversation/uploadForm";
    }


    /**
     * Upload
     */
    @RequestMapping(method = RequestMethod.POST, value = "/conversation/upload/add.do")
    public final String upload( HttpServletRequest request,
            @RequestParam("name") String name,
            @RequestParam(value="description",required=false) String description,
            @RequestParam(value="date",required=true) String date,
            @RequestParam(value="time",required=true) String time,
            ModelMap model) throws Exception {
            if(request instanceof MultipartHttpServletRequest){

                MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
                MultipartFile conversation_data = req.getFile("conversation");
                String url = storeMedia.store(conversation_data);
                MultipartFile tags_xml = req.getFile("tags");

                String datetime = date + time;
                Date meetingTime = sdf.parse(datetime);

                AppleChapterConversationDetails details = new AppleChapterConversationDetails();
                details.setName(name);
                details.setAppleChapterXMLStream(tags_xml.getInputStream());
                details.setMediaUrl(url);
                details.setConversationDate(meetingTime);
                details.setDescription(description);

                Conversation conversation = conversationManager.newConversation(details);
                return "redirect:../" + conversation.getId() ;

            }
            return "conversation/uploadForm";
    }


    /**
     * Delete a tag from a conversation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/tag/view-json.do")
    public final ModelAndView viewTagAsJson(
            @RequestParam("conversation") String conversationId,
            @RequestParam("mediaTagId") String mediaTagId) {
        TagUpdateDetails tag = conversationManager.loadTag(conversationId, mediaTagId);
        ModelAndView mav = new ModelAndView(new GenericJSONView(tag));
        return mav;
    }


    /**
     * Delete a tag from a conversation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/tag/json.do")
    public final ModelAndView viewAllTagsAsJson(
            @RequestParam(required=false,value="q") String tagName) {

        Map<String,String> data = conversationManager.findAllTags();

        DojoDatastore store = new DojoDatastore(data);
        ModelAndView mav = new ModelAndView(DojoDatastoreView.SHARED_VIEW);
        mav.addObject(DojoDatastoreView.KEY, store);
        return mav;
    }

    /**
     * Delete a tag from a conversation
     */
    @RequestMapping(method = RequestMethod.GET, value = "/conversation/tag/delete.do")
    public final String deleteMediaTag(@RequestParam("mediaTagId") String mediaTagId, HttpServletRequest request, ModelMap model) {
        conversationManager.deleteTag(mediaTagId);
        model.put("tagId", mediaTagId);
        return "tags/onDelete";
    }



    /**
     * Edit a tag from a conversation
     */
    @RequestMapping(method = RequestMethod.POST, value = "/conversation/tag/edit.do")
    public final String editMediaTag(
            @RequestParam("conversation") String conversationId,
            @RequestParam("mediaTagId") String mediaTagId,
            @RequestParam("tagType") String tagType,
            @RequestParam(value="newTagName",required=false) String newTagName,
            @RequestParam(value="projectId", required=false) String projectId,
            @ModelAttribute TagUpdateDetails tag,
            HttpServletRequest request, ModelMap model) {

        if (TAG_NEW.equals(tagType)) {
            tag.setTagId(null);
            tag.setTagName(newTagName);
            tag.setTagProjectId(projectId);

        }
        conversationManager.updateTag(conversationId, mediaTagId, tag);
        model.put("tagId", mediaTagId);
        return "redirect:/conversation/" + conversationId + "/time/" + (long)tag.getStartTime();
    }

     /**
     * Create a tag for a conversation
     */
    @RequestMapping(method = RequestMethod.POST, value = "/conversation/tag/new.do")
    public final String newMediaTag(
            @RequestParam("conversation") String conversationId,
            @RequestParam("tagType") String tagType,
            @RequestParam(value="newTagName",required=false) String newTagName,
            @RequestParam(value="projectId", required=false) String projectId,
            @ModelAttribute TagUpdateDetails tag,
            HttpServletRequest request, ModelMap model) {

        if (TAG_NEW.equals(tagType)) {
            tag.setTagId(null);
            tag.setTagName(newTagName);
            tag.setTagProjectId(projectId);
        }
        conversationManager.addTag(conversationId, tag);
        return "redirect:/conversation/" + conversationId + "/time/" + (long)tag.getStartTime();
    }

}
