package com.cx.springboot02.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShopClass {
    @TableId(value = "id", type = IdType.AUTO)

    private Long id;

    private String name;

    private String img;
}
