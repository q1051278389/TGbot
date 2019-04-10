package cn.tohsaka.TGbot.horse;

import it.rebase.rebot.api.emojis.Emoji;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class HorseUtils {
    public static InlineKeyboardMarkup getHorseList(){

        String horse_icon = "   \uD83D\uDC34";
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> commands = new ArrayList<>();

        List<InlineKeyboardButton> commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText("  $5   ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText("  $10  ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText("  $50  ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText("  $100  ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText("  $500  ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText("  梭哈  ").setCallbackData("0"));
        commands.add(commandRow);
        commandRow = new ArrayList<>();
        commandRow.add(new InlineKeyboardButton().setText(" \uD83C\uDFB2 开始  ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText(" ⚽ 签到 ").setCallbackData("0"));
        commandRow.add(new InlineKeyboardButton().setText(" \uD83D\uDCB5 余额").setCallbackData("0"));
        commands.add(commandRow);
        replyKeyboardMarkup.setKeyboard(commands);
        return replyKeyboardMarkup;
    }
}
