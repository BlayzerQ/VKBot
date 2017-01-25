package blayzer.vkbot.api;

import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class VK {   
    
	static String vkToken = "3986146576be07b2f6c0f0dbcde7520c8011fa3ab420a754e6a54175e79b5438c774108212474515993dc";
    static String vkApi= "https://api.vk.com";
    
    public static String getMessages() {

        try { 
        	String responce = Utils.readUrl(vkApi + "/method/messages.get?out=0&count=1&access_token="+vkToken);
            return (responce);
        } 
        catch (IOException e)
        {
            return ("{response: {\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения сообщений.\"}]}}");
        }  
    }
    
    public static void sendMessage (String uid, String message, String attachment) {
    	
		try {
			Random random = new Random();
			StringBuilder vkurl =  new StringBuilder();
			vkurl.append("/method/messages.send?" +
						uid +"&message=" + Utils.fixString(message) + "&random_id=" + random.nextInt()
						+ "&v=5.45&access_token=" + vkToken);
			if(attachment != null) {
				vkurl.append("&attachment=" + attachment);
			}
			
			Utils.connect(vkApi + vkurl);
	        System.out.println("Ответ: " + message + "\nВложение: " + attachment);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setAsRead (String uid) {
    	
		try {
			Utils.connect(vkApi + "/method/messages.markAsRead?peer_id=" +
					uid + "&access_token="+vkToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getName (String uid) {
    	
		try {
			String responce = Utils.readUrl(vkApi + "/method/users.get?user_ids=" +
					uid + "&access_token="+vkToken);
            
			JSONObject messages = (JSONObject) new JSONParser().parse(responce);
			JSONArray response = (JSONArray) messages.get("response");
			JSONObject json = (JSONObject) response.get(0);
			String name = json.get("first_name").toString();
			String last_name = json.get("last_name").toString();
            
            return (name + " " + last_name);
	        
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			
			return ("Неизвестный отправитель");
		}
    }
    
    public static void setOnline () {
    	
		try {
			Utils.connect(vkApi + "/method/account.setOnline?access_token="+vkToken);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getPosts(Integer groupID, Integer limit) {
        try { 
        	Random random = new Random();
			String responce = Utils.readUrl(vkApi + "/method/wall.get?owner_id=" + groupID + 
        			"&offset=" + random.nextInt(limit) + "&count=1&access_token="+vkToken);
            return (responce);
        } 
        catch (IOException e)
        {
            return ("{response: {\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения постов.\"}]}}");
        }  
    }
	
}
