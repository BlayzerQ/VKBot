package blayzer.vkbot.modules;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.Utils;
import blayzer.vkbot.api.VK;

public class Chat {

	public static void Init(String uid, String[] lastMessage) throws ParseException, IOException {
		if(Utils.checkChatMessage("лайкните", "пролайкате", "поставьте лайк", "лайкни", "лайк")) {
			VK.setAsRead(uid);
			VK.sendMessage(uid, Utils.getRandomMessage("Мамку я твою лайкал", "Сам себя лайкай, пес", "Очко твое лайкнуть только могу", "Иди нахуй отсюда, лайкодрочер"), null);
		}
		else
		if(Utils.checkChatMessage("привет", "здарова", "кулити", "ку")) {
			String answer = Utils.getRandomMessage("Готов служить!", "Здарова, тварына!", "Слава Украине!",
					"Здарова, привет, привет, здарова!", "Привет, молодой!"); 
			VK.sendMessage(uid, answer, null);
		}
		else
			if(Utils.checkMessage("🌚"))
				VK.sendMessage(uid, "&#127770;", null);
//		else
//		if(Utils.checkChatMessage("как дела", "как настроение", "как ты")) {
//			String answer = Utils.getRandomMessage("Все отлично! Сам как?", "Все прекрасно!", "В порядке",
//					"Мне всегда хорошо. Я же бот.", "Получше некоторых"); 
//			VK.sendMessage(uid, answer, null);
//		}
//		else
//		if(Utils.checkChatMessage("что делаешь", "чем занимаешься")) {
//			String answer = Utils.getRandomMessage("Читаю сообщения от всяких придурков", "Читаю", "В ВК залипаю",
//					"Ничего", "Мемы смотрю"); 
//			VK.sendMessage(uid, answer, null);
//		}
//		else
//		if(Utils.checkChatMessage("хорошо", "окей", "ладно", "пусть", "принято", "как скажешь")) {
//			String answer = Utils.getRandomMessage("Вот и отлично!", "Здорово!", "Вот и договорились",
//					"Великолепно!", "Рад, что ты понял. Тупой дибил."); 
//			VK.sendMessage(uid, answer, null);
//		}
//		else {
//			String answer = Utils.getRandomMessage("Я не буду отвечать", "Я ничего не знаю об этом", "Заткнись",
//					"Давай поговорим о другом?", "Я не знаю что тебе ответить", "Охуел что ли, такое писать?", "Ты в своем уме?"); 
//			VK.sendMessage(uid, answer, null);
//		}
	}
}
