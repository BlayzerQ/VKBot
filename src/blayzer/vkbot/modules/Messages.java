package blayzer.vkbot.modules;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
		
			if(Utils.checkMessage("привет", "здарова")) {
				String answer = Utils.getRandomMessage("Готов служить!", "Здарова, тварына!", "Слава Украине!",
						"Здарова, привет, привет, здарова!", "Привет, молодой!"); 
				VK.sendMessage(uid, answer, null);
			}
			else
				if(Utils.checkMessage("луна", "🌚"))
					VK.sendMessage(uid, "&#127770;", null);
				else
					if(Utils.checkMessage("кто")) {
						String answer = Utils.getRandomMessage("Тот кто выше написал", "Мамка твоя", "Ты пидор",
								"Вот мудак", "Нахуй иди", "Никто", "Да вы все тут"); 
						VK.sendMessage(uid, answer, null);
					}
			else
				if(Utils.checkMessage("команды"))
						VK.sendMessage(uid, "Список доступных команд: \nпривет, \nлуна, \nкоманды,"
								+ "\nвремя, \nшар, \nдвач, \nмемы, \nшк, \nсиськи, \nфм, \nрасписание, \nнеделя"
								+ "\nонлайн, \nшкуры, \nкурс, \nнайди, \nнапиши, \nпошути", null);
			else
				if(Utils.checkMessage("время")) {
					Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
					Date curDate = calendar.getTime();
						VK.sendMessage(uid, curDate.toString(), null);
				}
			else
				if(Utils.checkMessage("неделя")) {
					    int day = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
					    if(day%2 == 0){
					    	VK.sendMessage(uid, "Верхняя неделя", null);
					    }else{
					    	VK.sendMessage(uid, "Нижняя неделя", null);
					    }
				}
			else
				if(Utils.checkMessage("шар", "скажи")){
					String answer = Utils.getRandomMessage("Да", "Конечно", "Не думаю",
							"Нет", "Знаки говорят - да", "Несомненно!",
							"Скорее да, чем нет", "Не могу решить",
							"Мой ответ - нет", "Да, но только если ты не смотришь аниме");
					VK.sendMessage(uid, answer, null);
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
				if(Utils.checkMessage("расписание")) {
				    if(lastMessage.length > 9.40-11.10) {
					if(lastMessage[2].equalsIgnoreCase("понедельник")){
						String result = "Верхняя неделя:\n 8.00-9.30) Окно\n 9.40-11.10) Окно\n 11.20-12.50) Математика\n 13.00-14.30) Окно\n 14.40-16.10) ИИКГ\n\n"
									  + "Нижняя неделя:\n 8.00-9.30) Окно\n 9.40-11.10) Окно\n 11.20-12.50) Математика\n 13.00-14.30) Химия\n 14.40-16.10) Окно";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("вторник")) {
						String result = "8.00-9.30) Окно\n 9.40-11.10) Физкультура\n 11.20-12.50) Окно\n 13.00-14.30) Окно\n 14.40-16.10) Окно";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("среда")) {
						String result = "Верхняя неделя:\n 8.00-9.30) Физика, пол группы\n 9.40-11.10) ИИКГ\n 11.20-12.50) ТехМех\n 13.00-14.30) ТехМех\n 14.40-16.10) ФИЗ\n 16.20-17.50) Материаловедение\n\n"
								  + "Нижняя неделя:\n 8.00-9.30) Окно\n 9.40-11.10) Физ\n 11.20-12.50) ТехМех\n 13.00-14.30) ТехМех\n 14.40-16.10) Окно\n 16.20-17.50) Материаловедение";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("четверг")) {
						String result = "8.00-9.30) ПП\n 9.40-11.10) Окно\n 11.20-12.50) Окно\n 13.00-14.30) Окно\n 14.40-16.10) Окно";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("пятница")) {
						String result = "Верхняя неделя:\n 8.00-9.30) Окно\n 9.40-11.10) Физкультура\n 11.20-12.50) Математика\n 13.00-14.30) Математика\n 14.40-16.10) Окно\n 16.20-17.50) Физика\n\n"
								  + "Нижняя неделя:\n 8.00-9.30) Окно\n 9.40-11.10) Физкультура\n 11.20-12.50) Окно\n 13.00-14.30) Математика\n 14.40-16.10) Химия\n 16.20-17.50) Физика";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("суббота")) {
						String result = "Нет пар";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("сегодня")) {
						String result = "";
						VK.sendMessage(uid, result, null);
					} else if(lastMessage[2].equalsIgnoreCase("завтра")) {
						String result = "";
						VK.sendMessage(uid, result, null);
					}
				    } else VK.sendMessage(uid, "На какой день?", null);
				}
			else
				if(Utils.checkMessage("найди")) {
					if(VKBot.lastMessage.length > 2) {
						JSONObject request = (JSONObject) new JSONParser().parse(VK.searchVideo(VKBot.lastMessage[2]));
						JSONArray response = (JSONArray) request.get("response");
							if(response != null) {
								System.out.println(response);
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
	}
}