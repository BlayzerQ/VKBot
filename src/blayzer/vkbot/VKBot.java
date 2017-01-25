package blayzer.vkbot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import blayzer.vkbot.api.VK;
import blayzer.vkbot.modules.Posts;
import blayzer.vkbot.modules.Messages;
import blayzer.vkbot.modules.Sites;

public class VKBot {
	
	public static String prefixes = "! фб вб файнбот вкбот";
	public static String[] lastMessage;
	//public static String[] prefixes = {"!", "фб", "файнбот", "вкбот"};
	
	public void Init() {
		System.out.println("VKBot запущен! Авторизация...");
		VK.setOnline();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Приступаю к приему сообщений.");
		Work();
	}
	
	public void Work(){
		try {
			while(true){
				
				JSONObject messages = (JSONObject) new JSONParser().parse(VK.getMessages());
				JSONArray response = (JSONArray) messages.get("response");
					if(response != null) {
						//Добавить тут мультипоточную обработку сообщений циклом
						JSONObject json = (JSONObject) response.get(1);
						String uid = "";
						if(json.get("chat_id") != null) {
							uid = "chat_id=" + json.get("chat_id").toString();
						} else {
							uid = "user_id=" + json.get("uid").toString();
						}
						Long status = (Long) json.get("read_state");
						String fullMessage = (String )json.get("body");
						lastMessage = fullMessage.split(" ");

						if(prefixes.contains(lastMessage[0]) && status == 0 && lastMessage.length >= 2){
							VK.setAsRead(uid);
							System.out.println("Сообщение от " + uid.replaceAll("_id=", " id") 
									+ ": " + fullMessage);
							
							Messages.Init(uid, lastMessage);
							Posts.Init(uid, lastMessage);
							Sites.Init(uid, lastMessage);
						}
					}
				
					Thread.sleep(400);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		VKBot vkbot = new VKBot();
		vkbot.Init();
	}
	
}
