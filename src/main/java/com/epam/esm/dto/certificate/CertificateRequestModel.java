package com.epam.esm.dto.certificate;

import com.epam.esm.dto.tag.TagRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestModel {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Integer duration;
    private List<TagRequestModel> tagRequestModels;
}
