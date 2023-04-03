package com.epam.esm.controller;

import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Resource that provides an API for interacting with Tag entity
 *
 * @author Denis Davydov
 * @version 2.0
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    /**
     * To get tag by id
     *
     * @param id tag id
     * @return return ResponseEntity with found tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseModel> findById(@PathVariable int id) {
        TagResponseModel tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    /**
     * To get all tags
     *
     * @return return ResponseEntity with found tags
     */
    @GetMapping
    public ResponseEntity<List<TagResponseModel>> findAll() {
        List<TagResponseModel> tags = tagService.getAll();
        return ResponseEntity.ok(tags);
    }

    /**
     * To create tag
     *
     * @param tagRequestModel tag request model
     * @return return status of operation
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TagRequestModel tagRequestModel) {
        tagService.create(tagRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * To delete tag by id
     *
     * @param id tag id
     * @return return status of operation
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        tagService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
