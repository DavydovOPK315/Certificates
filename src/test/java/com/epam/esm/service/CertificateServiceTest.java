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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Spy
    @InjectMocks
    private CertificateService service;

    private final Certificate certificate = new Certificate();
    private final CertificateResponseModel certificateResponseModel = new CertificateResponseModel();
    private final List<Certificate> certificateList = Collections.singletonList(certificate);

    {
        certificate.setId(1L);
        certificate.setName("c1");
        certificateResponseModel.setId(1L);
    }

    @Test
    void createWithNewTags() {
        CertificateRequestModel certificateRequestModel = new CertificateRequestModel();
        certificateRequestModel.setId(1L);
        certificateRequestModel.setName("c1");
        certificateRequestModel.setTagRequestModels(Collections.singletonList(new TagRequestModel(1L, "tag1")));

        List<TagResponseModel> tagResponseModelList = new ArrayList<>();
        tagResponseModelList.add(new TagResponseModel(5L, "tag5"));
        tagResponseModelList.add(new TagResponseModel(1L, "tag1"));

        doNothing().when(certificateRepository).save(certificate);
        when(modelMapper.map(certificateRequestModel, Certificate.class))
                .thenReturn(certificate);
        when(tagService.getAll()).thenReturn(tagResponseModelList);
        when(tagRepository.findAll())
                .thenReturn(Collections.singletonList(new Tag(1L, "tag1", null)));

        assertDoesNotThrow(() -> service.create(certificateRequestModel));

        verify(service, times(1)).create(certificateRequestModel);
        verify(certificateRepository, times(1)).save(certificate);
        verify(modelMapper, times(1)).map(certificateRequestModel, Certificate.class);
        verify(tagService, times(1)).getAll();
    }

    @Test
    void createWithoutNewTags() {
        CertificateRequestModel certificateRequestModel = new CertificateRequestModel();

        doNothing().when(certificateRepository).save(certificate);
        when(modelMapper.map(certificateRequestModel, Certificate.class))
                .thenReturn(certificate);

        certificateRequestModel.setTagRequestModels(null);
        assertDoesNotThrow(() -> service.create(certificateRequestModel));
        verify(certificateRepository, times(1)).save(certificate);
        verify(modelMapper, times(1)).map(certificateRequestModel, Certificate.class);
    }

    @Test
    void deleteSuccess() {
        when(certificateRepository.findById(anyLong()))
                .thenReturn(certificate);
        doNothing().when(certificateRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> service.delete(anyLong()));

        verify(service, times(1)).delete(anyLong());
        verify(certificateRepository, times(1)).findById(anyLong());
        verify(certificateRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteWithException() {
        when(certificateRepository.findById(anyLong()))
                .thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(anyLong()));

        verify(service, times(1)).delete(anyLong());
        verify(certificateRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllByTagsNames() {
        when(tagRepository.findAll())
                .thenReturn(Collections.singletonList(new Tag(1L, "tag1", null)));
        when(certificateRepository.findAllByTags(anyList(), anyInt(), anyInt()))
                .thenReturn(certificateList);
        getCertificateResponseModelsMock();
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tags2");

        assertEquals(1, service.getAllByTagsNames(tags, 1, 20).size());

        verify(service, times(1)).getAllByTagsNames(anyList(), anyInt(), anyInt());
        verify(tagRepository, times(1)).findAll();
        verify(certificateRepository, times(1)).findAllByTags(anyList(), anyInt(), anyInt());

        assertEquals(0, service.getAllByTagsNames(new ArrayList<>(), 1, 20).size());
    }


    @Test
    void getCertificateById() {
        when(certificateRepository.findById(anyLong()))
                .thenReturn(certificate);
        when(modelMapper.map(certificate, CertificateResponseModel.class))
                .thenReturn(certificateResponseModel);
        certificate.setTags(Collections.singletonList(new Tag(1L, "Tag1", new ArrayList<>())));

        assertNotNull(service.getCertificateById(1L));

        certificate.setTags(new ArrayList<>());
        verify(service, times(1)).getCertificateById(anyLong());
        verify(certificateRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAll() {
        when(certificateRepository.findAll(anyInt(), anyInt()))
                .thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAll(anyInt(), anyInt()).size());

        verify(service, times(1)).getAll(anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAll(anyInt(), anyInt());
    }

    @Test
    void getAllByTagName() {
        when(certificateRepository.findAllByTagName(anyString(), anyInt(), anyInt()))
                .thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllByTagName(anyString(), anyInt(), anyInt()).size());

        verify(service, times(1)).getAllByTagName(anyString(), anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByTagName(anyString(), anyInt(), anyInt());
    }

    @Test
    void getAllByName() {
        when(certificateRepository.findAllByNameLike(anyString(), anyInt(), anyInt()))
                .thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllByName(anyString(), anyInt(), anyInt()).size());

        verify(service, times(1)).getAllByName(anyString(), anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByNameLike(anyString(), anyInt(), anyInt());
    }

    @Test
    void getAllByDescription() {
        when(certificateRepository.findAllByDescriptionLike(anyString(), anyInt(), anyInt()))
                .thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllByDescription(anyString(), anyInt(), anyInt()).size());

        verify(service, times(1)).getAllByDescription(anyString(), anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByDescriptionLike(anyString(), anyInt(), anyInt());
    }

    @Test
    void getAllAndSortByDateAndName() {
        when(certificateRepository.findAllByOrderByCreateDateDescNameDesc(anyInt(), anyInt()))
                .thenReturn(certificateList);
        when(certificateRepository.findAllByOrderByCreateDateAscNameAsc(anyInt(), anyInt()))
                .thenReturn(certificateList);
        when(certificateRepository.findAllByOrderByCreateDateDescNameAsc(anyInt(), anyInt()))
                .thenReturn(certificateList);
        when(certificateRepository.findAllByOrderByCreateDateAscNameDesc(anyInt(), anyInt()))
                .thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllAndSortByDateAndName("desc", "desc", 1, 20).size());
        assertEquals(1, service.getAllAndSortByDateAndName("asc", "asc", 1, 20).size());
        assertEquals(1, service.getAllAndSortByDateAndName("desc", "asc", 1, 20).size());
        assertEquals(1, service.getAllAndSortByDateAndName("asc", "desc", 1, 20).size());
        assertEquals(0, service.getAllAndSortByDateAndName("", "", 1, 20).size());

        verify(service, times(5)).getAllAndSortByDateAndName(anyString(), anyString(), anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByOrderByCreateDateDescNameDesc(anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByOrderByCreateDateAscNameAsc(anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByOrderByCreateDateDescNameAsc(anyInt(), anyInt());
        verify(certificateRepository, times(1)).findAllByOrderByCreateDateAscNameDesc(anyInt(), anyInt());
    }

    private void getCertificateResponseModelsMock() {
        when(certificateRepository.findById(anyLong()))
                .thenReturn(certificate);
        when(modelMapper.map(certificate, CertificateResponseModel.class))
                .thenReturn(certificateResponseModel);
        when(certificateRepository.findById(anyLong()))
                .thenReturn(certificate);
        when(modelMapper.map(certificate, CertificateResponseModel.class))
                .thenReturn(certificateResponseModel);
        certificate.setTags(Collections.singletonList(new Tag(1L, "tag1", new ArrayList<>())));
    }
}