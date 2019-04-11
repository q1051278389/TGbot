package cn.tohsaka.TGbot.Game;

import com.mysql.cj.MysqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class IGamer {
    protected String username;
    protected long dollar = 0;
    protected long lastTime = 0;
    private boolean registered = false;
    public String getUsername(){
        return username;
    }
    public long getLastTime(){
        return lastTime;
    }
    public long getDollar() throws  Exception{
        doUpdate();
        return dollar;
    }
    public IGamer incDollar(int amount) throws Exception{

        return doUpdate();
    }
    public IGamer getfree(long time) throws Exception{
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement("UPDATE IGAMER SET dollar=dollar+100,lasttime=? WHERE username = ?;");
        ps.setLong(1,time);
        ps.setString(2,getUsername());
        ps.execute();
        ps.close();
        conn.close();
        return doUpdate();
    }
    public IGamer decDollar(int amount) throws Exception{
        System.out.println(amount);
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement("UPDATE IGAMER SET dollar=dollar-? WHERE username = ?;");
        ps.setInt(1,amount);
        ps.setString(2,getUsername());
        ps.execute();
        ps.close();
        conn.close();
        return doUpdate();
    }
    protected IGamer doUpdate() throws Exception{
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM IGAMER WHERE username = ?;");
        ps.setString(1,getUsername());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            lastTime=rs.getLong(2);
            dollar=rs.getInt(3);
            registered=true;
        }
        rs.close();
        ps.close();
        conn.close();
        return this;
    }
    public boolean isRegistered() throws Exception{
        return registered;
    }

    public void doReg() throws Exception{
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO IGAMER(username) VALUES(?) ON DUPLICATE KEY UPDATE dollar=dollar;");
        ps.setString(1,getUsername());
        ps.execute();
        ps.close();
        conn.close();
        doUpdate();
    }
    public Connection getConnection() throws Exception{
        return DriverManager.getConnection("jdbc:mysql://ddns.tohsaka.cn:3306/tgbot?serverTimezone=GMT%2B8","root","19961211");
    }
}
