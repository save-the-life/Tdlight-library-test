package com.example.TDLight_server.config;

import it.tdlight.Init;
import it.tdlight.Log;
import it.tdlight.Slf4JLogMessageHandler;
import it.tdlight.client.APIToken;
import it.tdlight.client.SimpleTelegramClientFactory;
import it.tdlight.client.TDLibSettings;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.lang.System.getenv;

@Component
public class TelegramClientInitializer {

    private SimpleTelegramClientFactory clientFactory;
    private TDLibSettings settings;

    @PostConstruct
    public void init() throws Exception {
        // init TDLight
        Init.init();

        // setting log
        Log.setLogMessageHandler(1, new Slf4JLogMessageHandler());

        // create client factory
        clientFactory = new SimpleTelegramClientFactory();

        Map<String, String> env = getenv();
        Integer userId = Integer.parseInt(env.get("Id"));
        String userHas = env.get("hash");
        // setting API token
        APIToken apiToken = new APIToken(userId, userHas);

        // setting client
        settings = TDLibSettings.create(apiToken);
        Path sessionPath = Paths.get("sl-session");
        settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));
        settings.setDownloadedFilesDirectoryPath(sessionPath.resolve("downloads"));
    }

    @PreDestroy
    public void shutdown() {
        if (clientFactory != null) {
            clientFactory.close();
        }
    }

    public SimpleTelegramClientFactory getClientFactory() {
        return clientFactory;
    }

    public TDLibSettings getSettings() {
        return settings;
    }
}
