package blayzer.vkbot.modules;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import blayzer.vkbot.api.Utils;
import blayzer.vkbot.api.VK;

public class Sites {
	
	static String[] message; 
    
    public static void Init(String uid, String[] lastMessage) throws IOException {
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
    }

}
