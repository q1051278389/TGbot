package cn.tohsaka.TGbot.Game;

public class Gamer {
    public static IGamer fromUserName(String username) throws Exception{
        IGamer gamer = new IGamer();
        gamer.username=username;
        gamer.doUpdate();
        return gamer;
    }
}
