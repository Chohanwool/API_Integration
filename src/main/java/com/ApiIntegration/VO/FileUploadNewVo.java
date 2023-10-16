package com.ApiIntegration.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadNewVo {
    private String menuCd;
    private String filePath;
    private String name;
    private String fileGroupUuid;
    private String userId;
    private String type; // 파일 타입에 따라 sub 폴더 생성
    private MultipartFile file; // 첨부파일 업로드용
}
