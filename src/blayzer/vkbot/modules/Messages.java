package blayzer.vkbot.modules;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.vk;

public class Messages {
	
	static String[] message; 
	
	public static void Init(String uid, String[] lastMessage) throws ParseException {
		Random random = new Random();
		message = lastMessage;
		
		if(lastMessage.length >= 1) {
			if(checkMessage("привет") || checkMessage("здарова")) {
				String[] answers = {"Готов служить!", "Здарова, тварына!", "Слава Украине!",
						"Здарова, привет, привет, здарова!", "Привет, молодой!"}; 
				vk.sendMessage(uid, answers[random.nextInt(3)], null);
			}
			else
				if(checkMessage("луна"))
					vk.sendMessage(uid, "&#127770;", null);
			else
				if(checkMessage("команды"))
						vk.sendMessage(uid, "Список доступных команд: \n привет, луна, команды, шар", null);
			else
				if(checkMessage("время")) {
					Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
					Date curDate = calendar.getTime();
						vk.sendMessage(uid, curDate.toString(), null);
				}
			else
				if(checkMessage("шар") || checkMessage("скажи")){
					String[] answers = {"Да", "Конечно", "Не думаю",
							"Нет", "Знаки говорят - да", "Не сомненно!",
							"Скорее да, чем нет", "Не могу решить",
							"Мой ответ - нет", "Да, но только если ты не смотришь аниме"}; 
					vk.sendMessage(uid, answers[random.nextInt(9)], null);
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