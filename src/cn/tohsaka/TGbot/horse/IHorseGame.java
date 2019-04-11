package cn.tohsaka.TGbot.horse;

import cn.tohsaka.TGbot.TGBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IHorseGame {
    public Map<String,Long> metadata; // [chat_id,lastdoing,stage,fps]
    public Map<String,Integer> coin;  //xmsoft -> $10
    public Map<String,Integer> horse_selected; // xmsoft -> 4;
    public Map<String,Integer> progress; // 🐎 1 -> 15
    public Map<String,String> userinfo;
    public IHorseGame(Long game_id,Long chat_id,int message_id){
        metadata=new HashMap<>();
        metadata.put("game_id",game_id);
        metadata.put("chat_id",chat_id);
        metadata.put("lastdoing",-1L);
        metadata.put("message_id",(long)message_id);
        metadata.put("stage",0L);
        metadata.put("fps",-2L);
        metadata.put("start_time",new Date().getTime());
        coin = new HashMap<>();
        horse_selected = new HashMap<>();
        progress = new HashMap<>();
        userinfo = new HashMap<>();
    }
    public IHorseGame addUserInfo(String username,String displayName){
        userinfo.put(username,(displayName.length()>10)?displayName.substring(0,16):displayName);
        return this;
    }
    public IHorseGame setMetaData(String key,Long data){
        if(metadata.containsKey(key)){
            metadata.replace(key,data);
            return this;
        }else{
            metadata.put(key,data);
            return this;
        }
    }
    public IHorseGame inc_Coin(String username,int amount) throws Exception{
        if(coin.containsKey(username)){
            coin.replace(username,coin.get(username)+amount);
        }else{
            coin.put(username,amount);
        }
        updateStatus();
        return this;
    }
    public IHorseGame selectHorse(String username,int index) throws Exception{
        if(horse_selected.containsKey(username)){
            horse_selected.replace(username,index);
        }else{
            horse_selected.put(username,index);
        }
        updateStatus();
        return this;
    }
    public void updateStatus() throws Exception{
        TGBot.bot.execute(new EditMessageText()
                .setChatId(metadata.get("chat_id"))
                .setMessageId(metadata.get("message_id").intValue())
                .setReplyMarkup(getStageMenu())
                .setText(PrepareMsg())
        );
    }
    public int getStage(){
        return metadata.get("stage").intValue();
    }
    private InlineKeyboardMarkup getStageMenu(){
        if(getStage()==1){
                return HorseUtils.getHorseMarkUp(metadata.get("game_id"));
        }
        return HorseUtils.getMenuMarkup(metadata.get("game_id"));
    }
    private String PrepareMsg() {
        String menu = "";
        if(getStage()==0){
            menu= "\uD83C\uDFB2赛马 投注中\n\n"+metadata.get("game_id")+"\n\n玩家列表：\n";
            for(String k : coin.keySet()){
                int after_coin = Integer.parseInt(userinfo.get(k+"_allcoin"))-coin.get(k);
                menu+=String.format("%s :\uD83D\uDCB0%d(%s)\n",userinfo.get(k),coin.get(k),(after_coin>0)?after_coin:0);
            }
        }
        if(getStage()==1){
            menu= "\uD83C\uDFB2赛马 选\uD83D\uDC34中\n\n"+metadata.get("game_id")+"\n\n玩家列表：\n";
            for(String k : coin.keySet()){
                int num = horse_selected.getOrDefault(k,0);
                menu+=String.format("%s : %s",userinfo.get(k),(num!=0)?"\uD83D\uDC34"+num:"\uD83D\uDD52未完成");
            }
        }
        return menu+"\n";
    }
}
