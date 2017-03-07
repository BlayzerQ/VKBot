package blayzer.vkbot.modules;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.Utils;
import blayzer.vkbot.api.VK;

public class Shedule {

	public static void Init() {
		Runnable watchFriends = () -> {
		 while(true) {
			try {
			JSONObject request = (JSONObject) new JSONParser().parse(VK.getRequests());
			JSONObject response = (JSONObject) request.get("response");
			if(response != null) {
				JSONArray items = (JSONArray) response.get("items");
				for(int i = 0; i < items.size(); i++) {
					Long item = (Long) items.get(i);
					VK.addFriends(item);
				}
			}
			
			TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException | ParseException e) {
				Utils.logging(Level.SEVERE, e.getStackTrace().toString());
			}
		 }
		};
		
		Runnable keepOnline = () -> {
			 while(true) {
				try {
				VK.setOnline();
				TimeUnit.MINUTES.sleep(10);
				} catch (InterruptedException e) {
					Utils.logging(Level.SEVERE, e.getStackTrace().toString());
				}
			 }
			};
		
		new Thread(watchFriends).start();
		new Thread(keepOnline).start();
	}
	
}
