import java.lang.reflect.Array;

public class Streamer {
    private String id;      // 트위치 서버에서 스트리머를 구분하는 id
    private String login;   // 스트리머가 로그인할때 사용하는 id
    private String name;    // 스트리머 닉네임

    public Streamer(String id,String login, String name)
    {
        this.id = id;
        this.login = login;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getLogin()
    {
        return login;
    }

    public String getName()
    {
        return name;
    }

    public void to_string()
    {
        System.out.println("name : " + name + "\tid : " + id + "\tlogin : " + login);
    }

    public String[] getInfo()
    {
        String[] info = {name,id,login};
        return info;
    }
}
