package blayzer.vkbot.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
//import java.util.logging.FileHandler;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import blayzer.vkbot.VKBot;

public class Utils {
	
	public static Logger log = Logger.getLogger(VKBot.class.getName());
	public static void logging(Level level, String message) {
		InputStream in = VKBot.class.getResourceAsStream("log4j.properties");
		PropertyConfigurator.configure(in);
        log.log(level, message);
	}

	public static String readUrl(String url) throws IOException {
		
    	URL uri = new URL(url);
        URLConnection conURl = (URLConnection) uri.openConnection();
        conURl.setConnectTimeout(2000);
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conURl.getInputStream(), "UTF-8"));
        String inputLine = in.readLine();
        in.close();
        
        if(VKBot.debug)
		logging(Level.INFO, inputLine);
        
		return inputLine;
		
	}
	
	public static void connect(String url) throws IOException {
		
		URL uri = new URL(url);
        URLConnection conURl = (URLConnection) uri.openConnection();
        conURl.setConnectTimeout(2000);
        
        InputStreamReader in = new InputStreamReader(conURl.getInputStream(), "UTF-8");
        
        if(VKBot.debug)
		logging(Level.INFO, new BufferedReader(in).toString());
        
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
	
	public static boolean checkChatMessage(String... words) {
		if(VKBot.lastMessage.length >= 1) {
		for(String word : words) {
			if(VKBot.lastMessage[0].equalsIgnoreCase(word)) {
				return true;
			}
		}
		} return false;
	}
	
	public static String getAttachMedia(JSONObject response) {
		try {
			JSONArray items = (JSONArray) response.get("items");
			JSONObject json = (JSONObject) items.get(0);
			JSONArray att = (JSONArray) json.get("attachments");
			JSONObject photo = (JSONObject) att.get(0);
			JSONObject media = (JSONObject) photo.get("photo");
			String owner_id = media.get("owner_id").toString();
			String type = photo.get("type").toString();
			String att_id = media.get("id").toString();
			String key = media.get("access_key").toString();
			String attachment = type + owner_id + "_" + att_id + "_" + key;
			return attachment;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static String getRandomMessage(String... words) {
		Random random = new Random();
		return words[random.nextInt(words.length)];
	}
	
//    public static void speech(String text) throws IOException {
//        String uri = "http://tts.voicetech.yandex.net/generate?text="
//                + URLEncoder.encode(text, "utf-8")
//                + "&format=mp3"
//                + "&lang=ru-RU"
//                + "&speaker=oksana"
//                + "&key=f540e9a9-9641-446c-a0bc-916ce0ecb9ea";
//        
//        URL url = new URL(uri);
//        HttpURLConnection conn = (HttpURLConnection)(url.openConnection());
//        conn.setRequestMethod("GET");
//        
//        try (InputStream inp = conn.getInputStream()) {
//        	
//			if (!data.exists()) {
//				data.mkdir();
//			}
//            try (OutputStream f = new FileOutputStream(data.getAbsolutePath() + "/" + text + ".mp3")) {
//                byte[] buf = new byte[65536];
//                int len;
//                while ((len = inp.read(buf)) >= 0) {
//                    f.write(buf, 0, len);
//                }
//            }
//        }
//    }
    
    public static byte[] speech(String text) throws IOException {
        String uri = "http://tts.voicetech.yandex.net/generate?text="
                + URLEncoder.encode(text, "utf-8")
                + "&format=mp3"
                + "&lang=ru-RU"
                + "&speaker=oksana"
                + "&key=f540e9a9-9641-446c-a0bc-916ce0ecb9ea";
        
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection)(url.openConnection());
        conn.setRequestMethod("GET");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream input = conn.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) >= 0) {
            baos.write(buffer, 0, bytesRead);
        }
        
        return baos.toByteArray();
    }
	
    public static String postRequest(String requestUrl, String body) {
        String answer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");
            con.setRequestProperty("Accept-Language", "UTF-8");

            con.setDoOutput(true);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
            outputStreamWriter.write(body);
            outputStreamWriter.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            answer = response.toString();
        }catch(IOException e){
            e.printStackTrace();
        }

        return answer;
    }
    
    public static String XORencrypt(String message, String key) {

        int ml = message.length();
        int kl = key.length();
        String newmsg = "";

        for (int i=0; i < ml; i++){
            newmsg += ((char)(message.charAt(i) ^ key.charAt(i % kl)));

        }

        String encoded = DatatypeConverter.printBase64Binary(newmsg.getBytes());

        return encoded;
    }

    public static String XORdecrypt(String encryptedMessage, String key) {
        String decoded = new String(DatatypeConverter.parseBase64Binary(encryptedMessage));
        String msg = decoded;
        int ml = msg.length();
        int kl = key.length();
        String newmsg = "";

        for (int i=0; i < ml; i++){

            newmsg += ((char)(msg.charAt(i) ^ key.charAt(i % kl)));
        }

        return newmsg;
    }
    
    public static String getToken() {
    	String token = VKBot.vkTokens[0];
    	
    	if(VKBot.tokenid < VKBot.vkTokens.length) {
    		token = VKBot.vkTokens[VKBot.tokenid];
    		VKBot.tokenid++;
    		return token;
    	} else {
    		VKBot.tokenid = 0;
    		return token;
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
