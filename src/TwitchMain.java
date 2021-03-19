import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TwitchMain {
    private static final String cliendId = "rhyxuce818fcwpytxr495867i02eiz";
    private static final String redirectURL = "http://localhost";
    private static final String tempAccessToken = "Bearer qs9ypu1ytvd718bs1x2auero0f4ed9";
    private static final String tempSecret = "wnj3usc2hjuzs4k2y3l7g5xfzh574o";

    public static void main(String[] args) {

        //getChannel("44445592");
        Auth();
    }

    private static void getChannel(String channelName)
    {
        URL url;
        HttpURLConnection connection = null;

        try
        {
            url = new URL("https://api.twitch.tv/helix/channels?broadcaster_id="  + channelName);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Client-ID", cliendId);
            connection.setRequestProperty("Authorization", tempAccessToken);
            connection.setRequestMethod("GET");
            connection.connect();

            PrintResponseState(connection);

            StringBuffer response = getResponse(connection);
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

            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            PrintResponseState(connection);

            StringBuffer response = getResponse(connection);
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

    // Response상태 출력
    private static void PrintResponseState(HttpURLConnection connection)
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

    // body값(응답) 출력
    private static StringBuffer getResponse(HttpURLConnection connection)
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
}
