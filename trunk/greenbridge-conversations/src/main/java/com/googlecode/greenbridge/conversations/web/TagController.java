package com.googlecode.greenbridge.conversations.web;


import com.googlecode.greenbridge.conversations.domain.Project;
import com.googlecode.greenbridge.conversations.domain.Tag;
import com.googlecode.greenbridge.conversations.manager.TagManager;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author ryan
 */
@Controller
public class TagController {

    private TagManager tagManager;

    public TagController(TagManager tagManager) {
        this.tagManager = tagManager;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tag/list.do")
    public String list(ModelMap model) {
        List<Tag> globalTags = tagManager.listAllGlobalTags();
        model.addAttribute("globalTags", globalTags);

        //List<Project> projects = tagManager.listAllProjectsWithTags();
        //model.addAttribute("projects", projects);
        return "tags/list";
    }


}
