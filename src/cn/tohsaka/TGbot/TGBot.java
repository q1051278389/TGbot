package cn.tohsaka.TGbot;

import cn.tohsaka.TGbot.Game.GameRender;
import cn.tohsaka.TGbot.horse.HorseGame;
import cn.tohsaka.TGbot.horse.HorseUtils;
import com.google.gson.Gson;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.util.List;

public class TGBot extends AbilityBot {
    public static TGBot bot;
    protected TGBot(String botToken, String botUsername,DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
        TGBot.bot=this;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        try{
            for (Update u : updates) {
                ProcessUpdate(u);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ProcessUpdate(Update u) throws Exception{
        System.out.println(new Gson().toJson(u));
        if(GameRender.renderUpdate(u,this)){
            return;
        }

    }

    @Override
    public int creatorId() {
        return 0;
    }
}
