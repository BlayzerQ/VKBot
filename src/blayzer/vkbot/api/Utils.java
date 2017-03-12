package blayzer.vkbot.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import blayzer.vkbot.VKBot;

public class Utils {
	
	public static Logger log = Logger.getLogger(VKBot.class.getName());
	public static File data = new File("data");
	
	public static void logging(Level level, String message) {
		try {
			File file = new File("logs");
			if (!file.exists()) {
			    file.mkdir();
			}
			FileHandler logfile = new FileHandler("logs/VKBot.log", 10240, 5, true);
			logfile.setFormatter(new SimpleFormatter());
			log.addHandler(logfile);
			log.log(level, message);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
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
		JSONArray items = (JSONArray) response.get("items");
		System.out.println(items);
		JSONObject json = (JSONObject) items.get(0);
		JSONArray att = (JSONArray) json.get("attachments");
		if(att != null) {
		JSONObject photo = (JSONObject) att.get(0);
		JSONObject media = (JSONObject) photo.get("photo");
		if(media == null) {
			return null;
		}
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
	
    public static void speech(String text) throws IOException {
        String uri = "http://tts.voicetech.yandex.net/generate?text="
                + URLEncoder.encode(text, "utf-8")
                + "&format=mp3"
                + "&lang=ru-RU"
                + "&speaker=oksana"
                + "&key=f540e9a9-9641-446c-a0bc-916ce0ecb9ea";
        
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection)(url.openConnection());
        conn.setRequestMethod("GET");
        
        try (InputStream inp = conn.getInputStream()) {
        	
			if (!data.exists()) {
				data.mkdir();
			}
            try (OutputStream f = new FileOutputStream(data.getAbsolutePath() + "/" + text + ".mp3")) {
                byte[] buf = new byte[65536];
                int len;
                while ((len = inp.read(buf)) >= 0) {
                    f.write(buf, 0, len);
                }
            }
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
