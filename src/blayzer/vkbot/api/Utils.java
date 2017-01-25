package blayzer.vkbot.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import blayzer.vkbot.VKBot;

public class Utils {

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
	
	public static boolean checkMessage(String word) {
		if(VKBot.lastMessage.length >= 2) {
		//for(String world : words) {
			if(VKBot.lastMessage[1].equalsIgnoreCase(word)) {
				return true;
		//}
			} else
				return false;
		} return false;
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
