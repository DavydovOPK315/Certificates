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
     * To get all tags
     *
     * @return ResponseEntity with found tags
     */
    @GetMapping
    public ResponseEntity<List<TagResponseModel>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                         @RequestParam(defaultValue = "20") int pageSize) {
        List<TagResponseModel> tags = tagService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok(tags);
    }

    /**
     * To get tag by id
     *
     * @param id tag id
     * @return ResponseEntity with found tag
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagResponseModel> getById(@PathVariable long id) {
        TagResponseModel tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    /**
     * Get the most widely used tag of a user with the highest cost of all orders
     *
     * @return ResponseEntity with found tag
     */
    @GetMapping("/widely-used")
    public ResponseEntity<TagResponseModel> getMostWidelyUsedTagOfUserWithHighestCostOfAllOrders() {
        TagResponseModel tag = tagService.getMostWidelyUsedTagOfUserWithHighestCostOfAllOrders();
        return ResponseEntity.ok(tag);
    }

    /**
     * To create tag
     *
     * @param tagRequestModel tag request model
     * @return status of operation
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody TagRequestModel tagRequestModel) {
        tagService.create(tagRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * To delete tag by id
     *
     * @param id tag id
     * @return status of operation
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> delete(@PathVariable long id) {
        tagService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
