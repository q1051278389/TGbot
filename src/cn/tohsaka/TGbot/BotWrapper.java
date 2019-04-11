package cn.tohsaka.TGbot;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Date;

public class BotWrapper {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost("127.0.0.1");
        botOptions.setProxyPort(1082);

        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        TGBot bot = new TGBot("704551573:AAEymuzbV-OH02Zlggf6XRAKq9EWydPlqhw", "TGBot", botOptions);
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}