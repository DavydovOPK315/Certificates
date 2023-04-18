package com.epam.esm.service;

import com.epam.esm.dto.tag.TagRequestModel;
import com.epam.esm.dto.tag.TagResponseModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ModelMapper modelMapper;

    @Spy
    @InjectMocks
    private TagService service;

    private final Tag tag = new Tag();
    private final TagResponseModel tagResponseMode = new TagResponseModel();
    private final List<Tag> tagList = Collections.singletonList(tag);

    @Test
    void create2() {
        TagRequestModel tagRequestModel = new TagRequestModel();
        when(modelMapper.map(tagRequestModel, Tag.class))
                .thenReturn(tag);
        doNothing().when(tagRepository).save(any(Tag.class));

        assertDoesNotThrow(() -> service.create(tagRequestModel));

        verify(service, times(1)).create(any(TagRequestModel.class));
        verify(modelMapper, times(1)).map(tagRequestModel, Tag.class);
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void delete() {
        doNothing().when(tagRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> service.delete(anyLong()));

        verify(service, times(1)).delete(anyLong());
        verify(tagRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getTagById() {
        when(tagRepository.findById(anyLong()))
                .thenReturn(tag);
        when(modelMapper.map(tag, TagResponseModel.class))
                .thenReturn(tagResponseMode);

        assertNotNull(service.getTagById(anyLong()));

        verify(service, times(1)).getTagById(anyLong());
        verify(tagRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(tag, TagResponseModel.class);
    }

    @Test
    void getAll() {
        when(tagRepository.findAll())
                .thenReturn(tagList);

        assertEquals(1, service.getAll().size());

        verify(service, times(1)).getAll();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void getAllWithPagination() {
        when(tagRepository.findAll(anyInt(), anyInt()))
                .thenReturn(tagList);

        assertEquals(1, service.getAll(anyInt(), anyInt()).size());

        verify(service, times(1)).getAll(anyInt(), anyInt());
        verify(tagRepository, times(1)).findAll(anyInt(), anyInt());
    }

    @Test
    void getMostWidelyUsedTagOfUserWithHighestCostOfAllOrders() {
        when(tagRepository.findMostWidelyUsedTagOfUserWithHighestCostOfAllOrders())
                .thenReturn(tag);
        when(modelMapper.map(tag, TagResponseModel.class))
                .thenReturn(tagResponseMode);

        assertNotNull(service.getMostWidelyUsedTagOfUserWithHighestCostOfAllOrders());

        verify(service, times(1)).getMostWidelyUsedTagOfUserWithHighestCostOfAllOrders();
        verify(tagRepository, times(1)).findMostWidelyUsedTagOfUserWithHighestCostOfAllOrders();
        verify(modelMapper, times(1)).map(tag, TagResponseModel.class);
    }
}