package com.epam.esm.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestModel {
    private Long id;
    private String name;
}
