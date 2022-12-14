package com.school.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SchoolPage {
    private int pageNumber = 0;
    private int pageSize = 1;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";
}
