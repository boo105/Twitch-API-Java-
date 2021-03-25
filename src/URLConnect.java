import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class URLConnect {

    // Connection 설정 후 반환
    public static HttpURLConnection getConnection(URL url,String method ,JSONObject jsonObject)
    {
        HttpURLConnection connection = null;
        try
        {
            // Method 설정 및 헤더 설정 후 연결
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method);

            if(jsonObject != null)
            {
                // 모든 헤더 설정값을 등록해줌
                Iterator<String> iter = ConvertJson.getKeys(jsonObject);
                while (iter.hasNext())
                {
                    String key = iter.next();
                    connection.setRequestProperty(key, jsonObject.get(key).toString());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    // Response상태 출력
    public static void PrintResponseState(HttpURLConnection connection)
    {
        try
        {
            System.out.println("URL : " + connection.getURL());
            System.out.println("Request Method : " + connection.getRequestMethod());
            System.out.println("Request Code : " + connection.getResponseCode());
            System.out.println("Message : " + connection.getResponseMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // body값(응답) 반환
    public static StringBuffer getResponse(HttpURLConnection connection)
    {
        try
        {
            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            reader.close();
            return response;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    // API 서버에 Connect 요청 한뒤 Response를 받음
    public static JSONObject apiConnect(HttpURLConnection connection)
    {
        StringBuffer response = null;
        JSONObject data = null;

        // connection 잘못 될시 처리
        if(connection == null)
        {
            System.out.println("커넥션 미싱");
            return null;
        }

        try
        {
            connection.connect();

            // 응답 상태 확인
            URLConnect.PrintResponseState(connection);

            response = URLConnect.getResponse(connection);
            data = ConvertJson.getJson(response.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return data;
    }


}
