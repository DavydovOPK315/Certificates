package com.epam.esm.service;

import com.epam.esm.dto.CertificateRequestModel;
import com.epam.esm.dto.CertificateResponseModel;
import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final ModelMapper modelMapper;
    private final TagService tagService;
    private final TagRepository tagRepository;

    public void create(CertificateRequestModel certificateRequestModel) {
        Certificate certificate = modelMapper.map(certificateRequestModel, Certificate.class);
        String date = LocalDateTime.now().toString();
        certificate.setCreateDate(date);
        certificate.setLastUpdateDate(date);
        List<TagRequestModel> newTagRequestModels = certificateRequestModel.getTagRequestModels();

        if (newTagRequestModels == null || newTagRequestModels.isEmpty()) {
            certificateRepository.save(certificate);
            return;
        }
        newTagRequestModels.stream()
                .filter(t1 -> tagService.getAll().stream()
                        .noneMatch(t2 -> t1.getName().equals(t2.getName())))
                .forEach(tagService::create);
        Set<Tag> boundTag = tagRepository.findAll().stream()
                .filter(t -> newTagRequestModels.stream()
                        .anyMatch(t2 -> t.getName().equals(t2.getName())))
                .collect(Collectors.toSet());
        certificate.setTags(boundTag);
        certificate.setId(null);
        certificateRepository.save(certificate);
    }

    public void delete(long id) {
        Certificate certificate = certificateRepository.findById(id);

        if (certificate == null) {
            throw new ResourceNotFoundException("Certificate not exist with id: " + id);
        }
        certificateRepository.deleteById(id);
    }

    public void update(long id, CertificateRequestModel certificateRequestModel) {
        Certificate certificate = certificateRepository.findById(id);

        if (certificate == null) {
            throw new ResourceNotFoundException("Certificate not exist with id: " + id);
        }
        Long price = certificateRequestModel.getPrice();
        Integer duration = certificateRequestModel.getDuration();

        if (price == null && duration == null) {
            throw new IllegalArgumentException("Wrong price or duration of certificate");
        }
        if (price != null && !price.equals(certificate.getPrice())) {
            certificate.setPrice(certificateRequestModel.getPrice());
        } else {
            certificate.setDuration(certificateRequestModel.getDuration());
        }
        List<TagRequestModel> newTags = certificateRequestModel.getTagRequestModels();

        if (newTags != null && newTags.size() > 0) {
            List<TagResponseModel> actualTagResponseModels = tagService.getAll();
            newTags.stream().filter(t1 -> actualTagResponseModels.stream()
                            .noneMatch(t2 -> t1.getName().equals(t2.getName())))
                    .forEach(tagService::create);
            Set<Tag> boundTag = tagRepository.findAll().stream()
                    .filter(t -> newTags.stream()
                            .anyMatch(t2 -> t.getName().equals(t2.getName())))
                    .collect(Collectors.toSet());
            certificate.setTags(boundTag);
        }
        String date = LocalDateTime.now().toString();
        certificate.setId(id);
        certificate.setLastUpdateDate(date);
        certificateRepository.update(certificate);
    }

    public CertificateResponseModel getCertificateById(long id) {
        Certificate certificate = certificateRepository.findById(id);

        if (certificate == null) {
            throw new ResourceNotFoundException("Certificate not exist with id: " + id);
        }
        CertificateResponseModel certificateResponseModel = modelMapper.map(certificate, CertificateResponseModel.class);
        List<TagResponseModel> tagResponseModels = certificate.getTags().stream()
                .map(tag -> modelMapper.map(tag, TagResponseModel.class))
                .collect(Collectors.toList());
        certificateResponseModel.setTagResponseModels(tagResponseModels);
        return certificateResponseModel;
    }

    public List<CertificateResponseModel> getAll() {
        List<Certificate> certificateList = certificateRepository.findAll();
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByTagName(String tagName) {
        List<Certificate> certificateList = certificateRepository.findAllByTagName(tagName);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByName(String name) {
        List<Certificate> certificateList = certificateRepository.findAllByNameLike(name);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByDescription(String description) {
        List<Certificate> certificateList = certificateRepository.findAllByDescriptionLike(description);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllAndSortByDateAndName(String dateOrder, String nameOrder) {
        List<Certificate> certificateList;
        List<CertificateResponseModel> resultList = new ArrayList<>();
        String value = dateOrder + " " + nameOrder;

        switch (value.toLowerCase()) {
            case ("desc desc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateDescNameDesc();
                break;
            case ("asc asc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateAscNameAsc();
                break;
            case ("desc asc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateDescNameAsc();
                break;
            case ("asc desc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateAscNameDesc();
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