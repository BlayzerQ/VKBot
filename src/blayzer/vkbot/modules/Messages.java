package blayzer.vkbot.modules;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.VKBot;
import blayzer.vkbot.api.VK;
import blayzer.vkbot.api.Utils;

public class Messages {
	
	public static void Init(String uid, String[] lastMessage) throws ParseException, IOException {
		Random random = new Random();
		
			if(Utils.checkMessage("привет", "здарова")) {
				String[] answers = {"Готов служить!", "Здарова, тварына!", "Слава Украине!",
						"Здарова, привет, привет, здарова!", "Привет, молодой!"}; 
				VK.sendMessage(uid, answers[random.nextInt(4)], null);
			}
			else
				if(Utils.checkMessage("луна"))
					VK.sendMessage(uid, "&#127770;", null);
			else
				if(Utils.checkMessage("команды"))
						VK.sendMessage(uid, "Список доступных команд: \nпривет, \nлуна, \nкоманды,"
								+ "\nвремя, \nшар, \nдвач, \nмемы, \nшк, \nсиськи, \nфм, "
								+ "\nонлайн, \nшкуры, \nкурс", null);
			else
				if(Utils.checkMessage("время")) {
					Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
					Date curDate = calendar.getTime();
						VK.sendMessage(uid, curDate.toString(), null);
				}
			else
				if(Utils.checkMessage("шар", "скажи")){
					String[] answers = {"Да", "Конечно", "Не думаю",
							"Нет", "Знаки говорят - да", "Не сомненно!",
							"Скорее да, чем нет", "Не могу решить",
							"Мой ответ - нет", "Да, но только если ты не смотришь аниме"}; 
					VK.sendMessage(uid, answers[random.nextInt(9)], null);
				}
			else
				if(Utils.checkMessage("напиши")) {
					if(VKBot.lastMessage.length > 2) {
						StringBuilder fullMessage = new StringBuilder();
						for (int i = 3; i < lastMessage.length; i++) {
							fullMessage.append(lastMessage[i] + " ");
						}
						VK.sendMessage("user_id=" + VKBot.lastMessage[2], "Привет! Меня тут попросили тебе написать: \n"
								+ fullMessage, null);
						VK.sendMessage(uid, "Сообщение отправлено.", null);
					} else
						VK.sendMessage(uid, "Кому и что мне написать?", null);
				}
				else
					if(Utils.checkMessage("найди")) {
						if(VKBot.lastMessage.length > 2) {
							JSONObject request = (JSONObject) new JSONParser().parse(VK.searchVideo(VKBot.lastMessage[2]));
							JSONArray response = (JSONArray) request.get("response");
								if(response != null) {
									JSONObject json = (JSONObject) response.get(1);
									System.out.println(json);
									if(json != null) {
										String owner_id = json.get("owner_id").toString();
										String id = json.get("id").toString();
									VK.sendMessage(uid, "Вот, что я нашел", "video" + owner_id + "_" + id);
									} else {
										VK.sendMessage(uid, "Ошибка поиска", null);
									}

								}
						} else
							VK.sendMessage(uid, "Что мне искать?", null);
					}
//			else
//				if(Utils.checkMessage("музыка")) {
//					JSONObject audios = (JSONObject) new JSONParser().parse(VK.getAudioRecomendations(
//							Integer.valueOf(uid.replaceAll("user_id=", "")), 5));
//					JSONArray response = (JSONArray) audios.get("response");
//						if(response != null) {
//							 VK.sendMessage(uid, "Вот твоя музыка!", Utils.getAttachMedia(response);
//
//						}
//				}
	}
}