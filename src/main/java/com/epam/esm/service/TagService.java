package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagDAO tagDAO;
    private final ModelMapper modelMapper;

    public void create(TagRequestModel tagRequestModel) {
        Tag tag = modelMapper.map(tagRequestModel, Tag.class);
        tagDAO.create(tag);
    }

    public void delete(int id) {
        tagDAO.delete(id);
    }

    public TagResponseModel getTagById(int id) {
        Tag tag = tagDAO.getById(id);
        return modelMapper.map(tag, TagResponseModel.class);
    }

    public List<TagResponseModel> getAll() {
        List<Tag> tags = tagDAO.getAll();
        return mapList(tags, TagResponseModel.class);
    }

    public List<TagResponseModel> getTagsByCertificateId(int certificateId) {
        List<Tag> tags = tagDAO.getTagsByCertificateId(certificateId);
        return mapList(tags, TagResponseModel.class);
    }

    private  <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}