package blayzer.vkbot.modules;

import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import blayzer.vkbot.utils.Utils;
import blayzer.vkbot.utils.VK;

public class Sites {
	
	static String[] message; 
    
    public static void Init(String uid, String[] lastMessage) throws IOException, ParseException {
    	Random random = new Random();
		message = lastMessage;
		
		if(Utils.checkMessage("пошути")) {
			String url = "http://bash.im/byrating/" + random.nextInt(50);
	        Document bashDoc = Jsoup.connect(url).get();
	        Elements elements = bashDoc.select(".text");

	        String randomQuote = elements
	                .get(random.nextInt(elements.size()))
	                .html()
	                .replace("<br>", "");
	        
			VK.sendMessage(uid, randomQuote, null);
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

						VK.sendMessage(uid, "1 Доллар = " + USD + "руб." + "\n1 Евро = " + EUR + "руб." +
								"\n40 евро = " + Float.valueOf(EUR)*40, null);

					}
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
							
							VK.sendMessage(uid, "HardTech: " + htOnline + "\nTechnoMagic: " + tmOnline +
									"\nMagicRPG: " + magicOnline + "\nОбщий онлайн: " + onlineAll +
									"\nДневной рекорд: " + recordDay + "\nОбщий рекорд: " + record, null);

						}
				}
    }

}
