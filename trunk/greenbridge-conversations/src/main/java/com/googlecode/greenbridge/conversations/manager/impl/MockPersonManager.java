/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.greenbridge.conversations.manager.impl;

import com.googlecode.greenbridge.conversations.domain.Person;
import com.googlecode.greenbridge.conversations.manager.PersonManager;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ryan
 */
public class MockPersonManager implements PersonManager {

    private Map<String,Person> people = new HashMap<String,Person>();

    public MockPersonManager() {
        {
            Person p = new Person();
            p.setId("1");
            p.setName("Ryan Ramage");
            p.setSlug("ryan-ramage");
            p.setEmail("rr@1234.com");
            people.put(p.getSlug(), p);
        }
        {
            Person p = new Person();
            p.setId("2");
            p.setName("Test Ramage");
            p.setSlug("test-ramage");
            p.setEmail("tr@1234.com");
            people.put(p.getSlug(), p);
        }
    }

    private Person findByEmail(String email) {
        for (Person person : people.values()) {
            if (email.equals(person.getEmail())) return person;
        }
        return null;
    }


    @Override
    public Person findPersonForText(String text) {
        if (isEmailAddress(text)) return findByEmail(text);
        return people.get(text);
    }

    @Override
    public Person createPersonForEmailText(String text) {
        Person p = new Person();
        p.setId(people.size() + 1 + "");
        String name = text.split("@")[0];
        p.setName(name);
        p.setSlug(name);
        p.setEmail(text);
        people.put(p.getSlug(), p);
        return p;
    }

    @Override
    public boolean isEmailAddress(String slug) {
       if (slug.contains("@")) return true;
       else return false;
    }

}
