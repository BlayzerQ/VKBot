package blayzer.vkbot.modules;

import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.VK;

public class Shedule {

	public static void Init() {
		Runnable watchFriends = () -> {
		 while(true) {
			try {
			JSONObject request = (JSONObject) new JSONParser().parse(VK.getRequests());
			JSONArray response = (JSONArray) request.get("response");
			if(response != null) {
				for(int i = 0; i < response.size(); i++) {
					Long item = (Long) response.get(i);
					VK.addFriends(item);
				}
			}
			
			TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException | ParseException e) {
				e.printStackTrace();
			}
		 }
		};
		
		Runnable keepOnline = () -> {
			 while(true) {
				try {
				VK.setOnline();
				TimeUnit.MINUTES.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }
			};
		
		new Thread(watchFriends).start();
		new Thread(keepOnline).start();
	}
	
}
