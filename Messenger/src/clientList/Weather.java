//package clientList;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//public class Weather {
//
//	public static StringBuilder temp = new StringBuilder();
//
//	public static void main(String[] args) throws IOException, ParseException {
//		getInfo();
//	}
//
//	public static String getInfo() throws IOException, ParseException {
//		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
//		SimpleDateFormat hour = new SimpleDateFormat("HH00");
//		Date time = new Date();
//		String nx = "36"; //위도
//		String ny = "128"; //경도
////		String baseDate = date.format(time); //조회하고싶은 날짜
////		String baseTime = hour.format(time); //조회하고싶은 시간
//		String baseDate = "20201216";
//		String baseTime = "0500";
//		String type = "JSON"; //타입 xml, json 등등 ..
//		String serviceKey = "Pe7chXCUPk%2FBnVerISrk2EuEfXgkQH2%2BsaLQCirYWac8AS8rMSH9pccLM%2Bd%2BeaHPr%2F6OTWa07pSZQhWFZDerag%3D%3D"; // 서비스키
//		String URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"; // URL
//
//		StringBuilder urlBuilder = new StringBuilder(URL); /*URL*/
//		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey); /*Service Key*/
//		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
//		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
//		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*20년 11월 24일 발표*/
//		urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*05시 발표(정시단위)*/
//		urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
//		urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점 Y 좌표*/
//		URL url = new URL(urlBuilder.toString());
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Content-type", "application/json");
////		System.out.println("Response code: " + conn.getResponseCode());
//		BufferedReader rd;
//		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		} else {
//			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//		}
//		StringBuilder sb = new StringBuilder();
//		String line;
//		while ((line = rd.readLine()) != null) {
//			sb.append(line);
//		}
//		rd.close();
//		conn.disconnect();
//		String result=sb.toString();
////		System.out.println(sb.toString());
//
//
////		StringBuilder temp = new StringBuilder();
//
//		JSONParser parser = new JSONParser();
//		JSONObject obj = (JSONObject) parser.parse(result);
//
//		// first_parse로 파싱
//		JSONObject first_parse = (JSONObject) obj.get("response");
//
//		// first_parse에서 second_parse로 가져오기
//		JSONObject second_parse = (JSONObject) first_parse.get("body");
//
//		// second_parse에서 items로 가져오기
//		JSONObject parse_items = (JSONObject) second_parse.get("items");
//
//		// items 에서 parse_Array로 가져오기
//		JSONArray parse_Array = (JSONArray) parse_items.get("item");
//
//		// String 형식으로 다시 가져오기
//		String contents;
//
//		// 모든정보를 weather라는 JSONObject로 가져옴
//		JSONObject weather;
//
//		sb.append(baseDate+"/"+ baseTime);
//
//		temp.append("날짜 : " + baseDate);
//		temp.append(" ");
//		temp.append("시간 : " + baseTime);
//		temp.append("\n");
//
//		for(int i = 0 ; i < parse_Array.size(); i++) {
//			weather = (JSONObject) parse_Array.get(i);
//			Object fcstValue = weather.get("fcstValue");
//			Object fcstDate = weather.get("fcstDate");
//			Object fcstTime = weather.get("fcstTime");
//			contents = (String)weather.get("category");
//
//
//			// 카테고리별로 파싱한 값을 넣어준다
//			if(contents.equals("POP"))
//				temp.append("강수확률 : " + fcstValue + "%");
//			else if(contents.equals("PTY"))
//				temp.append(", 강수형태 : " + fcstValue);
//			else if(contents.equals("SKY"))
//				temp.append(", 하늘상태 : " + fcstValue);
//			else if(contents.equals("WSD"))
//				temp.append(", 풍속  : " + fcstValue);
//			else if(contents.equals("R06"))
//				temp.append(", 6시간 강수량 : " + fcstValue + "mm");
//			else if(contents.equals("REH"))
//				temp.append(", 습도 : " + fcstValue + "%");
//			else if(contents.equals("S06"))
//				temp.append(", 6시간 적설량 : " + fcstValue + "cm");
//			else if(contents.equals("WAV"))
//				temp.append(", 파고 : " + fcstValue + "M");
//			else if(contents.equals("T3H") || contents.equals("TMN") || contents.equals("TMX")) {
//				temp.append(", 3시간 기온 : " + fcstValue + "℃");
//				temp.append("\n");
//			}
//			else if(contents.equals("UUU") || contents.equals("VVV"))
//				temp.append(", 풍속 : " + fcstValue + "m/s");
//			else if(contents.equals("VEC"))
//				temp.append(", 풍향 : " + fcstValue + "m/s");
//			else
//				temp.append(", " + fcstValue);
//		}
//
//		System.out.println(temp.toString());
//
//
//		return temp.toString();
//	}
//}