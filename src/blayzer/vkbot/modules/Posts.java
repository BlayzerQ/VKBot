package blayzer.vkbot.modules;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.Utils;
import blayzer.vkbot.api.VK;

public class Posts {

	static String[] message; 
	
	public static void Init(String uid, String[] lastMessage) throws ParseException {
		Random random = new Random();
		message = lastMessage;
		
		if(lastMessage.length >= 1) {
			if(Utils.checkMessage("двач")) {
				JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-22751485, 1985));
				JSONArray response = (JSONArray) messages.get("response");
					if(response != null) {
						if(response != null) {
							VK.sendMessage(uid, "Свежак с Двача!", Utils.getAttachMedia(response));
						}
					}
			}
			else
				if(Utils.checkMessage("мемы")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-87960594, 1985));
					JSONArray response = (JSONArray) messages.get("response");
						if(response != null) {
							if(response != null) {
								VK.sendMessage(uid, "Мемосы!", Utils.getAttachMedia(response));
							}

						}
				}
			else
				if(Utils.checkMessage("шк")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-83971955, 1985));
					JSONArray response = (JSONArray) messages.get("response");
						if(response != null) {
							if(response != null) {
								VK.sendMessage(uid, "Баллов на " + random.nextInt(10) + " из 10", Utils.getAttachMedia(response));
							}
						}
				}
			else
				if(Utils.checkMessage("сиськи")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-20282193, 800));
					JSONArray response = (JSONArray) messages.get("response");
						if(response != null) {
							if(response != null) {
								VK.sendMessage(uid, "Сиськи поданы!", Utils.getAttachMedia(response));
							}

						}
				}
			else
				if(Utils.checkMessage("фм")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-35140461, 1100));
					JSONArray response = (JSONArray) messages.get("response");
						if(response != null) {
							if(response != null) {
								VK.sendMessage(uid, "Куйня какая-то!", Utils.getAttachMedia(response));
							}

						}
				}
			else
				if(Utils.checkMessage("шкуры")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-83971955, 1985));
					JSONArray response = (JSONArray) messages.get("response");
						if(response != null) {
							VK.sendMessage(uid, "Как вам эта?", Utils.getAttachMedia(response));
						}
				}
		}
	}
	
}
