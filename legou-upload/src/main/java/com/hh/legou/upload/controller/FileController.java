package com.hh.legou.upload.controller;

import com.hh.legou.upload.config.FileDfsUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传c层
 *
 * @author hh
 * @version 1.0
 * @time 2023年9月2日14:55:18
 */
@RestController
@CrossOrigin
public class FileController {

    @Resource
    private FileDfsUtil fileDfsUtil;

    @ApiOperation(value = "上传文件", notes = "测试FastDFS文件上传")
    @RequestMapping(value = "/uploadFile", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String result;
        try {
            String path = fileDfsUtil.upload(file);
            if (StringUtils.isNotEmpty(path)) {
                result = path;
            } else {
                result = "上传失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "服务异常";
//            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "通过路径删除文件", notes = "测试通过路径删除文件")
    @RequestMapping(value = "/deleteByPath", method = RequestMethod.GET)
    public ResponseEntity<String> deleteByPath(String filePathName) {
        fileDfsUtil.deleteFile(filePathName);
        return ResponseEntity.ok("SUCCESS");
    }
}
