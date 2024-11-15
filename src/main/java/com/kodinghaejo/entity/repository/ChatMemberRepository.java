package com.kodinghaejo.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodinghaejo.entity.ChatMemberEntity;
import com.kodinghaejo.entity.ChatMemberEntityID;

public interface ChatMemberRepository extends JpaRepository<ChatMemberEntity, ChatMemberEntityID> {

}