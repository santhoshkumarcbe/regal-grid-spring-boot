package com.regalgrid.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.regalgrid.demo.model.Chat;

public interface ChatRepository extends MongoRepository<Chat, String>{

    List<Chat> findChatsBySenderIdAndReceiverId(String senderId, String receiverId);
    
}
