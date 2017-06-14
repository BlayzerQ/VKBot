package blayzer.vkbot.modules;

import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.utils.Utils;
import blayzer.vkbot.utils.VK;

public class Posts {

	public static void Init(String uid, String[] lastMessage) throws ParseException {
		Random random = new Random();
		
		if(lastMessage.length >= 1) {
			if(Utils.checkMessage("двач")) {
				JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-22751485, 1985));
				JSONObject response = (JSONObject) messages.get("response");
					if(response != null) {
						String answer = Utils.getRandomMessage("Свежак с Двача!", "Ну держи!",
								"Не баян (баян)", "Каеф", "&#127770;");
						VK.sendMessage(uid, answer, Utils.getAttachMedia(response));
					}
			}
			else
				if(Utils.checkMessage("мемы")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-87960594, 1985));
					JSONObject response = (JSONObject) messages.get("response");
						if(response != null) {
							String answer = Utils.getRandomMessage("Мемосы!", "Мемы поданы!", "&#127770;");
							VK.sendMessage(uid, answer, Utils.getAttachMedia(response));
						}
				}
			else
				if(Utils.checkMessage("шк")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-83971955, 458));
					JSONObject response = (JSONObject) messages.get("response");
						if(response != null) {
							String answer = Utils.getRandomMessage("Я тут выбрал, посмотри", "Вот, держи!",
									"Баллов на " + random.nextInt(10) + " из 10", "А разве за ЦП не банят?",
									"&#127770;");
							if(Utils.getAttachMedia(response) != null)
								VK.sendMessage(uid, answer, Utils.getAttachMedia(response));
							else
								VK.sendMessage(uid, "Сегодня без школьниц, приятель!", null);
						}
				}
			else
				if(Utils.checkMessage("сиськи")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-73401695, 800));
					JSONObject response = (JSONObject) messages.get("response");
						if(response != null) {
							String answer = Utils.getRandomMessage("Ну держи!", "Сиськи поданы!", "&#127770;");
							VK.sendMessage(uid, answer, Utils.getAttachMedia(response));
						}
				}
			else
				if(Utils.checkMessage("фм")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-35140461, 1100));
					JSONObject response = (JSONObject) messages.get("response");
						if(response != null) {
							String answer = Utils.getRandomMessage("Я сам смотреть не буду, но вы смотрите",
									"Ну держи!", "Лол, что это вообще?");
							VK.sendMessage(uid, answer, Utils.getAttachMedia(response));
						}
				}
			else
				if(Utils.checkMessage("шкуры")) {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-83971955, 458));
					JSONObject response = (JSONObject) messages.get("response");
						if(response != null) {
							String answer = Utils.getRandomMessage("Как вам эта?", "Вроде ничего так...",
									"Баллов на " + random.nextInt(10) + " из 10", "Вот, держи!");
							VK.sendMessage(uid, answer, Utils.getAttachMedia(response));
						}
				}
		}
	}
	
}
