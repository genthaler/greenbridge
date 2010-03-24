package com.googlecode.greenbridge.conversations.web;


import com.googlecode.greenbridge.conversations.dao.ConversationDao;
import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.manager.ConversationManager;
import com.googlecode.greenbridge.conversations.manager.MediaTagSearchResults;
import com.googlecode.greenbridge.conversations.manager.PersonManager;
import com.googlecode.greenbridge.conversations.manager.SlugGenerator;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author ryan
 */
@Controller
public class PersonController {

    private PersonManager personnManager;
    private ConversationDao dao;
    private SlugGenerator slug;

    public PersonController(PersonManager personnManager, ConversationDao dao,  SlugGenerator slug) {
        this.personnManager = personnManager;
        this.dao = dao;
        this.slug = slug;
    }


    /**
     * list
     */
    @RequestMapping(method = RequestMethod.GET, value = "/person/list.do")
    public final String list( ModelMap model) throws Exception {
        model.addAttribute("people", dao.findAllPeople());
        return "person/list";
    }

    /**
     * Single person page
     */
    @RequestMapping(method = RequestMethod.GET, value = "/person/view.do")
    public final String view(@RequestParam("slug")  String slug, ModelMap model) throws Exception {

        Person p = dao.findBySlug(slug);
        model.addAttribute("person", p);
        MediaTagSearchResults results = personnManager.searchForPersonTags(p.getId(), null, null, 0, 100);
        model.put("results", results);
        Pagination pagination = new Pagination(0, 100, results.getTotalMediaTagsInResults());
        model.put("pagination", pagination);
        return "person/view";
    }

   private String generateUUID() {
       return UUID.randomUUID().toString();
   }
    /**
     * list
     */
    @RequestMapping(method = RequestMethod.POST, value = "/person/add.do")
    public final String view( ModelMap model, 
                              @RequestParam("name")  String name,
                              @RequestParam("email") String email
            ) throws Exception {

        
        Person p = new Person();
        p.setSlug(slug.generateSlug(name));
        p.setName(name);
        p.setEmail(email);
        p.setId(generateUUID());
        dao.savePerson(p);

        return list(model);
    }
}