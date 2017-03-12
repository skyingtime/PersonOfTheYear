package com.controller;

import com.cache.NomineeStore;
import com.model.Nominee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;

/**
 * Created by yangliu on 18/12/2016.
 */
@RestController
@RequestMapping(value="/api")
public class Controller {

    @RequestMapping(value = "nominees", method = RequestMethod.GET)
    public ResponseEntity<Collection<Nominee>> getAllNominees() {
        Collection<Nominee> nominees = NomineeStore.getAllNominees();
        if(nominees != null && !nominees.isEmpty()) {
            System.out.println("Fetch all Nominees.");
            return new ResponseEntity<>(nominees, HttpStatus.OK);
        }
        else {
            System.out.println("List of Nominees can not be found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "nominee/{key}", method = RequestMethod.GET)
    public ResponseEntity<Nominee> getSpecificNominee(@PathVariable String key) throws ParseException {
        Nominee nominee = NomineeStore.getNominee(key);
        if(nominee != null) {
            System.out.println("Fetch Nominee " + nominee.getLastName());
            return new ResponseEntity<>(nominee, HttpStatus.OK);
        }
        else {
            System.out.println("Nominee can not be found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "nominee/{key}", method = RequestMethod.PUT)
    public ResponseEntity<Nominee> modifyNominee(@PathVariable String key, @RequestBody Nominee nominee) {
        if(NomineeStore.updateNominee(key, nominee)) {
            System.out.println("Nominee " + nominee.getLastName() + " has been updated successfully.");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            System.out.println("Nominee " + nominee.getLastName() + " can not be found.");
            return   new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "nominee", method = RequestMethod.POST)
    public ResponseEntity<Nominee> addNominee(@RequestBody Nominee nominee) {
        if(NomineeStore.addNominee(nominee.generateKey(), nominee)) {
            System.out.println("Nominee " + nominee.getLastName() + " has been saved successfully.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            System.out.println("Nominee " + nominee.getLastName() + " already exist.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "nominee/{key}", method = RequestMethod.DELETE)
    public ResponseEntity<Nominee> deleteNominee(@PathVariable String key) throws ParseException {
        if(NomineeStore.deleteNominee(key)) {
            System.out.println("Nominee has been deleted.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            System.out.println("Nominee can not be found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
