package com.ApiIntegration.Service;

import com.ApiIntegration.Util.CommonException;
import com.ApiIntegration.Util.FileUploadUtil;
import com.ApiIntegration.VO.FileUploadNewVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private FileUploadUtil fileUploadUtil;

    public Map<String, String> uploadFile(FileUploadNewVo fileVo) throws Exception {
        if(StringUtils.isEmpty(fileVo.getFilePath())){
            String menuCd = fileVo.getMenuCd();
            if(StringUtils.isEmpty(menuCd)){
                throw new CommonException("파일 업로드 경로를 지정해주세요.");
            }

            fileVo.setFilePath(menuCd);
        }

        String allowedExts = "all";
        String fileName = fileVo.getFile().getName();
        String filePath = "/"+ fileVo.getFilePath() + "/";

        return fileUploadUtil.uploadFile(fileVo.getFile(), fileName, filePath, allowedExts);
    }
}
