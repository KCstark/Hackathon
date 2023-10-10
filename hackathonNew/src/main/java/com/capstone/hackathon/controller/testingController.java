package com.capstone.hackathon.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.hackathon.entities.User;
import com.capstone.hackathon.repo.UserRepo;

@RestController
@RequestMapping("/hackathon/testing")
public class testingController {
    @Autowired
    UserRepo ur;

    @PostMapping("/process")
    public String processFormData(@RequestBody User formData) {
        // Process the data or perform any backend tasks
        System.out.println("HEY!");
        System.out.println(formData.getName());
        System.out.println(formData.getEmail());
        System.out.println(formData.getPassword());
        System.out.println(formData.getRole());
        System.out.println();
        try (Scanner sc = new Scanner(System.in)) {
            var k= sc.nextLine();
            System.out.println("You wrote: "+k);
        }
        
        // Return a response back to the frontend

        return "{\"status\": \"success\", \"message\": \"Data received successfully!\"}";
        // return "NICE :)";

    }

    

}
