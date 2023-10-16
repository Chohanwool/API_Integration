package com.ApiIntegration.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FileUploadUtil {

    @Value("${file.path.upload.system}")
    private String filePathPreFix;

    private final int fileMaxSize = 1000 * 1024 * 1024; // 1000Mb
    final String[] validType = {"application/x-msdownload", "application/octet-stream", "application/x-sh"};

    public static final String[] allowedImageExtsDefault = new String[]{"gif", "jpeg", "jpg", "png", "svg", "blob"};

    public static final String[] allowedImageMimeTypesDefault = new String[]{"image/gif", "image/jpeg", "image/pjpeg", "image/x-png", "image/png", "image/svg+xml"};

    public Map<String, String> uploadFile(MultipartFile file, String fileName, String filePath, String allowedExts) throws Exception {

        if (file == null || file.isEmpty()) {
            throw new CommonException("업로드할 파일이 없습니다.");
        }
        if (StringUtils.isEmpty(filePath)) {
            throw new CommonException("파일 경로를 확인해 주세요.");
        }

        Map<String, String> returnMap = new HashMap<>();
        String baseFilePath = filePath + DateFormatUtils.format(new Date(), "yyyyMMdd") + "/";

        // 파일 사이즈 체크
        if (file.getSize() > fileMaxSize) {
            throw new CommonException("첨부파일은 1000mb 미만 등록할 수 있습니다.");
        }
        // 확장자 체크
        allowedExts = allowedExts.toLowerCase();
        String idxFileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        if(idxFileName.equals("blob")){
            byte[] audioData = file.getBytes();
            idxFileName = "aduio.wav";
        }

        if (allowedExts.indexOf(idxFileName.toLowerCase()) == -1 && !allowedExts.equals("all")) {
            throw new CommonException("사용할 수 없는 확장자입니다.");
        }

        // 파일 마임타입 확인
        if (!allowedExts.equals("all")) {
            for (int i = 0; i < validType.length; i++) {
                if (file.getContentType().equalsIgnoreCase(validType[i])) {
                    throw new CommonException("사용할 수 없는 확장자입니다.");
                }
            }
        }

        String sourceFileName = FilenameUtils.getName(file.getOriginalFilename());
        String sourceFileNameExt = FilenameUtils.getExtension(idxFileName).toLowerCase();
        String destinationFileName = StringUtils.defaultIfEmpty(fileName, DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + "_" + RandomStringUtils.randomAlphanumeric(6)) + "."
                + sourceFileNameExt;

        File destinationFile = new File(filePathPreFix + baseFilePath + destinationFileName);

        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);

        returnMap.put("fileSize", String.valueOf(file.getSize()));
        returnMap.put("originalFilename", sourceFileName.replaceAll("'", ""));
        returnMap.put("filePath", baseFilePath + destinationFileName);


        return returnMap;
    }
}
