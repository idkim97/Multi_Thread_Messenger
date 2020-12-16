package client;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.*;

public class Weather
{
	public static String getWeather() throws IOException
	{
		/*예보 발표 날짜는 실행 전 날짜*/
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		/*23시 이후에 실행하면 전날 23시의 예보 데이터를 받아올 수 없음 ( API가 1일치만 제공 ) 따라서 당일 날짜를 이용*/
		if (calendar.get(Calendar.HOUR_OF_DAY) == 23)
		{
			calendar.add(calendar.DATE, 0);
		}
		else
		{
			calendar.add(calendar.DATE, -1);
		}
		
		String result = "";
		String serviceKey = "49f1u%2BNPwv1IknmkOA2tI%2FXbNJnkdlkVxoKNnBDGc6BfPSzwqW4e5Gl7BvK3edmhg9Q7SSH8o4vRIJAUv3WbgA%3D%3D"; // 인증키
		String dataType = "JSON"; // 요청자료형식
		String baseDate = dateFormat.format(calendar.getTime()); // 발표날짜 : 전날
		String baseTime = "2300"; // 발표시간 23시
		String nX = "63"; // 태평3동 격자X
		String nY = "124"; // 태평3동 격자Y
		
		/*프로그램 실행 전날 23시 기준 예보 데이터로, 프로그램 실행 당일 06시까지의 예보 결과를 얻기위한 요청변수들을 url로 encode함*/
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst");
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("21", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nX, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(nY, "UTF-8"));
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader rd;
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
        {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        else
        {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        
        StringBuilder sb = new StringBuilder();
        while ((result = rd.readLine()) != null)
        {
            sb.append(result);
        }
        
        rd.close();
        conn.disconnect();

        String we = doParse(sb.toString());
        return we;
	}
	
	public static String doParse(String dataString) 
    {
		JsonObject item;
		
		JsonParser jsonParser = new JsonParser(); // json parser 객체 생성
    	JsonObject jsonObject = (JsonObject) jsonParser.parse(dataString); // 데이터를 json 객체로 변환
    	JsonObject jsonResult = (JsonObject) jsonObject.get("response"); // response 키를 이용해 파싱
    	JsonObject jsonBody = (JsonObject) jsonResult.get("body"); // response에서 body 찾기
    	JsonObject jsonItems = (JsonObject) jsonBody.get("items"); // body에서 items 찾기
    	JsonArray jsonItem = (JsonArray) jsonItems.get("item"); // items에서 item 아래 값들을 배열로 저장
    	String result = "";
    	
    	for (int i = 0; i < jsonItem.size(); i++)
    	{
    		item = (JsonObject) jsonItem.get(i);
    		
    		String fcstTime = item.get("fcstTime").toString();
    		String category = item.get("category").toString();
    		String fcstValue = item.get("fcstValue").toString();
    		
    		/*06시 자료구분코드에 따라서 출력*/
    		if (fcstTime.equals("\"0600\""))
    		{
    			switch(category)
    			{
    			case "\"POP\"" :
    				result += "오늘 태평3동의 기상예보는";
    				result +=" 강수확률 " + fcstValue.substring(1, fcstValue.length()-1) + "% 이고, ";
    				break;
    			//case "\"PTY\"" :
    				//result += "강수형태 " + findPTY(fcstValue.substring(1, fcstValue.length()-1) + " ");
    				//break;
    			//case "\"SKY\"" :
    				//result += "하늘 " + findSKY(fcstValue.substring(1, fcstValue.length()-1)+ " ");
    				//break;
    			case "\"TMN\"" :
    				result += "아침 최저기온 " + fcstValue.substring(1, fcstValue.length()-1) + "도";
    				break;
    			//case "\"WSD\"" :
    				//result += "풍속 " + fcstValue.substring(1, fcstValue.length()-1) + "m/s";
    				//break;
    			default:
    				break;
    			}
    		}	
    	}
    	return result;
    }
	
	/*코드값으로 받아오는 강수형태를 해석*/
	public static String findPTY(String ptyCode)
	{
		String status = "";
		
		switch(ptyCode)
		{
			case "0" :
				status = "없음";
				break;
			case "1" :
				status = "비";
				break;
			case "2" :
				status = "진눈개비";
				break;
			case "3" :
				status = "눈";
				break;
			case "4" :
				status = "소나기";
				break;
			case "5" :
				status = "빗방울";
				break;
			case "6" :
				status = "빗방울/눈날림";
				break;
			case "7" :
				status = "눈날림";
				break;
			default :
				status = "알수없음";
				break;
		}
		return status;
	}
	
	/*코드값으로 받아오는 하늘상태를 해석*/
	public static String findSKY(String skyCode)
	{
		String status = "";
		
		switch(skyCode)
		{
			case "1" :
				status = "맑음";
				break;
			case "3" :
				status = "구름많음";
				break;
			case "4" :
				status = "흐림";
				break;
			default :
				status = "알수없음";
				break;
		}
		return status;
	}
}
