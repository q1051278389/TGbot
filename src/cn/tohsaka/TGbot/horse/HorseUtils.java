package cn.tohsaka.TGbot.horse;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class HorseUtils {
    public static InlineKeyboardMarkup getMenuMarkup(long id){

        String horse_icon = "   \uD83D\uDC34";
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> commands = new ArrayList<>();

        List<InlineKeyboardButton> commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText("$5").setCallbackData(String.format("horse/%d/inc/5",id)));
        commandRow.add(new InlineKeyboardButton().setText("$10").setCallbackData(String.format("horse/%d/inc/10",id)));
        commandRow.add(new InlineKeyboardButton().setText("$50").setCallbackData(String.format("horse/%d/inc/50",id)));
        commandRow.add(new InlineKeyboardButton().setText("$100").setCallbackData(String.format("horse/%d/inc/100",id)));
        commandRow.add(new InlineKeyboardButton().setText("$500").setCallbackData(String.format("horse/%d/inc/500",id)));
        commandRow.add(new InlineKeyboardButton().setText("梭哈").setCallbackData(String.format("horse/%d/inc/all",id)));
        commands.add(commandRow);
        commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText("\uD83C\uDFB2 开始").setCallbackData(String.format("horse/%d/start",id)));
        commandRow.add(new InlineKeyboardButton().setText("⚽ 签到").setCallbackData("global/getfreedollar"));
        commandRow.add(new InlineKeyboardButton().setText("\uD83D\uDCB5 余额").setCallbackData("global/getdollar"));
        commands.add(commandRow);
        replyKeyboardMarkup.setKeyboard(commands);
        return replyKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getHorseMarkUp(Long game_id) {
        String horse_icon = "\uD83D\uDC34";
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> commands = new ArrayList<>();
        List<InlineKeyboardButton> commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText(horse_icon+" 1").setCallbackData(String.format("horse/%d/select_horse/1",game_id)));
        commandRow.add(new InlineKeyboardButton().setText(horse_icon+" 2").setCallbackData(String.format("horse/%d/select_horse/2",game_id)));
        commandRow.add(new InlineKeyboardButton().setText(horse_icon+" 3").setCallbackData(String.format("horse/%d/select_horse/3",game_id)));
        commands.add(commandRow);
        commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText(horse_icon+" 4").setCallbackData(String.format("horse/%d/select_horse/4",game_id)));
        commandRow.add(new InlineKeyboardButton().setText(horse_icon+" 5").setCallbackData(String.format("horse/%d/select_horse/5",game_id)));
        commandRow.add(new InlineKeyboardButton().setText(horse_icon+" 6").setCallbackData(String.format("horse/%d/select_horse/6",game_id)));
        commands.add(commandRow);
        commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText("强制结算").setCallbackData(String.format("horse/%d/start_game",game_id)));
        commands.add(commandRow);
        replyKeyboardMarkup.setKeyboard(commands);
        return replyKeyboardMarkup;
    }
}
