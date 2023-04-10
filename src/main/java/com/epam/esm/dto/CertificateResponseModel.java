package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateResponseModel extends RepresentationModel<CertificateResponseModel> {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagResponseModel> tagResponseModels;
}
