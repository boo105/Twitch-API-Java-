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

    public static void main(String[] args) {
        Auth();
        getChannel("44445592");
    }

    // 일단 채널 얻어오는기능인데 임시
    private static void getChannel(String channelName)
    {
        URL url;
        HttpURLConnection connection = null;

        try
        {
            url = new URL("https://api.twitch.tv/helix/channels?broadcaster_id="  + channelName);

            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);
            connection.connect();

            URLConnect.PrintResponseState(connection);

            StringBuffer response = URLConnect.getResponse(connection);
            System.out.println("Response : " + response.toString());
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
        URL url;
        HttpURLConnection connection = null;
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
            connection.connect();

            URLConnect.PrintResponseState(connection);

            // response 에서 Access token 값 추출
            StringBuffer response = URLConnect.getResponse(connection);
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
}
