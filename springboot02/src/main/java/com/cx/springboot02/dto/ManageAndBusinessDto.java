package com.cx.springboot02.dto;

import lombok.Data;

@Data
public class ManageAndBusinessDto<T> {
    T data;
    private String type;
    private String token;
}
