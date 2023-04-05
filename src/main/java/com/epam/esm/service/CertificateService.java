package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateRequestModel;
import com.epam.esm.dto.CertificateResponseModel;
import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateDAO certificateDAO;
    private final ModelMapper modelMapper;
    private final TagService tagService;
    private final CertificateHasTagService certificateHasTagService;

    @Transactional
    public void create(CertificateRequestModel certificateRequestModel) {
        Certificate certificate = modelMapper.map(certificateRequestModel, Certificate.class);
        String date = LocalDateTime.now().toString();
        certificate.setCreateDate(date);
        certificate.setLastUpdateDate(date);

        certificateDAO.create(certificate);
        Certificate c = certificateDAO.getByName(certificate.getName());
        List<TagRequestModel> newTagRequestModels = certificateRequestModel.getTagRequestModels();

        if (newTagRequestModels == null || newTagRequestModels.isEmpty()) {
            return;
        }
        newTagRequestModels.stream()
                .filter(t1 -> tagService.getAll()
                        .stream()
                        .noneMatch(t2 -> t1.getName().equals(t2.getName())))
                .forEach(tagService::create);

        List<TagResponseModel> boundTagRequestModels = tagService.getAll()
                .stream()
                .filter(t -> newTagRequestModels
                        .stream()
                        .anyMatch(t2 -> t.getName().equals(t2.getName())))
                .collect(Collectors.toList());
        boundTagRequestModels.forEach(t -> certificateHasTagService.create(c.getId(), t.getId()));
    }

    @Transactional
    public void delete(int id) {
        Certificate certificate = certificateDAO.getById(id);

        if (certificate == null) {
            throw new ResourceNotFoundException("Certificate not exist with id: " + id);
        }
        certificateHasTagService.delete(id);
        certificateDAO.delete(id);
    }

    @Transactional
    public void update(int id, CertificateRequestModel certificateRequestModel) {
        Certificate certificate = certificateDAO.getById(id);

        if (certificate == null) {
            throw new ResourceNotFoundException("Certificate not exist with id: " + id);
        }
        modelMapper.map(certificateRequestModel, certificate);
        certificateHasTagService.delete(id);
        List<TagRequestModel> newTags = certificateRequestModel.getTagRequestModels();

        if (newTags != null && newTags.size() > 0) {
            List<TagResponseModel> actualTagResponseModels = tagService.getAll();
            newTags.stream()
                    .filter(t1 -> actualTagResponseModels
                            .stream()
                            .noneMatch(t2 -> t1.getName().equals(t2.getName())))
                    .forEach(tagService::create);

            List<TagResponseModel> tagsToAdd = tagService.getAll()
                    .stream().filter(t1 -> newTags
                            .stream()
                            .anyMatch(t2 -> t1.getName().equals(t2.getName())))
                    .collect(Collectors.toList());
            tagsToAdd.forEach(tag -> certificateHasTagService.create(id, tag.getId()));
        }
        String date = LocalDateTime.now().toString();
        certificate.setId(id);
        certificate.setLastUpdateDate(date);
        certificateDAO.update(certificate);
    }

    public CertificateResponseModel getCertificateById(int id) {
        Certificate certificate = certificateDAO.getById(id);

        if (certificate == null) {
            throw new ResourceNotFoundException("Certificate not exist with id: " + id);
        }
        CertificateResponseModel certificateResponseModel = modelMapper.map(certificate, CertificateResponseModel.class);
        List<TagResponseModel> tagResponseModels = tagService.getTagsByCertificateId(certificate.getId());
        certificateResponseModel.setTagResponseModels(tagResponseModels);
        return certificateResponseModel;
    }

    public List<CertificateResponseModel> getAll() {
        List<Certificate> certificateList = certificateDAO.getAll();
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByTagName(String tagName) {
        List<Certificate> certificateList = certificateDAO.getAllByTagName(tagName);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByName(String name) {
        List<Certificate> certificateList = certificateDAO.getAllByName(name);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByDescription(String description) {
        List<Certificate> certificateList = certificateDAO.getAllByDescription(description);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllAndSortByDateAndName(String dateOrder, String nameOrder) {
        List<Certificate> certificateList;
        List<CertificateResponseModel> resultList = new ArrayList<>();
        String value = dateOrder + " " + nameOrder;

        switch (value.toLowerCase()) {
            case ("desc desc"):
                certificateList = certificateDAO.getAllAndSortByDateDescAndNameDesc();
                break;
            case ("asc asc"):
                certificateList = certificateDAO.getAllAndSortByDateAscAndNameAsc();
                break;
            case ("desc asc"):
                certificateList = certificateDAO.getAllAndSortByDateDeskAndNameAsc();
                break;
            case ("asc desc"):
                certificateList = certificateDAO.getAllAndSortByDateAscAndNameDesc();
                break;
            default:
                return resultList;
        }
        return getCertificateResponseModels(certificateList, resultList);
    }

    private List<CertificateResponseModel> getCertificateResponseModels(List<Certificate> certificateList, List<CertificateResponseModel> resultList) {
        if (certificateList.size() != 0) {
            certificateList.forEach(certificate -> resultList.add(getCertificateById(certificate.getId())));
        }
        return resultList;
    }
}