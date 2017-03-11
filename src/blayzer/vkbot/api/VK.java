package blayzer.vkbot.api;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.VKBot;

public class VK {
	
    private static String vkApi= "https://api.vk.com";
    private static Random random = new Random();
    
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
    
    public static String uploadDoc (String filename) {
    	File file = new File(Utils.data.getAbsolutePath() + "/" + filename + ".mp3");
    	try {
        	Utils.speech(filename);
			String server = Utils.readUrl(vkApi + "/method/docs.getUploadServer?access_token="+VKBot.vkToken+"&type=audio_message&v=5.62");
			JSONObject object = (JSONObject) new JSONParser().parse(server);
			JSONObject response = (JSONObject) object.get("response");
			String url = (String) response.get("upload_url");
			
			MultipartUtility multipart = new MultipartUtility(url, "UTF-8");
			multipart.addFilePart("file", file);
			
			List<String> multi = multipart.finish();
			String fileUpl = "";
            for (String line : multi) {
                JSONObject obj3 = (JSONObject) new JSONParser().parse(line.toString());
                fileUpl = (String) obj3.get("file");
            }
            String respsave = Utils.readUrl("https://api.vk.com/method/docs.save?file=" +fileUpl +"&access_token="+VKBot.vkToken+"&v=5.60");
            JSONObject json = (JSONObject) new JSONParser().parse(respsave);
            JSONArray endresponse = (JSONArray) json.get("response");
            if(endresponse != null) {
            JSONObject items = (JSONObject) endresponse.get(0);
            Long owner_id = (Long) items.get("owner_id");
            Long id = (Long) items.get("id");
            return "doc" + owner_id + "_" + id;
            }
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return "doc" + null + "_" + null;
    }
    
    public static String docSearch (String text) {
    	try {
			String request = Utils.readUrl(vkApi + "/method/docs.search?q="+text+"&count=10"+"&offset="+random.nextInt(300)+"&access_token="+VKBot.vkToken+"&v=5.60");
			JSONObject json = (JSONObject) new JSONParser().parse(request);
			JSONObject response = (JSONObject) json.get("response");
			if(response != null) {
			JSONArray items = (JSONArray) response.get("items");
			if(items.size() == 0) {
				return "Ничего не найдено";
			}
			JSONObject doc = (JSONObject) items.get(0);
			Long owner_id = (Long) doc.get("owner_id");
			Long id = (Long) doc.get("id");
			return "doc" + owner_id + "_" + id;
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    	return "doc" + null + "_" + null;
    	
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
