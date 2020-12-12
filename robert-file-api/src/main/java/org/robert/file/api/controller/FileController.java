package org.robert.file.api.controller;

import org.robert.file.api.service.FileService;
import org.robert.file.api.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/file")
@Validated
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload")
    public R upload(@RequestParam("applicationName")  String applicationName, @RequestParam("moduleName")  String moduleName, @RequestParam("file")  MultipartFile file) {
        return R.ok(fileService.upload(applicationName, moduleName, file));
    }


}

