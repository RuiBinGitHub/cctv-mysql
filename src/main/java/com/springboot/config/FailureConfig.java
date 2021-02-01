package com.springboot.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FailureConfig implements ErrorController {

	@RequestMapping(value="/error")
    public String actionFailure() {
        return getErrorPath();
    }
	
	/** 设置错误跳转页面 */
	public String getErrorPath() {
		return "common/failure";
	}
}

