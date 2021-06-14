package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerControllers {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getById(id);
    }


    @PostMapping
    public Speaker creat(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        // Also need to check for children records before deleting
        // Homework implement to delete children records!
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    // Put will replace all the attributes
    // Patch will replace some of the attributes
    public Speaker update(@PathVariable Long id, @RequestBody Session session){
        //TODO: Add validation that all attributes are passed in , otherwise return a 400 bad payload
        Speaker existingSession = speakerRepository.getById(id);
        BeanUtils.copyProperties(session, existingSession, "speaker_id"); // If primary key is not ignored it will be set null
                                                                                        // This will lead to exception on database server
                                                                                        // sine pk cannot be null
        return speakerRepository.saveAndFlush(existingSession);
    }


}
