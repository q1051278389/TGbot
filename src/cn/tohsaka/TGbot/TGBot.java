package cn.tohsaka.TGbot;

import cn.tohsaka.TGbot.horse.HorseUtils;
import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.util.List;

public class TGBot extends AbilityBot {
    protected TGBot(String botToken, String botUsername,DefaultBotOptions botOptions) {
        super(botToken, botUsername, botOptions);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update u : updates) {
            ProcessUpdate(u);
        }
    }

    private void ProcessUpdate(Update u) {
        System.out.println(new Gson().toJson(u));
        if(u.getMessage().hasText()){
            if(u.getMessage().getText().equalsIgnoreCase("/start")){
                System.out.println("ok");
                SendMessage sd = new SendMessage();
                sd.setChatId(u.getMessage().getChatId());
                sd.setReplyMarkup(HorseUtils.getHorseList());
                sd.setText("欢迎来到赛马，请投注。\n" +
                        "游戏规则：每条马每秒前进 1-3 米，火箭加速可额外前进 16 米，但是有 50% 的概率死亡，快马加鞭可额外前进 4 米，但是有 33% 的机率摔下马，第五帧开始每帧有 10% 的概率重新上马，所有操作下一帧有效。\n" +
                        "提示：本游戏仅供娱乐，不提供赌博服务，不可提现。");


                try {
                    execute(sd);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }else{

        }
    }

    @Override
    public int creatorId() {
        return 0;
    }
}
