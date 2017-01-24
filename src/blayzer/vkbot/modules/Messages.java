package blayzer.vkbot.modules;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import blayzer.vkbot.api.vk;

public class Messages {
	
	static String[] message; 
	
	public static void Init(String uid, String[] lastMessage) throws ParseException {
		Random random = new Random();
		message = lastMessage;
		
		if(lastMessage.length >= 1) {
			if(checkMessage("������") || checkMessage("�������")) {
				String[] answers = {"����� �������!", "�������, �������!", "����� �������!",
						"�������, ������, ������, �������!", "������, �������!"}; 
				vk.sendMessage(uid, answers[random.nextInt(3)], null);
			}
			else
				if(checkMessage("����"))
					vk.sendMessage(uid, "&#127770;", null);
			else
				if(checkMessage("�������"))
						vk.sendMessage(uid, "������ ��������� ������: \n ������, ����, �������, ���", null);
			else
				if(checkMessage("�����")) {
					Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Moscow"));
					Date curDate = calendar.getTime();
						vk.sendMessage(uid, curDate.toString(), null);
				}
			else
				if(checkMessage("���") || checkMessage("�����")){
					String[] answers = {"��", "�������", "�� �����",
							"���", "����� ������� - ��", "�� ��������!",
							"������ ��, ��� ���", "�� ���� ������",
							"��� ����� - ���", "��, �� ������ ���� �� �� �������� �����"}; 
					vk.sendMessage(uid, answers[random.nextInt(9)], null);
				}
		}
	}
	
	public static boolean checkMessage(String word) {
		//for(String world : words) {
			if(message.length > 0 && message[1].equalsIgnoreCase(word))
				return true;
		//}
			else
				return false;
	}
}