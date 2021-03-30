import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TwitchMain {
    private static TwitchMain instance;

    private static final String cliendId = "rhyxuce818fcwpytxr495867i02eiz";
    private static final String tempSecret = "wnj3usc2hjuzs4k2y3l7g5xfzh574o";
    private static String accessToken = "";

    // 유저 id
    private static String userId;
    private static List<Streamer> streamers;

    private static URL url = null;
    private static HttpURLConnection connection = null;

    public TwitchMain()
    {
        streamers = new ArrayList<>();
        Auth();
    }

    public static TwitchMain getInstance()
    {
        if(instance == null)
            instance = new TwitchMain();

        return instance;
    }

    public static void main(String[] args) {
        streamers = new ArrayList<>();

        /*
        Auth();
        userId = getUserId("boo105");
        getUserFollows(userId);
        getChannel("103825127");
        getStreamInfo(streamers.get(47));
        */
        /*
        풍월량 : 103825127 (47번 배열)
        침착맨 : 66375105 (46번 배열)
         */
    }

    // 인증 및 Access Token 획득
    private void Auth()
    {
        System.out.println("인증 API 실행");
        try
        {
            url = new URL("https://id.twitch.tv/oauth2/token?client_id=" + cliendId +
                    "&client_secret=" + tempSecret +
                    "&grant_type=client_credentials");
            connection = URLConnect.getConnection(url,"POST",null);
            JSONObject data = URLConnect.apiConnect(connection);
            accessToken = data.get("access_token").toString();
            System.out.println("access token : " + accessToken + "\n");
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

    // 일단 채널 얻어오는기능인데 임시
    public void getChannel(String channelName)
    {
        System.out.println("\ngetChannel API 실행");
        try
        {
            url = new URL("https://api.twitch.tv/helix/channels?broadcaster_id="  + channelName);

            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);

            JSONObject data = URLConnect.apiConnect(connection);
            System.out.println("channel : " + data);
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

    public String getUserId(String id)
    {
        System.out.println("\ngetUser API 실행");
        try
        {
            url = new URL("https://api.twitch.tv/helix/users?login=" + id);
            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);
            JSONObject data = URLConnect.apiConnect(connection);

            JSONArray jsonArray = (JSONArray) data.get("data");
            data = (JSONObject) jsonArray.get(0);

            System.out.println("data : " + data);

            return (String) data.get("id");
        }
        catch(Exception e)
        {
            return null;
        }
        finally
        {
            if(connection != null)
                connection.disconnect();
        }
    }

    // follow 한 채널 얻기
    public void getUserFollows(String id)
    {
        System.out.println("\ngetUserFollows API 실행");
        try
        {
            // query값 first를 100을 줘서 최대 100개의 follows를 불러옴
            url = new URL("https://api.twitch.tv/helix/users/follows?first=100&from_id=" + id);
            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);
            JSONObject data = URLConnect.apiConnect(connection);
            System.out.println("data : " + data);

            // 모든 follow 한 스트리머들을 저장함
            JSONArray follows = (JSONArray) data.get("data");
            for(Object object : follows)
            {
                JSONObject follow = (JSONObject) object;
                String streamer_id = (String) follow.get("to_id");
                String streamer_login = (String) follow.get("to_login");
                String streamer_name = (String) follow.get("to_name");

                Streamer streamer = new Streamer(streamer_id,streamer_login,streamer_name);
                streamer.to_string();
                streamers.add(streamer);
            }
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

    // 생방송 정보를 획득
    public void getStreamInfo(Streamer streamer)
    {
        System.out.println("\ngetStreamInfo API 실행");
        try
        {
            // query값 first를 100을 줘서 최대 100개의 follows를 불러옴
            url = new URL("https://api.twitch.tv/helix/streams?user_id=" + streamer.getId());
            // 헤더 설정
            JSONObject headers = new JSONObject();
            headers.put("Client-ID",cliendId);
            headers.put("Authorization","Bearer " + accessToken);

            connection = URLConnect.getConnection(url,"GET",headers);
            JSONObject data = URLConnect.apiConnect(connection);
            System.out.println("data : " + data);

            String live = "offline";

            JSONArray jsonArray = (JSONArray) data.get("data");

            if(jsonArray.size() != 0)
            {
                data = (JSONObject) jsonArray.get(0);
                live = (String) data.get("type");
            }
            streamer.setIsLive(live);
            System.out.println(streamer.getName() + " 의 상태 : " + live);
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

    public void setStreamersInfo()
    {
        for(Streamer streamer : streamers)
        {
            getStreamInfo(streamer);
        }
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String id)
    {
        userId = id;
    }

    public List<Streamer> getStremaers()
    {
        return streamers;
    }
}
