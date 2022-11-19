package com.cx.springboot02.controller;


import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.RPage;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.PostMap;
import com.cx.springboot02.common.utils.Unobstructed;
import com.cx.springboot02.dto.AddSpuDto;
import com.cx.springboot02.dto.MenuDto;
import com.cx.springboot02.dto.UpdateSpuSingleDto;
import com.cx.springboot02.pojo.Category;
import com.cx.springboot02.pojo.Goods;
import com.cx.springboot02.service.impl.CategoryServiceImpl;
import com.cx.springboot02.service.impl.GoodsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.tools.Tool;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈翔
 * @since 2022-10-05
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private static final Logger log = LoggerFactory.getLogger(Logger.class);

    @Autowired
    GoodsServiceImpl goodsService;



    @GetMapping("/{cid}")
    @Unobstructed
    public JsonResult<List<Goods>> getGoodsById( @PathVariable String cid){
        List<Goods> goods = goodsService.getGoodsByCid(Long.valueOf(cid));
        return ResultTool.success(goods);
    }




    /**
     * 添加商品
     * @param addSpuDto
     * @return
     */
    @PostMapping("")
    @Unobstructed
    public JsonResult<Object> add(@RequestBody AddSpuDto addSpuDto){
        goodsService.addSpu(addSpuDto);
        return ResultTool.success(null);
    }


    /**
     * 删除spu 并且删除所有相关的sku
     * @param mp
     * @return
     */
    @DeleteMapping("")
    @Unobstructed
    public JsonResult<MenuDto> delete(@RequestBody Map<String,Object> mp){
        int id = (int) mp.get("gid");
        goodsService.deleteSpu((long) id);
        return ResultTool.success(null);
    }


    /**
     * 根据商家id获取到对应的数据 比如spu的分类 spu下的规格 spu下的sku
     * @param bid
     * @return
     */
    @GetMapping("/menu/{bid}")
    @Unobstructed
    public JsonResult<Object> get(@PathVariable String bid){
        return ResultTool.success(goodsService.get(Long.valueOf(bid)));
    }


    /**
     *
     * @param pagenum 第几页数据
     * @param size 每页多少条数据
     * @param key 名字like
     * @param categoryId 对应商家的食品分类id
     * @param id spu的id
     * @param shopId 商家id
     * @return
     */
    @GetMapping("/list/")
    @Unobstructed
    public JsonResult<Object> getSpuList(@PathParam("pagenum")Integer pagenum,@PathParam("size")Integer size
            ,@PathParam("key")String key,@PathParam("categoryId")Long categoryId,@PathParam("id") Long id,@PathParam("shopId")Long shopId){
        log.info("pagenum{} size{} key{}  categoryId{} id{} shopId{}",pagenum,size,key,categoryId,id,shopId);
        RPage rPage = null;
        try {
            rPage = goodsService.getSpuList(pagenum, size,key,categoryId,id,shopId);
            return ResultTool.success(rPage);
        } catch (Exception exception) {
            exception.printStackTrace();
            return  ResultTool.fail(ResultCode.PARAM_NOT_COMPLETE);
        }
    }


    /**
     * 更新单价商品
     * @param updateSpuSingleDto
     * @return
     */
    @PutMapping("/single")
    @Unobstructed
    public JsonResult<Object> getSpuList(@RequestBody UpdateSpuSingleDto updateSpuSingleDto){
        goodsService.updateSingleSpu(updateSpuSingleDto);
        return ResultTool.success(null);
    }

}

