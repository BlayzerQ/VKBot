package blayzer.vkbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.modules.Posts;
import blayzer.vkbot.modules.Chat;
import blayzer.vkbot.modules.Messages;
import blayzer.vkbot.modules.Shedule;
import blayzer.vkbot.modules.Sites;
import blayzer.vkbot.utils.IIIAPI;
import blayzer.vkbot.utils.Utils;
import blayzer.vkbot.utils.VK;

public class VKBot {
	
	public static String prefixes = "!, фб, файнбот, вб, вкбот";
	public static String[] vkTokens;
	public static String wordsBlacklist = "цп, лоли, дп";
	public static String blacklist = "0, 0";
	public static boolean debug = false;
	public static boolean chat = false;
	public static boolean chatVoice = false;
	public static String iiiBotId = "";
	public static String[] lastMessage;
	
	public static Map<String, String> IIIsessions = new HashMap<String, String>();
	public static int tokenid = 0;
	
	public void Init() {
		Utils.logging(Level.INFO, "VKBot запускается...");
		Properties config = new Properties();
		try {
			File file = new File("config.properties");
			if(!file.exists()){
				InputStream link = (VKBot.class.getResourceAsStream("config.properties"));
			    Files.copy(link, file.getAbsoluteFile().toPath());
			}
			BufferedReader configFile = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			config.load(configFile);
			prefixes = config.getProperty("prefixes");
			blacklist = config.getProperty("blacklist");
			wordsBlacklist = config.getProperty("wordsBlacklist");
			debug = Boolean.valueOf(config.getProperty("debug"));
			chat = Boolean.valueOf(config.getProperty("chat"));
			chatVoice = Boolean.valueOf(config.getProperty("chatVoice"));
			vkTokens = config.getProperty("vkToken").split(";");
			iiiBotId = config.getProperty("iiiBotId");
		} catch (IOException e) {
			Utils.logging(Level.WARN, "Не найден файл конфигурации. Будут использованы стандартные значения.");
		}
		Shedule.Init();
		Utils.logging(Level.INFO, "Приступаю к приему сообщений.");
		Work();
	}
	
	public void Work(){
		//Runnable VKBot = () -> {
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
							
							//Обработка сообщений с префиксами (Сделать нормальную проверку префиксов, без ложных страбатываний)
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
					//Utils.logging(Level.INFO, Utils.getToken());
					TimeUnit.SECONDS.sleep(1);
				}
		} catch (Exception e) {
			e.printStackTrace();
			Utils.logging(Level.ERROR, e.getMessage());
		}
		//};
		//new Thread(VKBot).start();
	}

	public static void main(String[] args){
		VKBot vkbot = new VKBot();
		vkbot.Init();
	}
	
}
