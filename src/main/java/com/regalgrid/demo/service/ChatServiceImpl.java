package com.regalgrid.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.model.Chat;
import com.regalgrid.demo.model.User;
import com.regalgrid.demo.repository.ChatRepository;
import com.regalgrid.demo.repository.UserRepository;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;
    
    @Override
    public String postChat(Chat chat) {
        try {
            User sender = userRepository.findById(chat.getSenderId()).orElse(null);
            User receiver = userRepository.findById(chat.getReceiverId()).orElse(null);
            if (receiver != null && sender != null) {
                chatRepository.save(chat);
                return "true";
            } else if (receiver == null && sender == null) {
                return "Sender and Receiver not found";
            } else if (receiver != null && sender == null) {
                return "Sender not found";
            } else if (receiver == null && sender != null) {
                return "Receiver not found";
            } else {
                return "Unknown request";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public List<Chat> findAllChatBySenderIdAndReceiverId(String senderId, String receiverId) {
        try {
            return chatRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimeAsc(senderId, receiverId, receiverId, senderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deleteChatById(String chatId) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat != null) {
            return "true";
        }
        return "chat not found";
    }

    @Override
    public List<Chat> findAllChatByChatId(String chatId) {
        return chatRepository.findAllByChatId(chatId);
    }

}
