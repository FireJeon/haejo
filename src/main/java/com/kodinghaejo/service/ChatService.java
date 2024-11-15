package com.kodinghaejo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodinghaejo.dto.ChatDTO;
import com.kodinghaejo.dto.ChatMsgDTO;
import com.kodinghaejo.dto.ChatLogDTO;
import com.kodinghaejo.dto.ChatMemberDTO;
import com.kodinghaejo.entity.ChatEntity;
import com.kodinghaejo.entity.ChatLogEntity;
import com.kodinghaejo.entity.ChatMemberEntity;
import com.kodinghaejo.entity.ChatMsgEntity;
import com.kodinghaejo.entity.repository.ChatLogRepository;
import com.kodinghaejo.entity.repository.ChatMemberRepository;
import com.kodinghaejo.entity.repository.ChatMsgRepository;
import com.kodinghaejo.entity.repository.ChatRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService {

	private Map<String, ChatDTO> chatRooms;
	private final ObjectMapper objectMapper;
	private final ChatRepository chatRepository;
	private final ChatLogRepository chatLogRepository;
	private final ChatMsgRepository chatMsgRepository;
    private final ChatMemberRepository chatMemberRepository;
	
	// chatRooms 초기화
	@PostConstruct
	private void init() {
		chatRooms = new LinkedHashMap<>();
	}
	
	//생성된 전체 대화방 정보 가져 오기
	public List<ChatDTO> findAllRooms(){
//		return new ArrayList<>(chatRooms.values());
		List<ChatDTO> chatDTOs = new ArrayList<>();
		chatRepository.findAll().stream().forEach((e) -> chatDTOs.add(new ChatDTO(e)));
		return chatDTOs;
	}
	
	//대화방 아이디로 특정 대화방 정보 가져 오기 
	public ChatDTO findRoomById(String idx) {
		return chatRooms.get(idx);
	}

	//대화방 새로 생성
	public Long createRoom(ChatDTO chatdto) {
		ChatEntity entity = ChatEntity.builder()
									.type(chatdto.getType())
									.title(chatdto.getTitle())
									.regdate(LocalDateTime.now())
									.isUse("Y")
									.password(chatdto.getPassword())
									.build();
		chatRepository.save(entity);
	
		return entity.getIdx();
	}
	
//	 public ChatEntity createMemberList(ChatMemberDTO chatmemberdto, ChatEntity chatIdx) {
//		 ChatMemberEntity memberEntity = ChatMemberEntity.builder()
//				 					.chatIdx(chatIdx)
//				 					.email(chatmemberdto.getEmail())
//				 					.nickname(chatmemberdto.getNickname())
//				 					.manager(chatmemberdto.getManager())
//				 					.regdate(LocalDateTime.now())
//				 					.isUse("Y")
//				 					.build();
//		 chatMemberRepository.save(memberEntity);
//		 
//		 return memberEntity.getChatIdx();
//	 }
	 
	 public Long postMessage(ChatMsgDTO chatmsgdto, ChatEntity chatIdx) {
		 ChatMsgEntity msgEntity = ChatMsgEntity.builder()
				 					.chatIdx(chatIdx)
				 					.email(chatmsgdto.getEmail())
				 					.content(chatmsgdto.getContent())
				 					.regdate(LocalDateTime.now())
				 					.isUse("Y")
				 					.build();
		 chatMsgRepository.save(msgEntity);
		 
		 return msgEntity.getIdx();
	 }
 
	 public Long createLog(ChatLogDTO chatlogdto, ChatEntity chatIdx) {
		 ChatLogEntity logEntity = ChatLogEntity.builder()
				 					.chatIdx(chatIdx)
				 					.content(chatlogdto.getContent())
				 					.regdate(LocalDateTime.now())
				 					.isUse("Y")
				 					.build();
		 chatLogRepository.save(logEntity);
		 return logEntity.getIdx();
	 }
	
	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
			//session.sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}