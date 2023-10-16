package com.ApiIntegration.Controller;

import com.ApiIntegration.Service.FileUploadService;
import com.ApiIntegration.VO.FileUploadNewVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Value("${file.path.upload.system}")
    private String filePathPrefix;

    @ResponseBody
    @PostMapping("/uploadFile.do")
    public Map<String, String> uploadFile(FileUploadNewVo file) throws Exception {

        Map<String, String> returnMap = new HashMap<>();

        Map<String, String> fileMap = fileUploadService.uploadFile(file);

        returnMap.put("link", fileMap.get("filePath"));
        returnMap.put("res", "success");

        return returnMap;
    }
}
