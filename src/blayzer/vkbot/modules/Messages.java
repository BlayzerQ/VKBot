package blayzer.vkbot.modules;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.VK;
import blayzer.vkbot.api.Utils;

public class Messages {
	
	public static void Init(String uid, String[] lastMessage) throws ParseException, IOException {
		Random random = new Random();
		
			if(Utils.checkMessage("привет") || Utils.checkMessage("здарова")) {
				String[] answers = {"Готов служить!", "Здарова, тварына!", "Слава Украине!",
						"Здарова, привет, привет, здарова!", "Привет, молодой!"}; 
				VK.sendMessage(uid, answers[random.nextInt(4)], null);
			}
			else
				if(Utils.checkMessage("луна"))
					VK.sendMessage(uid, "&#127770;", null);
			else
				if(Utils.checkMessage("команды"))
						VK.sendMessage(uid, "Список доступных команд: \n привет, луна, команды,"
								+ "время, шар, двач, мемы, шк, сиськи, фм, онлайн, шкуры, курс", null);
			else
				if(Utils.checkMessage("время")) {
					Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
					Date curDate = calendar.getTime();
						VK.sendMessage(uid, curDate.toString(), null);
				}
			else
				if(Utils.checkMessage("онлайн")) {
					
					String responce = Utils.readUrl("http://finemine.ru/mon/ajax.php");
		            
		            JSONObject onlineUrl = (JSONObject) new JSONParser().parse(responce);
					JSONObject servers = (JSONObject) onlineUrl.get("servers");
						if(servers != null) {
							JSONObject hardtech = (JSONObject) servers.get("HardTech");
							JSONObject technomagic = (JSONObject) servers.get("TechnoMagic");
							JSONObject magicrpg = (JSONObject) servers.get("MagicRPG");
				            String htOnline = hardtech.get("online").toString();
				            String tmOnline = technomagic.get("online").toString();
				            String magicOnline = magicrpg.get("online").toString();
							String onlineAll = onlineUrl.get("online").toString();
							String recordDay = onlineUrl.get("recordday").toString();
							String record = onlineUrl.get("record").toString();
				            
				            System.out.println(onlineUrl);
							
							VK.sendMessage(uid, "HardTech: " + htOnline + "\nTechnoMagic: " + tmOnline +
									"\nMagicRPG: " + magicOnline + "\nОбщий онлайн: " + onlineAll +
									"\nДневной рекорд: " + recordDay + "\nОбщий рекорд: " + record, null);

						}
				}
			else
				if(Utils.checkMessage("курс")) {
						
					String responceUSD = Utils.readUrl("http://api.fixer.io/latest?base=USD");
					String responceEUR = Utils.readUrl("http://api.fixer.io/latest?base=EUR");
			            
			           JSONObject kursUsdUrl = (JSONObject) new JSONParser().parse(responceUSD);
			           JSONObject kursEurUrl = (JSONObject) new JSONParser().parse(responceEUR);        
			           JSONObject ratesUsd = (JSONObject) kursUsdUrl.get("rates");
			           JSONObject ratesEur = (JSONObject) kursEurUrl.get("rates");
						if(ratesUsd != null && ratesEur != null) {
							String USD = ratesUsd.get("RUB").toString();
							String EUR = ratesEur.get("RUB").toString();

							VK.sendMessage(uid, "1 Доллар = " + USD + "\n1 Евро = " + EUR +
									"\n40 евро = " + Float.valueOf(EUR)*40, null);

						}
				}
			else
				if(Utils.checkMessage("шар") || Utils.checkMessage("скажи")){
					String[] answers = {"Да", "Конечно", "Не думаю",
							"Нет", "Знаки говорят - да", "Не сомненно!",
							"Скорее да, чем нет", "Не могу решить",
							"Мой ответ - нет", "Да, но только если ты не смотришь аниме"}; 
					VK.sendMessage(uid, answers[random.nextInt(9)], null);
				}
	}
}