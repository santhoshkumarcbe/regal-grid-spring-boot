package com.regalgrid.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.regalgrid.demo.model.Chat;

public interface ChatRepository extends MongoRepository<Chat, String>{

    List<Chat> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimeAsc(String senderId, String receiverId,
            String receiverId2, String senderId2);

    List<Chat> findAllByChatId(String chatId);
    
}
