package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateRequestModel;
import com.epam.esm.dto.CertificateResponseModel;
import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ResourceNotFoundException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {

    @Mock
    private CertificateDAO certificateDAO;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TagService tagService;

    @Mock
    private CertificateHasTagService certificateHasTagService;

    @Spy
    @InjectMocks
    private CertificateService service;

    private final Certificate certificate = new Certificate();
    private final CertificateResponseModel certificateResponseModel = new CertificateResponseModel();
    private final List<Certificate> certificateList = Collections.singletonList(certificate);

    {
        certificate.setId(1);
        certificate.setName("c1");
        certificateResponseModel.setId(1);
    }

    @Test
    void create() {
        CertificateRequestModel certificateRequestModel = new CertificateRequestModel();
        certificateRequestModel.setId(1);
        certificateRequestModel.setName("c1");
        certificateRequestModel.setTagRequestModels(Collections.singletonList(new TagRequestModel(1, "tag1")));

        List<TagResponseModel> tagResponseModelList = new ArrayList<>();
        tagResponseModelList.add(new TagResponseModel(5, "tag5"));
        tagResponseModelList.add(new TagResponseModel(1, "tag1"));

        doNothing().when(certificateDAO).create(certificate);
        when(certificateDAO.getByName(anyString())).thenReturn(certificate);
        when(modelMapper.map(certificateRequestModel, Certificate.class)).thenReturn(certificate);
        when(tagService.getAll()).thenReturn(tagResponseModelList);
        doNothing().when(certificateHasTagService).create(anyInt(), anyInt());

        assertDoesNotThrow(() -> service.create(certificateRequestModel));

        verify(service, times(1)).create(certificateRequestModel);
        verify(certificateDAO, times(1)).create(certificate);
        verify(certificateDAO, times(1)).getByName(anyString());
        verify(modelMapper, times(1)).map(certificateRequestModel, Certificate.class);
        verify(tagService, times(2)).getAll();
        verify(certificateHasTagService, times(1)).create(anyInt(), anyInt());
    }

    @Test
    void deleteSuccess() {
        when(certificateDAO.getById(anyInt())).thenReturn(certificate);
        doNothing().when(certificateHasTagService).delete(anyInt());
        doNothing().when(certificateDAO).delete(anyInt());

        assertDoesNotThrow(() -> service.delete(anyInt()));

        verify(service, times(1)).delete(anyInt());
        verify(certificateDAO, times(1)).getById(anyInt());
        verify(certificateHasTagService, times(1)).delete(anyInt());
        verify(certificateDAO, times(1)).delete(anyInt());
    }

    @Test
    void deleteWithException() {
        when(certificateDAO.getById(anyInt())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(anyInt()));

        verify(service, times(1)).delete(anyInt());
        verify(certificateDAO, times(1)).getById(anyInt());
    }

    @Test
    void getCertificateByIdException() {
        when(certificateDAO.getById(anyInt())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> service.getCertificateById(anyInt()));

        verify(service, times(1)).getCertificateById(anyInt());
        verify(certificateDAO, times(1)).getById(anyInt());
    }

    @Test
    void getAll() {
        when(certificateDAO.getAll()).thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAll().size());

        verify(service, times(1)).getAll();
        verify(certificateDAO, times(1)).getAll();

    }

    @Test
    void getAllByTagName() {
        when(certificateDAO.getAllByTagName(anyString())).thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllByTagName(anyString()).size());

        verify(service, times(1)).getAllByTagName(anyString());
        verify(certificateDAO, times(1)).getAllByTagName(anyString());
    }

    @Test
    void getAllByName() {
        when(certificateDAO.getAllByName(anyString())).thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllByName(anyString()).size());

        verify(service, times(1)).getAllByName(anyString());
        verify(certificateDAO, times(1)).getAllByName(anyString());
    }

    @Test
    void getAllByDescription() {
        when(certificateDAO.getAllByDescription(anyString())).thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllByDescription(anyString()).size());

        verify(service, times(1)).getAllByDescription(anyString());
        verify(certificateDAO, times(1)).getAllByDescription(anyString());
    }

    @Test
    void getAllAndSortByDateAndName() {
        when(certificateDAO.getAllAndSortByDateDescAndNameDesc()).thenReturn(certificateList);
        when(certificateDAO.getAllAndSortByDateAscAndNameAsc()).thenReturn(certificateList);
        when(certificateDAO.getAllAndSortByDateDeskAndNameAsc()).thenReturn(certificateList);
        when(certificateDAO.getAllAndSortByDateAscAndNameDesc()).thenReturn(certificateList);
        getCertificateResponseModelsMock();

        assertEquals(1, service.getAllAndSortByDateAndName("desc", "desc").size());
        assertEquals(1, service.getAllAndSortByDateAndName("asc", "asc").size());
        assertEquals(1, service.getAllAndSortByDateAndName("desc", "asc").size());
        assertEquals(1, service.getAllAndSortByDateAndName("asc", "desc").size());
        assertEquals(0, service.getAllAndSortByDateAndName("", "").size());


        verify(service, times(5)).getAllAndSortByDateAndName(anyString(), anyString());
        verify(certificateDAO, times(1)).getAllAndSortByDateDescAndNameDesc();
        verify(certificateDAO, times(1)).getAllAndSortByDateAscAndNameAsc();
        verify(certificateDAO, times(1)).getAllAndSortByDateDeskAndNameAsc();
        verify(certificateDAO, times(1)).getAllAndSortByDateAscAndNameDesc();
    }

    private void getCertificateResponseModelsMock() {
        when(certificateDAO.getById(anyInt())).thenReturn(certificate);
        when(tagService.getTagsByCertificateId(anyInt())).thenReturn(Collections.emptyList());
        when(modelMapper.map(certificate, CertificateResponseModel.class)).thenReturn(certificateResponseModel);
    }
}