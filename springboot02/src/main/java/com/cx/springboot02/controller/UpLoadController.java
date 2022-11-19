package com.cx.springboot02.controller;
 
import com.cx.springboot02.common.JsonResult;
import com.cx.springboot02.common.E.ResultCode;
import com.cx.springboot02.common.ResultTool;
import com.cx.springboot02.common.utils.FileTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @description: 上传文件控制器
 * @author: nxq email: niuxiangqian163@163.com
 * @createDate: 2020/12/19 4:09 下午
 * @updateUser: nxq email: niuxiangqian163@163.com
 * @updateDate: 2020/12/19 4:09 下午
 * @updateRemark:
 * @version: 1.0
 **/
@RestController
@RequestMapping("/upload")
public class UpLoadController {
    //获取主机端口
    @Value("${server.port}")
    private String POST;
    //静态资源对外暴露的访问路径
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    //实际存储路径
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    //图片
    @Value("${file.uploadImage}")
    private String uploadImage;


    @PostMapping("/image")
    public JsonResult<Object> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                System.out.println("file为空");
                return ResultTool.fail(ResultCode.COMMON_FAIL);
            }
            //获取文件名
            String oldName = file.getOriginalFilename();
            System.out.println("oldName:"+oldName);
            int lastindex = oldName.lastIndexOf(".");
            if(lastindex==-1){
                //没有后缀名
                System.out.println("无后缀名");
                return ResultTool.fail(ResultCode.COMMON_FAIL);
            }
            String suffix = oldName.substring(lastindex);
            //生成一个随机文件名
            UUID uuid = UUID.randomUUID();
            String newFileName = uuid.toString().replaceAll("-", "").toUpperCase() + suffix;
            System.out.println(newFileName);
            FileTool.uploadFiles(file.getBytes(),uploadFolder+uploadImage,newFileName);
            return ResultTool.success("http://127.0.0.1:8080/boot/file/image"+"/"+newFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultTool.fail(ResultCode.COMMON_FAIL);
        }
    }






}