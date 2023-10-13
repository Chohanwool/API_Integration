package com.ApiIntegration.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileUploadController {

    @Value("${file.path.upload.system}")
    private String filePathPrefix;

    @ResponseBody
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("uploadFile")MultipartFile file){
        return new ResponseEntity<>("res", HttpStatus.OK);
    }
}
