package com.epam.esm.service;

import com.epam.esm.dto.certificate.CertificateRequestModel;
import com.epam.esm.dto.certificate.CertificateResponseModel;
import com.epam.esm.dto.tag.TagRequestModel;
import com.epam.esm.dto.tag.TagResponseModel;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {
    public static final String CERTIFICATE_NOT_EXIST_WITH_ID = "Certificate doesn't exist with id: ";
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
        checkNewTags(certificate, newTagRequestModels);
        certificate.setId(null);
        certificateRepository.save(certificate);
    }

    public void update(long id, CertificateRequestModel certificateRequestModel) {
        Certificate certificate = certificateRepository.findById(id);
        Long price = certificateRequestModel.getPrice();
        Integer duration = certificateRequestModel.getDuration();

        checkCertificateOnNull(id, certificate);
        checkPriceAndDurationToUpdate(certificateRequestModel, certificate, price, duration);
        List<TagRequestModel> newTagRequestModels = certificateRequestModel.getTagRequestModels();

        if (newTagRequestModels != null && !newTagRequestModels.isEmpty()) {
            checkNewTags(certificate, newTagRequestModels);
        }
        String date = LocalDateTime.now().toString();
        certificate.setId(id);
        certificate.setName(certificateRequestModel.getName());
        certificate.setDescription(certificateRequestModel.getDescription());
        certificate.setLastUpdateDate(date);
        certificateRepository.update(certificate);
    }

    public void delete(long id) {
        Certificate certificate = certificateRepository.findById(id);
        checkCertificateOnNull(id, certificate);
        certificateRepository.deleteById(id);
    }

    public List<CertificateResponseModel> getAllByTagsNames(List<String> tagsNames, int pageNumber, int pageSize) {
        List<CertificateResponseModel> resultList = new ArrayList<>();

        if (tagsNames.isEmpty()) {
            return resultList;
        }
        List<Tag> tagList = tagRepository.findAll().stream()
                .filter(tag -> tagsNames.stream()
                        .anyMatch(name -> tag.getName().equals(name)))
                .collect(Collectors.toList());

        if (tagList.isEmpty()) {
            return resultList;
        }
        List<Certificate> certificateList = certificateRepository.findAllByTags(tagList, pageNumber, pageSize);
        return getCertificateResponseModels(certificateList, resultList);
    }

    public CertificateResponseModel getCertificateById(long id) {
        Certificate certificate = certificateRepository.findById(id);
        checkCertificateOnNull(id, certificate);

        CertificateResponseModel certificateResponseModel = modelMapper.map(certificate, CertificateResponseModel.class);
        List<TagResponseModel> tagResponseModels = certificate.getTags().stream()
                .map(tag -> modelMapper.map(tag, TagResponseModel.class))
                .collect(Collectors.toList());
        certificateResponseModel.setTagResponseModels(tagResponseModels);
        return certificateResponseModel;
    }

    public Long getTotalCount() {
        return certificateRepository.getTotalCount();
    }

    public List<CertificateResponseModel> getAll(int pageNumber, int pageSize) {
        List<Certificate> certificateList = certificateRepository.findAll(pageNumber, pageSize);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByTagName(String tagName, int pageNumber, int pageSize) {
        List<Certificate> certificateList = certificateRepository.findAllByTagName(tagName, pageNumber, pageSize);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByName(String name, int pageNumber, int pageSize) {
        List<Certificate> certificateList = certificateRepository.findAllByNameLike(name, pageNumber, pageSize);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllByDescription(String description, int pageNumber, int pageSize) {
        List<Certificate> certificateList = certificateRepository.findAllByDescriptionLike(description, pageNumber, pageSize);
        List<CertificateResponseModel> resultList = new ArrayList<>();
        return getCertificateResponseModels(certificateList, resultList);
    }

    public List<CertificateResponseModel> getAllAndSortByDateAndName(String dateOrder, String nameOrder, int pageNumber, int pageSize) {
        List<Certificate> certificateList;
        List<CertificateResponseModel> resultList = new ArrayList<>();
        String value = dateOrder + " " + nameOrder;

        switch (value.toLowerCase()) {
            case ("desc desc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateDescNameDesc(pageNumber, pageSize);
                break;
            case ("asc asc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateAscNameAsc(pageNumber, pageSize);
                break;
            case ("desc asc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateDescNameAsc(pageNumber, pageSize);
                break;
            case ("asc desc"):
                certificateList = certificateRepository.findAllByOrderByCreateDateAscNameDesc(pageNumber, pageSize);
                break;
            default:
                return resultList;
        }
        return getCertificateResponseModels(certificateList, resultList);
    }

    private void checkNewTags(Certificate certificate, List<TagRequestModel> newTagRequestModels) {
        newTagRequestModels.stream().filter(t1 -> tagService.getAll().stream()
                        .noneMatch(t2 -> t1.getName().equals(t2.getName())))
                .forEach(tagService::create);
        List<Tag> boundTag = tagRepository.findAll().stream()
                .filter(t -> newTagRequestModels.stream()
                        .anyMatch(t2 -> t.getName().equals(t2.getName())))
                .collect(Collectors.toList());
        certificate.setTags(boundTag);
    }

    private void checkPriceAndDurationToUpdate(CertificateRequestModel certificateRequestModel, Certificate certificate, Long price, Integer duration) {
        if (price == null && duration == null) {
            throw new ResourceNotFoundException("Wrong price or duration of certificate");
        }
        if (price != null && !price.equals(certificate.getPrice())) {
            certificate.setPrice(certificateRequestModel.getPrice());
        } else {
            certificate.setDuration(certificateRequestModel.getDuration());
        }
    }

    private void checkCertificateOnNull(long id, Certificate certificate) {
        if (certificate == null) {
            throw new ResourceNotFoundException(CERTIFICATE_NOT_EXIST_WITH_ID + id);
        }
    }

    private List<CertificateResponseModel> getCertificateResponseModels(List<Certificate> certificateList, List<CertificateResponseModel> resultList) {
        if (!certificateList.isEmpty()) {
            certificateList.forEach(certificate -> resultList.add(getCertificateById(certificate.getId())));
        }
        return resultList;
    }
}