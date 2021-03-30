import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    private static Main main;
    private LoginView loginView;

    private TwitchMain twitchApi;

    private static String header[] = {"이름","생방송","포인트"};
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
        Main main = Main.getInstance();
        main.loginView = LoginView.getInstance();
    }

    // 로그인 성공시 나오는 Main 창
    public void showMainFrame()
    {
        //twitchApi.getUserFollows(twitchApi.getUserId());
        test();
    }

    private void test()
    {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        setStreamersInfo();

        JTable table = new JTable(streamersInfo,header) { //수정불가
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);     // 컬럼들 이동 불가
        table.getTableHeader().setResizingAllowed(false);   // 컬럼 크기 조절 불가
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.pack();

        panel.add(new JLabel("Hello World!"));
        frame.add(panel);

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(840,840/12*9));
        frame.setSize(840,840/12*9);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setStreamersInfo()
    {
        List<Streamer> streamers = twitchApi.getStremaers();

        JFrame progressFrame = new JFrame("데이터 로딩");
        ProgressBar progressBar = new ProgressBar(0,streamers.size());
        progressFrame.getContentPane().add(progressBar, "Center");
        progressFrame.setSize(progressBar.getPreferredSize());
        progressFrame.setVisible(true);

        int count = 0;
        for(Streamer streamer : streamers)
        {
            progressBar.setValue(count);
            twitchApi.getStreamInfo(streamer);
            count++;
        }
        progressBar.setValue(streamers.size());
        System.out.println("count : " + count);

        System.out.println("streamersSize : " + streamers.size() + " header len : " +header.length);
        streamersInfo = new String[streamers.size()][header.length];
        int index = 0;
        for(Streamer streamer : streamers)
        {
            streamersInfo[index] = streamer.getInfo();
            index++;
        }
    }
}
