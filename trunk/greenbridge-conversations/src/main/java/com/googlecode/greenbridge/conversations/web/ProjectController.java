package com.googlecode.greenbridge.conversations.web;

import com.googlecode.greenbridge.conversations.dao.ConversationDao;
import com.googlecode.greenbridge.conversations.domain.Conversation;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.RemoteProject;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.ConversationManager;
import com.googlecode.greenbridge.conversations.manager.ConversationSearchResults;

import com.googlecode.greenbridge.conversations.manager.FreemarkerMindMapGenerator;
import com.googlecode.greenbridge.conversations.manager.MindMapParams;
import com.googlecode.greenbridge.conversations.manager.ProjectManager;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
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
public class ProjectController {
    private final Logger log = Logger.getLogger(ProjectController.class);



    private ProjectManager projectManager;
    private ConversationDao dao;


    public ProjectController(ProjectManager projectManager, ConversationDao dao) {
        this.projectManager = projectManager;
        this.dao = dao;
    }

    /**
     * list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/project/list.do")
    public final String list( ModelMap model) throws Exception {
        model.addAttribute("projects", projectManager.listAllProjects());
        return "project/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/list/json.do")
    public final ModelAndView listAllTagsAsJson() throws Exception {

        List<Project> projects = projectManager.listAllProjects();
        Map<String,String> data = new HashMap<String,String>();
        for (Project project : projects) {
            data.put(project.getId(), project.getName());
        }
        
        DojoDatastore store = new DojoDatastore(data);
        ModelAndView mav = new ModelAndView(DojoDatastoreView.SHARED_VIEW);
        mav.addObject(DojoDatastoreView.KEY, store);
        return mav;
    }


     /**
     * Get as a template
     */

    @RequestMapping(method = RequestMethod.GET, value = "/project/mm.do")
    public final void getAsTemplate(@RequestParam("name") String projectName, HttpServletRequest request, HttpServletResponse response ) throws IOException {
        Project p = projectManager.loadByName(projectName);
        List<Tag> tags = projectManager.findAllTagsForProject(p.getId());

        String meeting_name = p.getName() + "_meeting";


        List<Person> people = dao.findAllPeople();

        MindMapParams params = new MindMapParams();
        params.setProjectName(p.getName());
        params.setTags(tags);

        params.setMeetingName(meeting_name);
        params.setPeople(people);
        params.setAttending(new ArrayList<Person>());

        response.setContentType("application/x-freemind");
        response.setHeader("Content-disposition", "attachment; filename=" + meeting_name + ".mm");
        OutputStream out = response.getOutputStream();
        OutputStreamWriter stream = new OutputStreamWriter(out);
        FreemarkerMindMapGenerator generator = new FreemarkerMindMapGenerator();
        generator.generateMindmap(params, stream);

    }


    /**
     * View by an ID
     */

    @RequestMapping(method = RequestMethod.GET, value = "/project/view.do")
    public final String view(@RequestParam("id") String projectName, ModelMap model) {
        Project p = projectManager.loadByName(projectName);
        model.addAttribute("project", p);
        List<Tag> tags = projectManager.findAllTagsForProject(p.getId());
        model.addAttribute("tags", tags);
        return "project/view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project/addTag.do")
    public final String addTag(@ModelAttribute Tag tag,  ModelMap model) {

        projectManager.addProjectTag(tag);
        Project p = projectManager.loadById(tag.getProjectId());
        model.addAttribute("project", p);
        List<Tag> tags = projectManager.findAllTagsForProject(p.getId());
        model.addAttribute("tags", tags);
        return "project/view";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project/deleteTag.do")
    public final void deleteTag(@RequestParam("id") String tagId,  ModelMap model) {
        projectManager.deleteProjectTag(tagId, false);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/project/edit.do")
    public final String edit(@RequestParam("id") String projectId, HttpServletRequest request, ModelMap model) {
        //Conversation conversation = conversationManager.findById(conversationId);
        return "project/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/project/save.do")
    public final String save(@ModelAttribute Project project,
            @RequestParam(value="remote",required=false) Boolean remote,
            @RequestParam(value="remote_url",required=false) String remote_url,
            @RequestParam(value="remote_type",required=false) String remote_type,
            HttpServletRequest request, ModelMap model) {


        projectManager.saveProject(project);
        return "redirect:/project/" + project.getName() ;
    }
    /**
     * View by an ID
     */
    @RequestMapping(method = RequestMethod.GET, value = "/project/new.do")
    public final String create( HttpServletRequest request, ModelMap model) {
        //Conversation conversation = conversationManager.findById(conversationId);
        return "project/edit";
    }




}
