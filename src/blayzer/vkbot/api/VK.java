package blayzer.vkbot.api;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.VKBot;

public class VK {
    static String vkApi= "https://api.vk.com";
    
    public static String getMessages() {

        try {
        	String response = Utils.readUrl(vkApi + "/method/messages.get?out=0&count=20&access_token="+VKBot.vkToken+"&v=5.62");
            return response;
        } 
        catch (IOException e)
        {
            return "{response: [{\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения сообщений.\"}]}}";
        }  
    }
    
    public static void sendMessage (String uid, String message, String attachment) {
    	
		try {
			Random random = new Random();
			StringBuilder vkurl =  new StringBuilder();
			vkurl.append("/method/messages.send?" +
						uid +"&message=" + Utils.fixString(message) + "&random_id=" + random.nextInt()
						+ "&v=5.45&access_token="+VKBot.vkToken+"&v=5.62");
			if(attachment != null) {
				vkurl.append("&attachment=" + attachment);
			}
			
			Utils.connect(vkApi + vkurl);
			Utils.logging(Level.INFO, "Ответ: " + message + " Вложение: " + attachment);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setAsRead (String uid) {
    	
		try {
			if(uid.contains("chat_id")){
			Utils.connect(vkApi + "/method/messages.markAsRead?peer_id=" +
					uid.replace("chat_id=", "2000000000") + "&access_token="+VKBot.vkToken+"&v=5.62");
			} else {
			Utils.connect(vkApi + "/method/messages.markAsRead?peer_id=" +
					uid.replace("user_id=", "") + "&access_token="+VKBot.vkToken+"&v=5.62");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getName (String uid) {
    	
		try {
			String responce = Utils.readUrl(vkApi + "/method/users.get?" +
					uid + "&access_token="+VKBot.vkToken+"&v=5.62");
            
			JSONObject messages = (JSONObject) new JSONParser().parse(responce);
			JSONArray response = (JSONArray) messages.get("response");
			JSONObject json = (JSONObject) response.get(0);
			String name = json.get("first_name").toString();
			String last_name = json.get("last_name").toString();
            
            return name + " " + last_name;
	        
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			
			return "Неизвестный отправитель";
		}
    }
    
    public static void setOnline () {
    	
		try {
			Utils.connect(vkApi + "/method/account.setOnline?access_token="+VKBot.vkToken+"&v=5.62");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getPosts(Integer groupID, Integer limit) {
        try { 
        	Random random = new Random();
			String responce = Utils.readUrl(vkApi + "/method/wall.get?owner_id=" + groupID + 
        			"&offset=" + random.nextInt(limit) + "&count=1&access_token="+VKBot.vkToken+"&v=5.62");
            return responce;
        } 
        catch (IOException e)
        {
            return "{response: [{\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения постов.\"}]}}";
        }  
    }
    
    public static String searchVideo(String request) {
        try { 
			String responce = Utils.readUrl(vkApi + "/method/video.search?q=" + request + 
        			"&sort=2&adult=0&access_token="+VKBot.vkToken+"&v=5.62");
            return responce;
        } 
        catch (IOException e)
        {
            return "{response: [{\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения видео.\"}]}}";
        }  
    }
    
    public static String getRequests() {
		try {
			String responce = Utils.readUrl(vkApi + "/method/friends.getRequests?&access_token="+VKBot.vkToken+"&v=5.62");
	    	return responce;
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{response:[]}";
    }
    
    public static void addFriends(Long uid) {
		try {
			Utils.connect(vkApi + "/method/friends.add?user_id="+ uid +"&access_token="+VKBot.vkToken+"&v=5.62");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
