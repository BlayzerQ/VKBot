package blayzer.vkbot.modules;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.Utils;
import blayzer.vkbot.api.VK;

public class Chat {

	public static void Init(String uid, String[] lastMessage) throws ParseException, IOException {
		if(Utils.checkChatMessage("лайкните", "пролайкате", "поставьте лайк", "лайкни", "лайк")) {
			VK.setAsRead(uid);
			VK.sendMessage(uid, Utils.getRandomMessage("Мамку я твою лайкал", "Сам себя лайкай, пес", "Очко твое лайкнуть только могу", "Иди нахуй отсюда, пес"), null);
		}
		else
		if(Utils.checkChatMessage("привет", "здарова", "кулити", "ку")) {
			String answer = Utils.getRandomMessage("Готов служить!", "Здарова, тварына!", "Слава Украине!",
					"Здарова, привет, привет, здарова!", "Привет, молодой!"); 
			VK.sendMessage(uid, answer, null);
		}
	}
}
