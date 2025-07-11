package edu.kh.project.websocket.handler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.project.chatting.model.dto.Message;
import edu.kh.project.chatting.model.service.ChattingService;
import edu.kh.project.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChattingWebsocketHandler extends TextWebSocketHandler {
	
	private final ChattingService service;
	
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		sessions.add(session);
		log.info("{} 연결됨",session.getId());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	
		sessions.remove(session);
		log.info("{} 연결끊김",session.getId());
	}
	
	// 클라이언트로부터 텍스트 메세지를 받았을 때 실행
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		// message - JS를 통해 클라이언트로부터 전달받은 내용
		/* { "senderNo" : "1",
		 * 	 "targetNo" : "2",
		 *   "chattinfRoomNo" : "8",
		 *   "messagesContent": "안녕"}
		 */
		
		// Jackson(JSON과 관련된 모듈)에서 제공하는 객체
		ObjectMapper objectMapper = new ObjectMapper();
		
		Message msg = objectMapper.readValue(message.getPayload(), Message.class);
		
		// Message를 DB에 삽입 서비스 호출
		int result = service.insertMessage(msg);
		
		if(result > 0) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
			msg.setSendTime(sdf.format(new Date()));
			
			 // 필드에 있는 sessions 에는 접속중인 모든 회원의 세션 정보가 담겨있음
			for(WebSocketSession s : sessions) {
				
				// 가로챈 session 꺼내기
				HttpSession temp = (HttpSession)s.getAttributes().get("session");
				
				// 로그인된 회원 정보 중 회원 번호 필요
				int loginMemberNo = ((Member)temp.getAttribute("loginMember")).getMemberNo();
				
				// 로그인 상태인 회원 중 targetNo 또는 senderNo 일치하는 회원에게 메세지 전달
				if(loginMemberNo == msg.getTargetNo() || loginMemberNo == msg.getSenderNo()) {
					
					// 다시 DTO(-> msg)를 JSON으로 변환, 클라이언트에게 메세지 전송
					String jsonData = objectMapper.writeValueAsString(msg);
					s.sendMessage(new TextMessage(jsonData));
				}
			}
		}
	}
}
