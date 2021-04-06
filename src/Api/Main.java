package Api;

import Api.Frame.*;
import Api.Twitch.*;

import javax.swing.*;
import java.util.List;

public class Main {
    private static Main main;
    private LoginView loginView;
    private MainFrame mainFrame;

    private TwitchMain twitchApi;

    private static String header[] = {"이름","생방송"};
    private static String streamersInfo[][];

    public Main()
    {
        twitchApi = TwitchMain.getInstance();
    }

    public static Main getInstance()
    {
        if(main == null)
            main = new Main();
        return main;
    }

    public static void main(String[] args)
    {
        main = Main.getInstance();
        main.loginView = LoginView.getInstance();
    }

    // 로그인 성공시 데이터 로딩
    public void DataProcess()
    {
        Runnable increaseProgress = new Runnable() {
            @Override
            public void run() {
                setStreamersInfo();
            }
        };

        Runnable test = () -> {
            setStreamersInfo();
        };

        Thread worker = new Thread(increaseProgress);
        worker.start();
    }

    public void startMain()
    {
        main.mainFrame = MainFrame.getInstance(streamersInfo,header);
    }

    // 스트리머 방송상태 로딩
    private void setStreamersInfo()
    {
        List<Streamer> streamers = twitchApi.getStremaers();
        ProgressBar progressBar = new ProgressBar(0,streamers.size());
        // 라이브 상태 불러오기 및 progressBar에 상태 반영
        int count = 0;
        for(Streamer streamer : streamers)
        {
            progressBar.setValue(count);
            twitchApi.getStreamInfo(streamer);
            count++;
        }
        progressBar.setValue(streamers.size());

        // 스트리머 정보 테이블 설정
        System.out.println("streamersSize : " + streamers.size() + " header len : " +header.length);
        streamersInfo = new String[streamers.size()][header.length];
        int index = 0;
        for(Streamer streamer : streamers)
        {
            streamersInfo[index] = streamer.getInfo();
            index++;
        }

        // progressBar 제거 후 MainFrame 실행
        progressBar.dispose();
        startMain();
    }
}
