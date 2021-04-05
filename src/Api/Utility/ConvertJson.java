package Api.Utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Iterator;
import java.util.Set;

public class ConvertJson {

    /*
    인증 를 제외한 트위치 API json 형식
    { "data" : [ { } ] }
    * */

    // String 형태를 Json으로 반환
    public static JSONObject getJson(String data)
    {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try
        {
            jsonObject = (JSONObject)parser.parse(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    // 모든 키값 반환
    public static Iterator<String> getKeys(JSONObject jsonObject)
    {
        Set key =jsonObject.keySet();
        return key.iterator();
    }
}
