package com.epam.esm.service;

import com.epam.esm.dto.TagResponseModel;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    //    @Autowired
    public final TagDAO tagDAO;

    @Transactional
    public TagResponseModel getTagById(int id) {

        Tag tag = tagDAO.show(id);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(tag, TagResponseModel.class);
    }

    // TODO
    // CertificateRequestDto
    // name
    // TagsRequestDto (List<String> names)
    //


}
