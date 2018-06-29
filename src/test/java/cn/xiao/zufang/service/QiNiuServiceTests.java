package cn.xiao.zufang.service;

import java.io.File;

import cn.xiao.zufang.ApplicationTests;
import cn.xiao.zufang.service.house.IQiNiuService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

/**
 * Created by 瓦力.
 */
public class QiNiuServiceTests extends ApplicationTests {
    @Autowired
    private IQiNiuService qiNiuService;

// @Test
//    public void testUploadFile() {
//        String fileName = "/Users/bug/imooc/xunwu-project/tmp/xiaoqian.jpeg";
//        File file = new File(fileName);
//
//        Assert.assertTrue(file.exists());
//
//        try {
//            Response response = qiNiuService.uploadFile(file);
//            Assert.assertTrue(response.isOK());
//        } catch (QiniuException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testDelete() {
        String key = "Ftgy_8FIT5WaWfAGgAX5HDBlFEmz";
        try {
            Response response = qiNiuService.delete(key);
            Assert.assertTrue(response.isOK());
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }
}
