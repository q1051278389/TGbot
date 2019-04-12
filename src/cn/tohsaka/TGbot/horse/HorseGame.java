package cn.tohsaka.TGbot.horse;

import cn.tohsaka.TGbot.Game.Gamer;
import cn.tohsaka.TGbot.TGBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HorseGame {
    public static Map<Long,IHorseGame> games = new HashMap<>();
    public static SendMessage GenerateMenu(){
        SendMessage sd = new SendMessage();
        sd.setReplyMarkup(HorseUtils.getMenuMarkup(generateUniversalId()));
        sd.setText("欢迎来到赛马，请投注。\n游戏规则：赛道全长 50 米，每条马每秒前进 1-5 米，虫洞跃迁可额外前进 30 米，但是有 80% 的概率失踪，火箭加速可额外前进 16 米，但是有 50% 的概率死亡，快马加鞭可额外前进 4 米，但是有 33% 的概率摔下马，第五帧开始每帧有 10% 的概率重新上马，所有操作下一帧有效。\n提示：本游戏仅供娱乐，不提供赌博服务，不可提现。");
        return sd;
    }
    private static long generateUniversalId(){
        Long time = new Date().getTime();
        return Math.abs(new Random(time).nextLong()+time);
    }
    public static SendMessage renderGame(Update u) throws Exception {
        String args[] = u.getCallbackQuery().getData().split("/");
        Long game_id = Long.parseLong(args[1]);
        if (args[2].equalsIgnoreCase("inc")) {
            if(games.get(game_id) == null || (games.get(game_id).metadata != null && games.get(game_id).metadata.get("stage")==0L)){
                Long all_coin = Gamer.fromUserName(u.getCallbackQuery().getFrom().getUserName()).getDollar();
                if(!(args[3].equalsIgnoreCase("all")) && Integer.parseInt(args[3])>all_coin){
                    TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("资金不足").setShowAlert(true));
                    return null;
                }
                //broken coin not enougth ↑
                if (!games.containsKey(game_id)) {
                    games.put(game_id, new IHorseGame(game_id,u.getCallbackQuery().getMessage().getChatId(),u.getCallbackQuery().getMessage().getMessageId()));
                }
                Gamer.fromUserName(u.getCallbackQuery().getFrom().getUserName()).decDollar((args[3].equalsIgnoreCase("all"))? all_coin.intValue() :Integer.parseInt(args[3]));
                games.get(game_id)
                        .setMetaData("lastdoing",new Date().getTime())
                        .addUserInfo(u.getCallbackQuery().getFrom().getUserName()+"_allcoin",all_coin.toString())
                        .addUserInfo(u.getCallbackQuery().getFrom().getUserName(),u.getCallbackQuery().getFrom().getFirstName()+" "+u.getCallbackQuery().getFrom().getLastName())
                        .inc_Coin(u.getCallbackQuery().getFrom().getUserName(), (args[3].equalsIgnoreCase("all"))? all_coin.intValue() :Integer.parseInt(args[3]));
            }else{
                TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("投注错误，游戏已经开始").setShowAlert(true));
            }

        }
        if(args[2].equalsIgnoreCase("start")){
            if(games.get(game_id).metadata.get("stage")==0){
                if((new Date().getTime()-games.get(game_id).metadata.get("lastdoing"))<5000){
                    TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("5 秒内无人下注才可手动开始").setShowAlert(true));
                }else{
                    games.get(game_id).setMetaData("stage",1L);
                    games.get(game_id).updateStatus();
                }
            }else{
                TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("游戏已经开始").setShowAlert(true));
            }
        }
        if(args[2].equalsIgnoreCase("select_horse")){
            if(games.get(game_id).metadata.get("stage").intValue()==1){
                games.get(game_id).setMetaData("lastdoing",new Date().getTime()).selectHorse(u.getCallbackQuery().getFrom().getUserName(), Integer.parseInt(args[3]));
            }else{
                TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("游戏已经开始").setShowAlert(true));
            }
        }
        if(args[2].equalsIgnoreCase("start_game")){ //stage 2
            if((new Date().getTime()-games.get(game_id).metadata.get("lastdoing"))<10000){
                TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()).setText("10 秒内无人操作才可强制结算").setShowAlert(true));
            }else{
                games.get(game_id).startGame();
            }
        }
        TGBot.bot.execute(new AnswerCallbackQuery().setCallbackQueryId(u.getCallbackQuery().getId()));
        return null;
    }

}

