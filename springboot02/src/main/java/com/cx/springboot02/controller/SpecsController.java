package com.cx.springboot02.controller;


import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.data.vo.SpecsCategoryVo;
import com.cx.springboot02.dto.AddSpecsCategoryDto;
import com.cx.springboot02.dto.AddSpecsDto;
import com.cx.springboot02.dto.IdDto;
import com.cx.springboot02.mapper.SpecsMapper;
import com.cx.springboot02.mapper.Specs_categoryMapper;
import com.cx.springboot02.pojo.Specs;
import com.cx.springboot02.pojo.SpecsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specs")
public class SpecsController {


    @Autowired
    SpecsMapper specsMapper;

    @Autowired
    Specs_categoryMapper specsCategoryMapper;

    /**
     * 添加规格分类
     *
     * @return
     */
    @PostMapping("/addCategories")
    @Unobstructed
    public JsonResult getCategories(@RequestBody AddSpecsCategoryDto addSpecsCategoryDto) {
        SpecsCategory specsCategory = new SpecsCategory();
        specsCategory.setName(addSpecsCategoryDto.getName());
        specsCategory.setGoodsId(addSpecsCategoryDto.getGoodId());
        specsCategoryMapper.insert(specsCategory);
        return ResultTool.success(null);
    }

    /**
     * 添加规格
     *
     * @return
     */
    @PostMapping("/addSpecs")
    @Unobstructed
    public JsonResult getCategories(@RequestBody AddSpecsDto addSpecsDto) {
        Specs specs = new Specs();
        specs.setName(addSpecsDto.getName());
        specs.setDescription(addSpecsDto.getDescription());
        specs.setSpecsCategoryId(addSpecsDto.getSpecsCategoryId());
        specsMapper.insert(specs);
        return ResultTool.success(null);
    }


    /**
     * 获取一个商品[spu]的所有规格分类
     *
     * @return
     */
    @GetMapping("/list/{id}")
    @Unobstructed
    public JsonResult getAllCategories(@PathVariable String id) {
        Long gid = Long.valueOf(id);
        Map<String,Object> mp = new HashMap<>();
        mp.put("goods_id",gid);
        List<SpecsCategory> specsCategories = specsCategoryMapper.selectByMap(mp);
        return ResultTool.success(specsCategories);
    }

    /**
     * 获取一个商品[spu]的所有规格分类和其下的所有规格 方便前端形成树状结构的选择栏
     *
     * @return
     */
    @GetMapping("/categoryAndSpecsList/{id}")
    @Unobstructed
    public JsonResult categoryAndSpecsList(@PathVariable String id) {
        Long gid = Long.valueOf(id);
        List<SpecsCategoryVo> specsCategoryVos = specsCategoryMapper.selectSpecsListVo(gid);
        for (SpecsCategoryVo specsCategory : specsCategoryVos) {
            Map<String,Object> mp =new HashMap<>();
            mp.put("specs_category_id",specsCategory.getId());
            List<Specs> specsList = specsMapper.selectByMap(mp);
            specsCategory.setSpecsList(specsList);
        }
        return ResultTool.success(specsCategoryVos);
    }
}

