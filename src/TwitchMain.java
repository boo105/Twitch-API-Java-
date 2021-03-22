import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TwitchMain {
    private static final String cliendId = "rhyxuce818fcwpytxr495867i02eiz";
    private static final String redirectURL = "http://localhost";
    private static final String tempSecret = "wnj3usc2hjuzs4k2y3l7g5xfzh574o";
    private static String accessToken = "";

    private static URL url = null;
    private static HttpURLConnection connection = null;

    public static void main(String[] args) {
        Auth();
        getUserId("boo105");
        getChannel("44445592");
    }

    // 일단 채널 얻어오는기능인데 임시
    private static void getChannel(String channelName)
    {
        System.out.println("getChannel API 실행");
        try
        {
            url = new URL("https://api.twitch.tv/helix/channels?broadcaster_id="  + channelName);

            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);

            apiConnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(connection != null)
                connection.disconnect();
        }
    }

    // 인증 및 Access Token 획득
    private static void Auth()
    {
        System.out.println("인증 API 실행");
        try
        {
            /*
            url = new URL("https://id.twitch.tv/oauth2/authorize?response_type=token&client_id=" + cliendId +
                    "&redirect_uri=" + redirectURL +
                    "&scope=viewing_activity_read&state=c3ab8aa609ea11e793ae92361f002671");*/
            url = new URL("https://id.twitch.tv/oauth2/token?client_id=" + cliendId +
                    "&client_secret=" + tempSecret +
                    "&grant_type=client_credentials");
            connection = URLConnect.getConnection(url,"POST",null);
            StringBuffer response = apiConnect();
            JSONObject data = ConvertJson.getJson(response.toString());
            accessToken = data.get("access_token").toString();
            System.out.println("access token : " + accessToken);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(connection != null)
                connection.disconnect();
        }
    }

    private static void getUserId(String id)
    {
        System.out.println("getUser API 실행");
        try
        {
            url = new URL("https://api.twitch.tv/helix/users?login=" + id);
            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);
            apiConnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(connection != null)
                connection.disconnect();
        }
    }

    // API 서버에 Connect 요청 한뒤 Response를 받음
    private static StringBuffer apiConnect()
    {
        StringBuffer response = null;

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
            System.out.println("Response : " + response.toString() + "\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return response;
    }
}
