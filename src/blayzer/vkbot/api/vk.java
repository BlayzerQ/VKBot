package blayzer.vkbot.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class vk {   
    
	static String vkToken = "3986146576be07b2f6c0f0dbcde7520c8011fa3ab420a754e6a54175e79b5438c774108212474515993dc";
    static String vkApi= "https://api.vk.com";
    
    public static String getMessages() {

        try { 
        	URL vkGetURL = new URL((vkApi + "/method/messages.get?out=0&count=1&access_token="+vkToken));
            URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
            conVkGetURL.setConnectTimeout(2000);
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8"));
            String inputLine = in.readLine();
            in.close();
            //System.out.println(inputLine);
            return (inputLine);
        } 
        catch (IOException e)
        {
            return ("{response: {\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения сообщений.\"}]}}");
        }  
    }
    
    public static void sendMessage (String uid, String message, String attachment) {
    	
		try {
			Random random = new Random();
			URL vkGetURL;
			if(attachment != null) {
			vkGetURL = new URL((vkApi + "/method/messages.send?user_id=" +
					uid +"&message=" + fixString(message) + "&attachment=" + attachment + "&random_id=" + random.nextInt()
					+ "&v=5.45&access_token=" + vkToken));
			} else {
				vkGetURL = new URL((vkApi + "/method/messages.send?user_id=" +
						uid +"&message=" + fixString(message) + "&random_id=" + random.nextInt()
						+ "&v=5.45&access_token=" + vkToken));
			}
	        URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
	        conVkGetURL.setConnectTimeout(2000);
	        
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8"));
	        System.out.println(in.readLine());
            in.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setAsRead (String uid) {
    	
		try {
			URL vkGetURL = new URL((vkApi + "/method/messages.markAsRead?peer_id=" +
					uid + "&access_token="+vkToken));
	        URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
	        conVkGetURL.setConnectTimeout(2000);
	        
	        InputStreamReader in = new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8");
            in.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getName (String uid) {
    	
		try {
			URL vkGetURL = new URL((vkApi + "/method/users.get?user_ids=" +
					uid + "&access_token="+vkToken));
	        URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
	        conVkGetURL.setConnectTimeout(2000);
	        
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8"));
            String inputLine = in.readLine();
            in.close();
            System.out.println(inputLine);
            
			JSONObject messages = (JSONObject) new JSONParser().parse(inputLine);
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
			URL vkGetURL = new URL((vkApi + "/method/account.setOnline?access_token="+vkToken));
	        URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
	        conVkGetURL.setConnectTimeout(2000);
	        
	        InputStreamReader in = new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8");
            in.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static String getPosts(Integer groupID, Integer limit) {
    	Random random = new Random();
        try { 
        	URL vkGetURL = new URL((vkApi + "/method/wall.get?owner_id=" + groupID + 
        			"&offset=" + random.nextInt(limit) + "&count=1&access_token="+vkToken));
            URLConnection conVkGetURL = (URLConnection) vkGetURL.openConnection();
            conVkGetURL.setConnectTimeout(2000);
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conVkGetURL.getInputStream(), "UTF-8"));
            String inputLine = in.readLine();
            in.close();
            //System.out.println(inputLine);
            return (inputLine);
        } 
        catch (IOException e)
        {
            return ("{response: {\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения постов.\"}]}}");
        }  
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
