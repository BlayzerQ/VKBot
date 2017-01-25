package blayzer.vkbot.modules;

import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.VK;

public class Posts {

	static String[] message; 
	
	public static void Init(String uid, String[] lastMessage) throws ParseException {
		Random random = new Random();
		message = lastMessage;
		
		if(lastMessage.length >= 1) {
			if(checkMessage("двач")) {
				JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-22751485, 1985));
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
						String attachment = type + owner_id + "_" + att_id + "_" + key;
						VK.sendMessage(uid, "Свежак с двача!", attachment);
						} else {
							VK.sendMessage(uid, "Моча удалил тред, свежака не будет!", null);
						}

					}
			}
			else
				if(checkMessage("мемы")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-87960594, 1985));
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
							String attachment = type + owner_id + "_" + att_id + "_" + key;
							VK.sendMessage(uid, "Мемосы!", attachment);
							} else {
								VK.sendMessage(uid, "Мемосы не завезли. держу в курсе!", null);
							}

						}
				}
				else
					if(checkMessage("шк")) {
						JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-83971955, 1985));
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
								String attachment = type + owner_id + "_" + att_id + "_" + key;
								VK.sendMessage(uid, "Баллов на " + random.nextInt(10) + " из 10", attachment);
								} else {
									VK.sendMessage(uid, "Обнаружено ЦП! Жалоба на отправителя команды была отправлена!", null);
								}

							}
					}
					else
						if(checkMessage("сиськи")) {
							JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-20282193, 800));
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
									String attachment = type + owner_id + "_" + att_id + "_" + key;
									VK.sendMessage(uid, "Сиськи поданы!", attachment);
									} else {
										VK.sendMessage(uid, "Нулевочку нашел! Не, не буду скидывать.", null);
									}

								}
						}
						else
							if(checkMessage("фм")) {
								JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-35140461, 1100));
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
										String attachment = type + owner_id + "_" + att_id + "_" + key;
										VK.sendMessage(uid, "Куйня какая-то!", attachment);
										} else {
											VK.sendMessage(uid, "Зашкварные! Не скину!", null);
										}

									}
							}
							else
								if(checkMessage("шкуры")) {
									JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-83971955, 1985));
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
											String attachment = type + owner_id + "_" + att_id + "_" + key;
											VK.sendMessage(uid, "Как вам эта?", attachment);
											} else {
												VK.sendMessage(uid, "Ошибка получения шкур", null);
											}

										}
								}
		}
	}
	
	public static boolean checkMessage(String word) {
		if(message.length >= 2) {
		//for(String world : words) {
			if(message[1].equalsIgnoreCase(word)) {
				return true;
		//}
			} else
				return false;
		} return false;
	}
	
}
