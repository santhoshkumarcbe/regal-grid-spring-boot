package com.regalgrid.demo.service;

import java.util.List;

import com.regalgrid.demo.model.Chat;

public interface ChatService {

    String postChat(Chat chat);

    List<Chat> findAllChatBySenderIdAndReceiverId(String senderId, String receiverId);

    String deleteChatById(String chatId);

    List<Chat> findAllChatByChatId(String chatId);
    
}
