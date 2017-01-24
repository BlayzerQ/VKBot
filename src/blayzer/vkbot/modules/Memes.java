package blayzer.vkbot.modules;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.vk;

public class Memes {

	static String[] message; 
	
	public static void Init(String uid, String[] lastMessage) throws ParseException {
		Random random = new Random();
		message = lastMessage;
		
		if(lastMessage.length >= 1) {
			if(checkMessage("двач")) {
				JSONObject messages = (JSONObject) new JSONParser().parse(vk.getPosts(-22751485, 1985));
				JSONArray response = (JSONArray) messages.get("response");
					if(response != null) {
						JSONObject json = (JSONObject) response.get(1);
						JSONArray att = (JSONArray) json.get("attachments");
						if(att != null) {
						JSONObject photo = (JSONObject) att.get(0);
						JSONObject media = (JSONObject) photo.get("photo");
						String owner_id = media.get("owner_id").toString();
						String type = photo.get("type").toString();
						String att_id = media.get("pid").toString();
						String key = media.get("access_key").toString();
						System.out.println(media);
						String attachment = type + owner_id + "_" + att_id + "_" + key;
						vk.sendMessage(uid, "Свежак с Двача!", attachment);
						} else {
							vk.sendMessage(uid, "Моча удалил тред, свежака не будет!", null);
						}

					}
			}
			else
				if(checkMessage("мемы")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(vk.getPosts(-87960594, 1985));
					JSONArray response = (JSONArray) messages.get("response");
						if(response != null) {
							JSONObject json = (JSONObject) response.get(1);
							JSONArray att = (JSONArray) json.get("attachments");
							if(att != null) {
							JSONObject photo = (JSONObject) att.get(0);
							JSONObject media = (JSONObject) photo.get("photo");
							String owner_id = media.get("owner_id").toString();
							String type = photo.get("type").toString();
							String att_id = media.get("pid").toString();
							String key = media.get("access_key").toString();
							System.out.println(media);
							String attachment = type + owner_id + "_" + att_id + "_" + key;
							vk.sendMessage(uid, "Мемосы!", attachment);
							} else {
								vk.sendMessage(uid, "Мемосы не завезли. Держу в курсе!", null);
							}

						}
				}
				else
					if(checkMessage("шк")) {
						JSONObject messages = (JSONObject) new JSONParser().parse(vk.getPosts(-83971955, 1985));
						JSONArray response = (JSONArray) messages.get("response");
							if(response != null) {
								JSONObject json = (JSONObject) response.get(1);
								JSONArray att = (JSONArray) json.get("attachments");
								if(att != null) {
								JSONObject photo = (JSONObject) att.get(0);
								JSONObject media = (JSONObject) photo.get("photo");
								String owner_id = media.get("owner_id").toString();
								String type = photo.get("type").toString();
								String att_id = media.get("pid").toString();
								String key = media.get("access_key").toString();
								System.out.println(media);
								String attachment = type + owner_id + "_" + att_id + "_" + key;
								vk.sendMessage(uid, "Баллов на " + random.nextInt(10) + " из 10", attachment);
								} else {
									vk.sendMessage(uid, "Обнаружено ЦП! Жалоба на отправителя команды была отправлена!", null);
								}

							}
					}
		}
	}
	
	public static boolean checkMessage(String word) {
		//for(String world : words) {
			if(message.length > 0 && message[1].equalsIgnoreCase(word))
				return true;
		//}
			else
				return false;
	}
	
}
