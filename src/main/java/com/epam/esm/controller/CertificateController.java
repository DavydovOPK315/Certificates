package com.epam.esm.controller;

import com.epam.esm.dto.CertificateRequestModel;
import com.epam.esm.dto.CertificateResponseModel;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
     * @return return all found certificates
     */
    @GetMapping
    public ResponseEntity<List<CertificateResponseModel>> getAll() {
        List<CertificateResponseModel> certificates = certificateService.getAll();
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get certificate by id
     *
     * @param id tag id
     * @return return ResponseEntity with found certificate
     */
    @GetMapping("/{id}")
    public ResponseEntity<CertificateResponseModel> findById(@PathVariable int id) {
        CertificateResponseModel certificate = certificateService.getCertificateById(id);
        return ResponseEntity.ok(certificate);
    }

    /**
     * To get all certificates by tag name
     *
     * @param tagName tag name
     * @return return ResponseEntity with found certificates
     */
    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<CertificateResponseModel>> findAllByTagName(@PathVariable String tagName) {
        List<CertificateResponseModel> certificates = certificateService.getAllByTagName(tagName);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get all certificates by name or description
     *
     * @param request request with required parameters(name, description)
     * @return return ResponseEntity with found certificates
     */
    @GetMapping("/search")
    public ResponseEntity<List<CertificateResponseModel>> findAllByNameOrDescription(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        List<CertificateResponseModel> certificates = new ArrayList<>();

        if (name != null) {
            certificates = certificateService.getAllByName(name);
        } else if (description != null) {
            certificates = certificateService.getAllByDescription(description);
        }
        return ResponseEntity.ok(certificates);
    }

    /**
     * To get and sort all certificates by name and description
     *
     * @param request request with required parameters(dateOrder, nameOrder)
     * @return return ResponseEntity with found certificates
     */
    @GetMapping("/sort")
    public ResponseEntity<List<CertificateResponseModel>> getAllAndSortByDateAndName(HttpServletRequest request) {
        String dateOrder = request.getParameter("dateOrder");
        String nameOrder = request.getParameter("nameOrder");
        List<CertificateResponseModel> certificates = certificateService.getAllAndSortByDateAndName(dateOrder, nameOrder);
        return ResponseEntity.ok(certificates);
    }

    /**
     * To create certificate
     *
     * @param certificateRequestModel certificate request model
     * @return return status of operation
     */
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CertificateRequestModel certificateRequestModel) {
        certificateService.create(certificateRequestModel);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * To update certificate
     *
     * @param id                      id
     * @param certificateRequestModel certificate request model
     * @return return status of operation
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody CertificateRequestModel certificateRequestModel) {
        certificateService.update(id, certificateRequestModel);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}