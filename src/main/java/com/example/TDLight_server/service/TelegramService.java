package com.example.TDLight_server.service;

import com.example.TDLight_server.config.TelegramClientInitializer;
import it.tdlight.client.*;
import it.tdlight.jni.TdApi;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TelegramService {

    private SimpleTelegramClient client;


    public TelegramService(TelegramClientInitializer initializer) {
        // get the client factory and settings from initialization object
        SimpleTelegramClientFactory clientFactory = initializer.getClientFactory();
        TDLibSettings settings = initializer.getSettings();

        // create client builder
        SimpleTelegramClientBuilder clientBuilder = clientFactory.builder(settings);

        // set certification
        ConsoleInteractiveAuthenticationData authenticationData = AuthenticationSupplier.consoleLogin();

        // create client
        this.client = clientBuilder.build(authenticationData);
    }

    @PreDestroy
    public void shutdownClient() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    // getMe method : get the api user's information
    public CompletableFuture<TdApi.User> getMe() {
        TdApi.GetMe getMe = new TdApi.GetMe();
        return client.send(getMe);
    }


    // getChats method : get chatting room id lists that api user is involved
    public CompletableFuture<TdApi.Chats> getChats() {
        TdApi.GetChats getChats = new TdApi.GetChats();
        getChats.limit = 20;  // set the number of chatting room
        return client.send(getChats);
    }

    // getChatMember method : get the specific user's information in the chatting room
    public CompletableFuture<TdApi.ChatMember> getChatMember(long chatId, long userId) {
        TdApi.MessageSenderUser memberId = new TdApi.MessageSenderUser(userId);
        TdApi.GetChatMember getChatMember = new TdApi.GetChatMember(chatId, memberId);
        return client.send(getChatMember);
    }
}
