package com.ApiIntegration.Controller;

import com.ApiIntegration.Service.FileUploadService;
import com.ApiIntegration.VO.FileUploadNewVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Value("${file.path.upload.system}")
    private String filePathPreFix;

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

    @ResponseBody
    @PostMapping("/voiceUpload.do")
    public ResponseEntity<String> handleAudioUpload(FileUploadNewVo file) {
        try {

            Map<String, String> fileMap = fileUploadService.uploadVoice(file);

            if(!fileMap.get("filePath").isEmpty()){
                int result = stt(fileMap);
            }

            return ResponseEntity.ok("음성 파일 업로드 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("음성 파일 업로드 실패");
        }
    }

    public int stt(Map<String, String> fileMap){
        String clientId = "9aooxtys4c";             // Application Client ID";
        String clientSecret = "TNeawv6b6oET4r1dBUr9HZQf7JbfAhFVxJH3WYfN";     // Application Client Secret";

        try {
            String imgFile = "음성 파일 경로";
            File voiceFile = new File(filePathPreFix + fileMap.get("filePath"));

            String language = "Eng";        // 언어 코드 ( Kor, Jpn, Eng, Chn )
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return 0;
    }
}
