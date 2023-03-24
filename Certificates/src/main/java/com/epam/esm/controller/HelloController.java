package com.epam.esm.controller;

import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class HelloController {

//    @GetMapping("/hello")
//    public String hello() {
//        System.out.println("HelloController.hello");
//        return "hello";
//    }

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private TagService tagService;

//    @GetMapping("/hello")
//    @ResponseBody
//    public String hello(HttpServletResponse response) {
//        System.out.println("HelloController.hello");
//        response.setContentType("application/json");
//        Tag tag = new Tag();
//        tag.setName("Shop");
//        tagDAO.save(tag);
//        return "hello Kolymba";
//    }

//    @GetMapping("/hello")
//    @ResponseBody
//    public ResponseEntity<Tag> hello(HttpServletResponse response) {
//        System.out.println("HelloController.hello");
//        response.setContentType("application/json");
//        Tag tag = new Tag();
//        tag.setName("Shop3");
//        tag.setId(3);
//        tagDAO.save(tag);
//        return ResponseEntity.ok(tag);
//    }


//    @GetMapping("/hello")
//    @ResponseBody
//    public ResponseEntity<Object> helloMethod(HttpServletResponse response) {
//        response.setContentType("application/json");
//        return ResponseEntity.ok("Hello");
//    }
//
//
//    @GetMapping("/hello2")
//    @ResponseBody
//    public String helloMethod2(HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//        response.sendRedirect("/hello");
//        return "redirect:/hello";
//    }

//
//    @GetMapping("/tags/{id}")
////    @ResponseBody
//    public ResponseEntity<Tag> findById(@PathVariable int id, HttpServletResponse response) {
////        response.setContentType("application/json");
//        Tag tag = tagDAO.show(id);
//        return ResponseEntity.ok(tag);
//    }

    @GetMapping("/{id}")
//    @ResponseBody
    public ResponseEntity<TagResponseModel> findById(@PathVariable int id, HttpServletResponse response) {
//        response.setContentType("application/json");
        TagResponseModel tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Tag>> findAll(HttpServletResponse response) {
        response.setContentType("application/json");
        List<Tag> tags = tagDAO.index();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Tag tag, HttpServletResponse response) {
        response.setContentType("application/json");
        tagDAO.save(tag);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable int id, HttpServletResponse response) {
        response.setContentType("application/json");
        tagDAO.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
