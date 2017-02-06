package blayzer.vkbot;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import blayzer.vkbot.api.VK;
import blayzer.vkbot.modules.Posts;
import blayzer.vkbot.modules.Messages;
import blayzer.vkbot.modules.Shedule;
import blayzer.vkbot.modules.Sites;

public class VKBot {
	
	public static String prefixes = "!, фб, файнбот, вб, вкбот";
	public static String blacklist = "0, 0";
	public static String[] lastMessage;
	
	public void Init() {
		System.out.println("VKBot запущен! Авторизация...");
		Shedule.Init();
		System.out.println("Приступаю к приему сообщений.");
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

							if(prefixes.contains(lastMessage[0]) && status == 0 && lastMessage.length >= 2){
								if(!blacklist.contains(uid)) {
									VK.setAsRead(uid);
									System.out.println("Сообщение от " + uid.replaceAll("_id=", " id") 
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
		}
	}

	public static void main(String[] args){
		VKBot vkbot = new VKBot();
		vkbot.Init();
	}
	
}
