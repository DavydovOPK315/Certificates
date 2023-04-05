package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestModel {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private List<TagRequestModel> tagRequestModels;
}
