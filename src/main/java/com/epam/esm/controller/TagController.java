package com.epam.esm.controller;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagDAO tagDAO;
    private final TagService tagService;

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseModel> findById(@PathVariable int id) {
        TagResponseModel tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<List<Tag>> findAll() {
        List<Tag> tags = tagDAO.index();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Tag tag) {
        tagDAO.save(tag);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        tagDAO.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
