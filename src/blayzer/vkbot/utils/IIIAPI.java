package blayzer.vkbot.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.VKBot;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IIIAPI {

    // Do not change this - its default III.ru key
    static String IiiKey = "some very-very long string without any non-latin characters due to different string representations inside of variable programming languages";

    public static String getSession (String vkID) throws IOException, ParseException, InterruptedException {
        String decrypt;
        String iiiSession = "311c4035-4927-400f-81ea-03b40d30ce05";
        String uri = "http://iii.ru/api/2.0/json/Chat.init/"
                +VKBot.iiiBotId
                +"/"
                +vkID;
        String getUID = Utils.readUrl(uri);

        decrypt = Utils.XORdecrypt(getUID, IiiKey);
        decrypt = new String(DatatypeConverter.parseBase64Binary(decrypt));
        if (!(decrypt.equals(""))) {
        	JSONObject decodedJson = (JSONObject) new JSONParser().parse(decrypt);
        	System.out.println(decodedJson.toString());
            iiiSession = ((JSONObject) decodedJson
                    .get("result"))
                    .get("cuid")
                    .toString();
        }
        return iiiSession;
    }

    public static String getAnswer (String session, String body) throws Exception {
        body = body.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        body = body.replaceAll("\\+", "%2B");
        body = URLDecoder.decode(body, "UTF-8");
        String encrypt;
        String decrypt;

        String whattosend = "[\""+session+"\",\""+body+"\"]";
        String encoded = DatatypeConverter.printBase64Binary(whattosend.getBytes());
        encrypt = Utils.XORencrypt(encoded, IiiKey);

        String answer = Utils.postRequest("http://iii.ru/api/2.0/json/Chat.request", encrypt);

        if (!(answer.equals(""))) {
            decrypt = Utils.XORdecrypt(answer, IiiKey);
            decrypt = new String(DatatypeConverter.parseBase64Binary(decrypt));

            JSONObject decodedJson = (JSONObject) new JSONParser().parse(decrypt);

            String iiiAnswer = ((JSONObject) ((JSONObject) decodedJson
                    .get("result"))
                    .get("text"))
                    .get("value")
                    .toString();

            Pattern pattern1 = Pattern.compile("("
                    + "iii|"
                    + "проект|"
                    + "нас таких много|"
                    + "на сайте"
                    + ").*?(\\.|,| |-|!)");
            Matcher matcher1 = pattern1.matcher(iiiAnswer);
            String result1 = matcher1.replaceAll("$2");

            Pattern pattern2 = Pattern.compile("(" +
                    "парен|" +
                    "парня|" +
                    "инф|" +
                    "не челове|" +
                    "бот|" +
                    "viu\\-piu|" +
                    "виу-пиу|" +
                    "робот|" +
                    "виртуал.*? собесед|" +
                    "искусств.*? интелле|" +
                    "машина" +
                    ").*?(\\.|,| |-|\\!)", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher matcher2 = pattern2.matcher(result1);
            ArrayList<String> states = new ArrayList<String>();
            states.add("малышка$2");
            states.add("кукла$2");
            states.add("я$2");
            states.add("мне$2");
            states.add("взрослая$2");
            states.add("крошка$2");
            states.add("красотка$2");
            states.add("девочка$2");
            states.add("девушка$2");
            states.add("загадка$2");
            states.add("девочка загадка$2");
            states.add("секретик$2");
            states.add("скромница$2");
            states.add("маленькая девочка$2");
            states.add("лапочка$2");
            Collections.shuffle(states);

            answer = matcher2.replaceAll(states.get(0));
        }

        return answer;
    }




}
