package com.ApiIntegration.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/CSR")
public class CsrController {

    @RequestMapping("/main")
    public String csrMain(){
        return "/csr/main";
    }

    @RequestMapping("/imgUpload")
    public String imgUpload(){
        return "/csr/imgUpload";
    }

    @RequestMapping("/voiceUpload")
    public String voiceUpload(){return "/csr/voiceUpload";}
}
