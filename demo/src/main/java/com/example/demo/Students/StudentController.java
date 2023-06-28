package com.example.demo.Students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:8090")
public class StudentController {
    @Autowired
    studentRepository studentRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String healthCheck() {
        return "This is working";
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    @ResponseBody
    public Student addStudent(@RequestBody Student student) {
        System.out.println(student);
        return studentRepository.save(student);
    }

}
