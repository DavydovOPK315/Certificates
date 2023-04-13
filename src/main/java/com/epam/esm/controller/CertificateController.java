package com.epam.esm.controller;

import com.epam.esm.dto.CertificateRequestModel;
import com.epam.esm.dto.CertificateResponseModel;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Resource that provides an API for interacting with Certificate entity
 *
 * @author Denis Davydov
 * @version 2.0
 */
@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

    /**
     * To get all certificates
     *
     * @return all found certificates
     */
    @GetMapping
    public ResponseEntity<List<CertificateResponseModel>> getAll(@RequestParam(defaultValue = "1") int pageNumber,
                                                                 @RequestParam(defaultValue = "20") int pageSize) {
        List<CertificateResponseModel> certificates = certificateService.getAll(pageNumber, pageSize);
        certificates.forEach(this::generateHateoas);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get certificate by id
     *
     * @param id certificate id
     * @return ResponseEntity with found certificate
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateResponseModel> getById(@PathVariable long id) {
        CertificateResponseModel certificate = certificateService.getCertificateById(id);
        generateHateoas(certificate);
        return ResponseEntity.ok(certificate);
    }

    /**
     * To get all certificates by tag name
     *
     * @param tagName tag name
     * @return ResponseEntity with found certificates
     */
    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<CertificateResponseModel>> getAllByTagName(@PathVariable String tagName,
                                                                          @RequestParam(defaultValue = "1") int pageNumber,
                                                                          @RequestParam(defaultValue = "20") int pageSize) {
        List<CertificateResponseModel> certificates = certificateService.getAllByTagName(tagName, pageNumber, pageSize);
        certificates.forEach(this::generateHateoas);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get all certificates by name or description
     *
     * @param name        name
     * @param description description
     * @return ResponseEntity with found certificates
     */
    @GetMapping("/search/name-or-description")
    public ResponseEntity<List<CertificateResponseModel>> getAllByNameOrDescription(@RequestParam(required = false) String name,
                                                                                    @RequestParam(required = false) String description,
                                                                                    @RequestParam(defaultValue = "1") int pageNumber,
                                                                                    @RequestParam(defaultValue = "20") int pageSize) {
        List<CertificateResponseModel> certificates = new ArrayList<>();

        if (name != null) {
            certificates = certificateService.getAllByName(name, pageNumber, pageSize);
        } else if (description != null) {
            certificates = certificateService.getAllByDescription(description, pageNumber, pageSize);
        }
        certificates.forEach(this::generateHateoas);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get all certificates by several tags
     *
     * @param tags tags
     * @return ResponseEntity with found certificates
     */
    @GetMapping("/search/tags-names/{tags}")
    public ResponseEntity<List<CertificateResponseModel>> getAllByTagsNames(@PathVariable List<String> tags,
                                                                            @RequestParam(defaultValue = "1") int pageNumber,
                                                                            @RequestParam(defaultValue = "20") int pageSize) {
        List<CertificateResponseModel> certificates = certificateService.getAllByTagsNames(tags, pageNumber, pageSize);
        certificates.forEach(this::generateHateoas);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get and sort all certificates by name and description
     *
     * @param dateOrder order date
     * @param nameOrder order name
     * @return ResponseEntity with found certificates
     */
    @GetMapping("/sort/date-name")
    public ResponseEntity<List<CertificateResponseModel>> getAllAndSortByDateAndName(@RequestParam(defaultValue = "asc") String dateOrder,
                                                                                     @RequestParam(defaultValue = "asc") String nameOrder,
                                                                                     @RequestParam(defaultValue = "1") int pageNumber,
                                                                                     @RequestParam(defaultValue = "20") int pageSize) {
        List<CertificateResponseModel> certificates = certificateService.getAllAndSortByDateAndName(dateOrder, nameOrder, pageNumber, pageSize);
        certificates.forEach(this::generateHateoas);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To create certificate
     *
     * @param certificateRequestModel certificate request model
     * @return status of operation
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody CertificateRequestModel certificateRequestModel) {
        certificateService.create(certificateRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * To update certificate
     *
     * @param id                      id
     * @param certificateRequestModel certificate request model
     * @return status of operation
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> update(@PathVariable long id,
                                         @RequestBody CertificateRequestModel certificateRequestModel) {
        certificateService.update(id, certificateRequestModel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * To delete certificate
     *
     * @param id id
     * @return status of operation
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> delete(@PathVariable int id) {
        certificateService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void generateHateoas(CertificateResponseModel certificate) {
        Link selfLink = linkTo(CertificateController.class).slash(certificate.getId()).withSelfRel();
        Link allCertificatesLink = linkTo(methodOn(CertificateController.class)
                .getAll(1, 20)).withRel("allCertificates");
        Link allCertificatesByTagLink = linkTo(methodOn(CertificateController.class)
                .getAllByTagName("tag1", 1, 20)).withRel("allCertificatesByTag");
        certificate.add(selfLink, allCertificatesLink, allCertificatesByTagLink);
    }
}