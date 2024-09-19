package com.example.TDLight_server.controller;

import com.example.TDLight_server.service.TelegramService;
import it.tdlight.jni.TdApi;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class TelegramController {
    private final TelegramService telegramService;

    public TelegramController(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @GetMapping("/get-me")
    public TdApi.User getMe() throws ExecutionException, InterruptedException {
        return telegramService.getMe().get();
    }

    @GetMapping("/get-chats")
    public TdApi.Chats getChats() throws ExecutionException, InterruptedException {
        return telegramService.getChats().get();
    }

    @PostMapping("/get-chat-member")
    public TdApi.ChatMember getChatMember(@RequestBody GetChatMemberRequest request) throws ExecutionException, InterruptedException {
        return telegramService.getChatMember(request.getChatId(), request.getUserId()).get();
    }
    public static class GetChatMemberRequest {
        private long chatId;
        private long userId;

        public long getChatId() {
            return chatId;
        }

        public void setChatId(long chatId) {
            this.chatId = chatId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
