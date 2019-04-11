package cn.tohsaka.TGbot.Game;

import cn.tohsaka.TGbot.horse.HorseGame;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerShippingQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.client.Entity;
import java.util.Date;

public class GameRender {
    public static boolean renderUpdate(Update u, AbilityBot bot) throws Exception{
        if(u.hasMessage() && u.getMessage().hasText()){
            if(u.getMessage().getText().startsWith("/horse")){
                bot.execute(HorseGame.GenerateMenu().setChatId(u.getMessage().getChatId()));
            }
        }
        if(u.hasCallbackQuery()){
            String data = u.getCallbackQuery().getData();
            if(data.startsWith("global")){
                SendMessage sd = GameRender.renderGame(u,bot);
                if(sd != null){
                    bot.execute(sd);
                }
                return true;
            }
            if(data.startsWith("horse")){
                SendMessage sd = HorseGame.renderGame(u);
                if(sd != null){
                    bot.execute(sd);
                }
                return true;
            }
        }
        return false;
    }

    private static SendMessage renderGame(Update u, AbilityBot bot) throws Exception{
        String data = u.getCallbackQuery().getData();
        IGamer gamer = Gamer.fromUserName(u.getCallbackQuery().getFrom().getUserName());
        if(data.contains("getfreedollar")){
            if((new Date().getTime()-gamer.lastTime)>3600000){
                if(gamer.isRegistered()){
                    gamer.getfree(new Date().getTime());
                    bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("签到成功,获得$100").setShowAlert(true));
                }else{
                    gamer.doReg();
                    bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("您是第一次进行游戏,本次赠送您$2000\n请详细阅读各游戏规则后进行游戏").setShowAlert(true));
                }

            }else{
                bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("两次签到需要相隔至少 1 小时").setShowAlert(true));
            }
        }


        if(data.contains("getdollar")){
            if(gamer.isRegistered()){
                bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("您的余额：$"+gamer.getDollar()).setShowAlert(true));
            }else{
                gamer.doReg();
                bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("您是第一次进行游戏,本次赠送您$2000\n请详细阅读各游戏规则后进行游戏").setShowAlert(true));
            }
        }
        return null;
    }
}
