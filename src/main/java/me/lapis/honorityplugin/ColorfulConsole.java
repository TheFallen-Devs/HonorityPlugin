package me.lapis.honorityplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

public class ColorfulConsole {
    PluginDescriptionFile pluginDescriptionFile = null;

    /*
     * 많이 쓰이는 색깔 코드 정리 (ChatColor.<Color>)
     * */
    public final  String white = ChatColor.WHITE + "";
    public final  String red = ChatColor.RED + "";
    public final  String dred = ChatColor.DARK_RED + "";
    public final  String gold = ChatColor.GOLD + "";
    public final  String yellow = ChatColor.YELLOW + "";
    public final  String green = ChatColor.GREEN + "";
    public final  String dgreen = ChatColor.DARK_GREEN + "";
    public final String aqua = ChatColor.AQUA + "";
    public final String blue = ChatColor.BLUE + "";
    public final String dblue = ChatColor.DARK_BLUE + "";
    public final String gray = ChatColor.GRAY + "";

    /*
    많이 쓰이는 String 값 정리
    */

    // log 유형별 값들
    public final String info = green + "[Info] " + white + "";
    public final String warning = yellow + "[Warning] " + "";
    public final String error = dred + "[Error] " + red + "";
    public final String debug = dblue + "[Debug] " + blue + "";

    // 플러그인 정보 값들
    // 파일 받아온 이후에 값을 저장함.
    String plugin_name;
    String plugin_version;
    String plugin_descriptor;

    public ColorfulConsole(PluginDescriptionFile pluginDescriptionFile){
        if (pluginDescriptionFile != null){
            //플러그인 설정파일 저장
            this.pluginDescriptionFile = pluginDescriptionFile;

            //// 플러그인 정보 관련 변수 설정
            this.plugin_name = this.green + this.pluginDescriptionFile.getName() + this.white;
            this.plugin_version = this.green + this.pluginDescriptionFile.getVersion() + this.white;
            this.plugin_descriptor = "[" + plugin_name + " v" + plugin_version + "] ";
        }
        else{
            this.plugin_name = green + "LapisPlugin_Crashed" + white;
            this.plugin_version = green + "Unknown" + white;
            this.plugin_descriptor = "[" + plugin_name + " v" + plugin_version + "] ";
        }
    }

    public void console(String logType, String msg){
        Bukkit.getConsoleSender().sendMessage(plugin_descriptor + logType + msg);
    }
}
