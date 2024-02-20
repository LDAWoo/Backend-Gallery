package com.example.gardenedennft.utils;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;


@Data
@SuperBuilder(toBuilder = true)
public class BaseResponse<T> implements Serializable {
    private Integer page;
    private Integer limit;
    private Integer totalPage;
    private Long totalItem;
    private List<T> listResult;

}

