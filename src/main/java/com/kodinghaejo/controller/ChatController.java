package com.kodinghaejo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kodinghaejo.dto.ChatDTO;
import com.kodinghaejo.dto.ChatMsgDTO;
import com.kodinghaejo.entity.ChatEntity;
import com.kodinghaejo.service.ChatService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@AllArgsConstructor
@Log4j2
public class ChatController {

	/*
	 1. 웹 소켓은 HTML5를 지원하는 웹브라우저에서 작동 
	 2. 실시간 웹 애플리케이션을 위해 설계된 통신 프로토콜이며, TCP(Transmission Control Protocol)를 기반으로 함
	 3. HTTP와 다르게 클라이언트와 서버 간에 최초 연결이 이루어지면, 이 연결을 통해 양방향 통신(Full Duplex)을 지속적으로 유지할수 있음	  
	 */
	
	private final ChatService chatService;
	
	// 채팅방 목록
	@GetMapping("/chat/chatmain")
	public void getChat(Model model) {
		// 채팅 목록 로그
		log.info("--------------------- 채팅방 목록 ---------------------");	
		model.addAttribute("list", chatService.findAllRooms());
	}
	
	// 채팅방 생성 페이지
	@GetMapping("/chat/create")
	public void createpage() {}
		
	// 채팅방 생성
	@ResponseBody
	@PostMapping("/chat/create")
	public String createRoom(ChatDTO chat) {
		Long chatIdx = chatService.createRoom(chat);
		// 채팅방 생성 로그
		log.info("--------------------- 새로 생성된 idx : {} ---------------------",chatIdx);
		return "{\"message\":\"good\",\"idx\":\"" + chatIdx + "\"}";
	}
	
	// 채팅방
	@GetMapping("/chat/chatview")
	public void chatpage() {}
	
	// 이부분 수정중 <
//	@ResponseBody
//	@PostMapping("/chat/chatview")
//	public String postChat(ChatMsgDTO chatmsg) {
//		Long chatIdx = chatService.postMessage(chatmsg, chatIdx);
//		// 채팅내역 로그
//		log.info("--------------------- 작성된 채팅 idx : {} ---------------------",chatMsgIdx);
//		return "{\"message\":\"good\",\"idx\":\"\"" + chatMsgIdx + "\"}";
//	}
	//  >

//	@GetMapping("/chat/chat")
//	public void chat0(Model model) {
//		// 채팅 시작 로그
//		log.info("--------------------- 채팅 시작 ---------------------");	
//		model.addAttribute("list", chatService.findAllRooms());
//	}
//	@ResponseBody
//	@PostMapping("/chat/chat")
//	public String chat1(@RequestParam("title") String title) {
//		ChatDTO chatDTO = chatService.createRoom(title);
//		log.info("--------------------- 새로 생성된 idx : {} ---------------------",chatDTO.getIdx());
//		return "{\"message\":\"good\",\"idx\":\"" + chatDTO.getIdx() + "\"}";
//	}
}
