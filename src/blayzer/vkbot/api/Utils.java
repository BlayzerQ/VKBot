package blayzer.vkbot.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import blayzer.vkbot.VKBot;

public class Utils {
	
	public static Logger log = Logger.getLogger("logger");
	
	static {
		try {
			log.addHandler(new FileHandler("VKBot.log"));
			log.setUseParentHandlers(false);
		} catch (Exception e) {
			System.err.println("Could not return a static logger");
		}
	}
	
	public static Logger getLogger() {
		return log;
	}

	public static String readUrl(String url) throws IOException {
		
    	URL fm = new URL((url));
        URLConnection conVkGetURL = (URLConnection) fm.openConnection();
        conVkGetURL.setConnectTimeout(2000);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8"));
        String inputLine = in.readLine();
        in.close();
		
		return inputLine;
		
	}
	
	public static void connect(String url) throws IOException {
		
		URL vkGetURL = new URL((url));
        URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
        conVkGetURL.setConnectTimeout(2000);
        
        InputStreamReader in = new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8");
        in.close();
	}
	
	public static boolean checkMessage(String... words) {
		if(VKBot.lastMessage.length >= 2) {
		for(String word : words) {
			if(VKBot.lastMessage[1].equalsIgnoreCase(word)) {
				return true;
			}
		}
		} return false;
	}
	
	public static String getAttachMedia(JSONObject response) {
		JSONArray items = (JSONArray) response.get("items");
		System.out.println(items);
		JSONObject json = (JSONObject) items.get(0);
		JSONArray att = (JSONArray) json.get("attachments");
		if(att != null) {
		JSONObject photo = (JSONObject) att.get(0);
		JSONObject media = (JSONObject) photo.get("photo");
		String owner_id = media.get("owner_id").toString();
		String type = photo.get("type").toString();
		String att_id = media.get("id").toString();
		String key = media.get("access_key").toString();
		String attachment = type + owner_id + "_" + att_id + "_" + key;
		
		return attachment;
		}
		return null;
	}
	
	public static String getRandomMessage(String... words) {
		Random random = new Random();
		return words[random.nextInt(words.length)];
	}
	
    public static String fixString(String component) {
        String result = null;

        try {
            result = URLEncoder.encode(component, "UTF-8")
            .replaceAll("\\%28", "(")
            .replaceAll("\\%29", ")")
            .replaceAll("\\+", "%20")
            .replaceAll("\\%27", "'")
            .replaceAll("\\%21", "!")
            .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = component;
        }

        return result;
    }
	
}
