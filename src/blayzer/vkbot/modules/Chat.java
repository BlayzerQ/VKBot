package blayzer.vkbot.modules;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import blayzer.vkbot.VKBot;
import blayzer.vkbot.utils.IIIAPI;
import blayzer.vkbot.utils.Utils;
import blayzer.vkbot.utils.VK;

public class Chat {

	public static void Init(String uid, String[] lastMessage) throws ParseException, IOException {
		if(Utils.checkMessage("чат")) {
			if(VKBot.lastMessage.length >= 2) {
				StringBuilder answer = new StringBuilder();
				for(int i=1; i < VKBot.lastMessage.length; i++) {
					answer.append(VKBot.lastMessage[i] + " ");
				}
				try {
					String session = VKBot.IIIsessions.get(uid);
					if(session == null) {
						session = IIIAPI.getSession(uid);
						if (session.equals("311c4035-4927-400f-81ea-03b40d30ce05")) {
							VKBot.IIIsessions.put("null", session);
                        } else {
                        	VKBot.IIIsessions.put(uid, session);
                        }
					}
					String iii_answer = IIIAPI.getAnswer(session, answer.toString());
					if(VKBot.chatVoice)
						VK.sendMessage(uid, iii_answer, VK.uploadDoc(iii_answer));
					else
						VK.sendMessage(uid, iii_answer, null);
				} catch (Exception e) {
					VK.sendMessage(uid, "Я не могу сейчас говорить", null);
					e.printStackTrace();
				}
			} else
				VK.sendMessage(uid, "Что?", null);
		}
		if(Utils.checkChatMessage("лайкните", "пролайкате", "поставьте лайк", "лайкни", "лайк")) {
			VK.setAsRead(uid);
			VK.sendMessage(uid, Utils.getRandomMessage("Мамку я твою лайкал", "Сам себя лайкай, пес", "Очко твое лайкнуть только могу", "Иди нахуй отсюда, лайкодрочер"), null);
		}
		else
		if(Utils.checkChatMessage("🌚"))
			VK.sendMessage(uid, "&#127770;", null);
	}
}
