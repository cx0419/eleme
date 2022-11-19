package com.cx.springboot02.dto;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cx.springboot02.pojo.Food;
import com.cx.springboot02.pojo.Goods;
import lombok.Data;

import javax.xml.soap.SAAJResult;
import java.util.List;

@Data
public class CartDataDto {
    private List<Object> attrs;

    private Object extra;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 商店id
     */
    private Long shopId;

    /**
     * spuid
     */
    private Long spuId;
    private Goods spu;


    /**
     * 对应的skuid
     */
    private Long skuId;
    private Food sku;

    /**
     * 商品名字
     */
    private String name;

    /**
     * 价格
     */
    private Float price;

    /**
     * 规格列表["name1","name2"]的形式
     */
    private String specsList;

    /**
     * #1#1#...的形式
     */
    private String specsIdList = null;


    /**
     * 打包费
     */
    private Integer packingFee;



    public static void main(String[] args) {
        List<CartDataDto> cartDataDtos = JSONObject.parseArray("[{\"categoryId\":1,\"spuId\":\"2\",\"skuId\":\"1\",\"num\":1,\"price\":10.1,\"name\":\"只因\",\"specsList\":[\"微辣\",\"唱\"],\"specsIdList\":\"#1#4#\"},{\"categoryId\":1,\"spuId\":\"3\",\"skuId\":\"3\",\"num\":3,\"price\":20.2,\"name\":\"只因1\",\"specsList\":\"无规格商品\",\"specsIdList\":null}]",  CartDataDto.class);

        System.out.println(cartDataDtos.toString());
        for (CartDataDto cartDataDto : cartDataDtos) {
            if(cartDataDto.getSpecsIdList()==null){
                System.out.println("solo");
                continue;
            }
            String[] split = cartDataDto.getSpecsIdList().split("#");
            System.out.println(cartDataDto.getSpecsList()+":");
            for (String s : split) {
                System.out.print(s);
            }
            System.out.println();

        }
    }


}
