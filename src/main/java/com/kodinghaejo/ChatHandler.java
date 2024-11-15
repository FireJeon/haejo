package com.kodinghaejo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.kodinghaejo.dto.ChatMsgDTO;
import com.kodinghaejo.dto.ChatDTO;
import com.kodinghaejo.service.ChatService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class ChatHandler extends TextWebSocketHandler{
	
	// 생성된 세션들을 저장
	private static List<WebSocketSession> list = new ArrayList<>();
	
	private final ObjectMapper objectMapper;
	private final ChatService chatService;

	//open 이벤트 발생 후 처리할 부분

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("--------------------- 1. 접속하고 세션 생성 : Session ID = {} ---------------------",session.getId());
		//생성된 세션을 리스트에 저장
		list.add(session);
	}

	//메세지 이벤트가 발생한 후 클라이언트로부터 전달 받은 메세지를 처리하는 부분  
	@Override 
	public void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception {
		
		String payLoad = message.getPayload();
		log.info("---------------------- 2. 클라이언트로부터 전달 받은 메세지 처리 : 메세지 = {} --------------------- ", payLoad);
		
		//문자열로 전달 받은 JSON 타입의 메세지를 Map 타입으로 변환
		ObjectMapper objectMapper = new ObjectMapper();
	    TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>(){};
	    Map<String, Object> payload = objectMapper.readValue(message.getPayload(), typeReference);	    
	    String email = (String)payload.get("email");
	    String idx = (String)payload.get("idx");
	    
		
	    for(WebSocketSession s: list) {
	    	s.sendMessage(message);
	    }
				
		ChatMsgDTO chatMsg = objectMapper.readValue(payLoad, ChatMsgDTO.class);
		ChatDTO room = chatService.findRoomById(Long.toString(chatMsg.getIdx()));
		room.handleActions(session, chatMsg, chatService);	
		
	}
	
	//접속해제 처리 --> 세션 삭제

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("접속해제 Session ID = {}",session.getId());
		list.remove(session);
	}

}
