package com.epam.esm.service;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagRequestModel;
import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.util.MapListUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagDAO tagDAO;
    private final ModelMapper modelMapper;
    private final MapListUtil mapListUtil;

    public TagResponseModel getTagById(int id) {
        Tag tag = tagDAO.getById(id);
        return modelMapper.map(tag, TagResponseModel.class);
    }

    public List<TagResponseModel> getTags() {
        List<Tag> tags = tagDAO.index();
        return mapListUtil.mapList(tags, TagResponseModel.class);
    }

    public void create(TagRequestModel tagRequestModel) {
        Tag tag = modelMapper.map(tagRequestModel, Tag.class);
        tagDAO.create(tag);
    }

    public void remove(int id) {
        tagDAO.delete(id);
    }

    public List<TagResponseModel> getTagsByCertificateId(int certificateId) {
        List<Tag> tags = tagDAO.getTagsByCertificateId(certificateId);
        return mapListUtil.mapList(tags, TagResponseModel.class);
    }
}