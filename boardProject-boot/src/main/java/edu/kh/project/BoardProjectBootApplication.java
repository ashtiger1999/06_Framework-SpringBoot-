package edu.kh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
					  
@EnableScheduling // 해당 프로젝트 서버 실행시 스케줄러를 실행함
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) // Spring Security의 자동 설정 중 로그인 페이지 이용 안함
public class BoardProjectBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardProjectBootApplication.class, args);
	}

}
