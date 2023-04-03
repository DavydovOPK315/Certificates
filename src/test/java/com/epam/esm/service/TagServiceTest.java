package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagDAO tagDAO;

    @Mock
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private TagService service;

    private final Tag tag = new Tag();
    private final TagResponseModel tagResponseMode = new TagResponseModel();
    private final List<Tag> tagList = Collections.singletonList(tag);

    @Test
    void create() {
        TagRequestModel tagRequestModel = new TagRequestModel();
        when(modelMapper.map(tagRequestModel, Tag.class)).thenReturn(tag);
        doNothing().when(tagDAO).create(tag);

        assertDoesNotThrow(() -> service.create(tagRequestModel));

        verify(service, times(1)).create(tagRequestModel);
        verify(modelMapper, times(1)).map(tagRequestModel, Tag.class);
        verify(tagDAO, times(1)).create(tag);
    }

    @Test
    void delete() {
        doNothing().when(tagDAO).delete(anyInt());

        assertDoesNotThrow(() -> service.delete(anyInt()));

        verify(service, times(1)).delete(anyInt());
        verify(tagDAO, times(1)).delete(anyInt());
    }

    @Test
    void getTagById() {
        when(tagDAO.getById(anyInt())).thenReturn(tag);
        when(modelMapper.map(tag, TagResponseModel.class)).thenReturn(tagResponseMode);

        assertNotNull(service.getTagById(anyInt()));

        verify(service, times(1)).getTagById(anyInt());
        verify(tagDAO, times(1)).getById(anyInt());
        verify(modelMapper, times(1)).map(tag, TagResponseModel.class);

    }

    @Test
    void getTags() {
        when(tagDAO.getAll()).thenReturn(tagList);

        assertEquals(1, service.getAll().size());

        verify(service, times(1)).getAll();
        verify(tagDAO, times(1)).getAll();
    }

    @Test
    void getTagsByCertificateId() {
        when(tagDAO.getTagsByCertificateId(anyInt())).thenReturn(tagList);

        assertEquals(1, service.getTagsByCertificateId(anyInt()).size());

        verify(service, times(1)).getTagsByCertificateId(anyInt());
        verify(tagDAO, times(1)).getTagsByCertificateId(anyInt());
    }
}