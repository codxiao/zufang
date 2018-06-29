
package cn.xiao.zufang.web.controller.admin;

import cn.xiao.zufang.base.ApiResponse;
import cn.xiao.zufang.service.house.IQiNiuService;
import cn.xiao.zufang.web.dto.QiNiuResultSet;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AdminController {
    @Autowired
    private Gson gson;
    @Autowired
    private IQiNiuService qiNiuService;
    @GetMapping("/admin/center")
    public String center(){
        return "admin/center";
    }
    @GetMapping("admin/welcome")
    public String wekcomePage(){
        return "admin/welcome";
    }
    @GetMapping("admin/login")
    public String loginPage(){
        return "admin/login";
    }
    @GetMapping("/admin/add/house")
    public String addHouse(){
        return "admin/house-add";
    }
    @PostMapping(value = "/admin/upload/photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return ApiResponse.ofStatus(ApiResponse.STATUS.NOT_VALID_PARAM);
        }
        String fileName=file.getOriginalFilename();
        try {
            InputStream inputStream=file.getInputStream();
            Response response = qiNiuService.uploadFile(inputStream);
            if (response.isOK()){
                QiNiuResultSet resultSet= gson.fromJson(response.bodyString(),QiNiuResultSet.class);
                return ApiResponse.ofSuccess(resultSet);
            }else {
                return ApiResponse.ofMessage(response.statusCode, response.getInfo());
            }
            }catch(QiniuException e){

            Response response=e.response;
            try {
                return ApiResponse.ofMessage(response.statusCode,response.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
                return ApiResponse.ofStatus(ApiResponse.STATUS.INTERNAL_ERROR);
            }

        } catch (IOException e) {
            return ApiResponse.ofStatus(ApiResponse.STATUS.INTERNAL_ERROR);
        }
//        File target=new File("D:\\work\\zufang\\temp"+fileName);
//        try {
//            file.transferTo(target);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ApiResponse.ofStatus(ApiResponse.STATUS.INTERNAL_ERROR);
//        }
//        return ApiResponse.ofSuccess(null);

    }

}
