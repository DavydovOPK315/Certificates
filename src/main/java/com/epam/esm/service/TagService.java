package com.epam.esm.service;

import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    public void create(TagRequestModel tagRequestModel) {
        Tag tag = modelMapper.map(tagRequestModel, Tag.class);
        tag.setId(null);
        tagRepository.save(tag);
    }

    public void delete(long id) {
        tagRepository.deleteById(id);
    }

    public TagResponseModel getTagById(long id) {
        Tag tag = tagRepository.findById(id);
        return modelMapper.map(tag, TagResponseModel.class);
    }

    public List<TagResponseModel> getAll() {
        List<Tag> tags = tagRepository.findAll();
        return mapList(tags, TagResponseModel.class);
    }

    public TagResponseModel getMostWidelyUsedTagOfUserWithHighestCostOfAllOrders() {
        Tag tag = tagRepository.findMostWidelyUsedTagOfUserWithHighestCostOfAllOrders();
        return modelMapper.map(tag, TagResponseModel.class);
    }

    private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}