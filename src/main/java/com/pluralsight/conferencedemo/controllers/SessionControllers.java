package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//If api v1 sessions is called this will be mapped to
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionControllers {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }// returns the list of sessions from db

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getById(id);
    }

//    @PostMapping
//    //    @ResponseStatus(HttpStatus.CREATED) -- Typical response of endpoint
//    // Think about what are the appropriate responses of get, puts, creats etc..
//    public Session creat(@RequestBody final Session session){
//        return sessionRepository.saveAndFlush(session);
//    }

    @PostMapping
    public Session creat(@RequestBody final Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        // Also need to check for children records before deleting
        // Homework implement to delete children records!
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    // Put will replace all the attributes
    // Patch will replace some of the attributes
    public Session update(@PathVariable Long id, @RequestBody Session session){
        //TODO: Add validation that all attributes are passed in , otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getById(id);
        BeanUtils.copyProperties(session, existingSession, "session_id"); // If primary key is not ignored it will be set null
                                                                                        // This will lead to exception on database server
                                                                                        // sine pk cannot be null
        return sessionRepository.saveAndFlush(existingSession);
    }

}
