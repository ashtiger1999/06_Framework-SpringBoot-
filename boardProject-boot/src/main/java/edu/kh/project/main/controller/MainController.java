package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 요청/응답 제어 역할 명시 + Bean 등록
public class MainController {
	
	@RequestMapping("/")
	public String mainPage() {
		
		// forward 하겠다
		// 경로 : src/main/resources(classpath:)/templates/common/main.html
		return "common/main";
	}

}
