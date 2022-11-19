package com.cx.springboot02.dto;

import lombok.Data;


@Data
public class BookQueryDto {
    String name;
    Integer[] price;
    int size;
    int page;

    public int offset(){
        return (page-1)*size;
    }
    public int limit(){
        return size;
    }
}


