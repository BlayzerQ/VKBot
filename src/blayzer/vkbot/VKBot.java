package blayzer.vkbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import blayzer.vkbot.api.Utils;
import blayzer.vkbot.api.VK;
import blayzer.vkbot.modules.Posts;
import blayzer.vkbot.modules.Chat;
import blayzer.vkbot.modules.Messages;
import blayzer.vkbot.modules.Shedule;
import blayzer.vkbot.modules.Sites;

public class VKBot {
	
	public static String prefixes = "!, фб, файнбот, вб, вкбот";
	public static String vkToken = "";
	public static String wordsBlacklist = "цп, лоли, дп";
	public static String blacklist = "0, 0";
	public static boolean debug = false;
	public static boolean chat = false;
	public static String[] lastMessage;
	
	public void Init() {
		Utils.logging(Level.INFO, "VKBot запускается...");
		Properties config = new Properties();
		try {
			BufferedReader configFile = new BufferedReader(new InputStreamReader(new FileInputStream("config.properties"), "UTF8"));
			config.load(configFile);
			prefixes = config.getProperty("prefixes");
			blacklist = config.getProperty("blacklist");
			wordsBlacklist = config.getProperty("wordsBlacklist");
			debug = Boolean.valueOf(config.getProperty("debug"));
			chat = Boolean.valueOf(config.getProperty("chat"));
			vkToken = config.getProperty("vkToken");
		} catch (IOException e) {
			Utils.logging(Level.WARNING, "Не найден файл конфигурации. Будут использованы стандартные значения.");
		}
		Shedule.Init();
		Utils.logging(Level.INFO, "Приступаю к приему сообщений.");
		Work();
	}
	
	public void Work(){
		try {
			while(true){
				
				JSONObject messages = (JSONObject) new JSONParser().parse(VK.getMessages());
				JSONObject response = (JSONObject) messages.get("response");
					if(response != null) {
						JSONArray items = (JSONArray) response.get("items");
						for(int i=0; i < items.size(); i++){
							//Добавить тут мультипоточность
							JSONObject json = (JSONObject) items.get(i);
							String uid = "";
							if(json.get("chat_id") != null) {
								uid = "chat_id=" + json.get("chat_id").toString();
							} else {
								uid = "user_id=" + json.get("user_id").toString();
							}
							Long status = (Long) json.get("read_state");
							String fullMessage = (String)json.get("body");
							lastMessage = fullMessage.split(" ");
							
							//Обработка сообщений без префиксов
							if(status == 0 && chat) {
								Chat.Init(uid, lastMessage);
							}
							
							//Обработка сообщений с префиксами
							if(prefixes.contains(lastMessage[0]) && status == 0 && lastMessage.length >= 2){
								if(!blacklist.contains(uid)) {
									VK.setAsRead(uid);
									Utils.logging(Level.INFO, "Сообщение от " + uid.replaceAll("_id=", " id") 
									+ ": " + fullMessage);
								
									Messages.Init(uid, lastMessage);
									Posts.Init(uid, lastMessage);
									Sites.Init(uid, lastMessage);
								}
							}
						}
					}
					TimeUnit.SECONDS.sleep(1);
				}
		} catch (Exception e) {
			e.printStackTrace();
			Utils.logging(Level.SEVERE, e.getMessage());
		}
	}

	public static void main(String[] args){
		VKBot vkbot = new VKBot();
		vkbot.Init();
	}
	
}
