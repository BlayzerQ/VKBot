package blayzer.vkbot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import blayzer.vkbot.api.vk;
import blayzer.vkbot.modules.Memes;
import blayzer.vkbot.modules.Messages;

public class VKBot {
	
	public static String prefixes = "! фб вб файнбот вкбот";
	//public static String[] prefixes = {"!", "фб", "файнбот", "вкбот"};
	
	public void Init() {
		System.out.println("Бот запущен! Авторизация...");
		vk.setOnline();
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
				
				JSONObject messages = (JSONObject) new JSONParser().parse(vk.getMessages());
				JSONArray response = (JSONArray) messages.get("response");
					if(response != null) {
						JSONObject json = (JSONObject) response.get(1);
						String uid = json.get("uid").toString();
						Long status = (Long) json.get("read_state");
						String fullMessage = (String )json.get("body");
						String[] lastMessage = fullMessage.split(" ");

						if(prefixes.contains(lastMessage[0]) && status == 0){
							vk.setAsRead(uid);
							System.out.println("Команда от " + "id"+ uid + ": " + fullMessage);
							
							Messages.Init(uid, lastMessage);
							Memes.Init(uid, lastMessage);
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
