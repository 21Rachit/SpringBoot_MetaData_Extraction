package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Contact;
import com.example.repos.ContactRepository;

import java.util.List;
@RestController
public class ContactController {
	
	@Autowired
    ContactRepository itemRepo;

    @RequestMapping("/getAllContacts")
    @ResponseBody
    public List<Contact> getAllContacts(){
        return itemRepo.getAllContacts();
    }

    @RequestMapping("/getContact/{id}")
    @ResponseBody
    public Contact getContact(@PathVariable("id") int id){
        return itemRepo.getContact(id);
    }

    @RequestMapping("/addContact")
    @ResponseBody
    public String addContact(@RequestParam("name") String name,
                              @RequestParam("mobile") String mobile,@RequestParam("emailid") String emailid,@RequestParam("category") String category){
        if(itemRepo.addContact(name,mobile,emailid,category) >= 1){
            return "Contact Added Successfully";
        }else{
            return "Something went wrong !";
        }
    }
    @RequestMapping("/deteteContact/{id}")
    @ResponseBody
    public String deteteContact(@PathVariable("id") int id){
        if(itemRepo.deleteContact(id) >= 1){
            return "Contact Deleted Successfully";
        }else{
            return "Something went wrong !";
        }
    }	
    
    
    @RequestMapping("/sourceMeta")
    public void sourceMeta(){
    itemRepo.sourceMeta();
    }
    
    @RequestMapping("/getMeta")
    public void getMeta(){
    itemRepo.getMeta();
    }
    
    @RequestMapping(value = "/addColumn/{tableName}/{columnName}")
    @ResponseBody
    public void addColumn(@PathVariable("tableName") String tableName,@PathVariable("columnName") String columnName){
        itemRepo.addColumn(tableName, columnName);
    }

    
    @RequestMapping(value = "/updateColumn/{tableName}/{oldColumnName}/{oldColumnName}")
    @ResponseBody
    public void updateColumn(@PathVariable("tableName") String tableName,@PathVariable("oldColumnName") String oldColumnName,@PathVariable("newColumnName") String newColumnName){
        itemRepo.updateColumn(tableName, oldColumnName, oldColumnName);
    }
    
    @RequestMapping(value = "/deleteColumn/{tableName}/{columnName}")
    @ResponseBody
    public void deleteColumn(@PathVariable("tableName") String tableName,@PathVariable("columnName") String columnName){
        itemRepo.deleteColumn(tableName, columnName);
    }
    
    
}
