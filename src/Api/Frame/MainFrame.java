package Api.Frame;

import Api.Panel.ClipPanel;
import Api.Twitch.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;

public class MainFrame extends JFrame{
    private static MainFrame instance;
    private TwitchMain twitchApi;
    private List<Streamer> streamerList;


    public MainFrame(final String streamersInfo[][],final String header[])
    {
        twitchApi = TwitchMain.getInstance();
        streamerList = twitchApi.getStremaers();
        JPanel panel = new JPanel();

        // 스트리머 정보 테이블 설정
        JTable table = new JTable(streamersInfo,header) { //수정불가
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton() == 1)  // 클릭
                {
                    Streamer streamer = streamerList.get(table.getSelectedRow());
                    List<String> url_list = twitchApi.getClipsURL(streamer.getId());

                    streamer.to_string();
                    for(String url : url_list)
                        System.out.println(url);
                }
            }
        });

        table.getTableHeader().setReorderingAllowed(false);     // 컬럼들 이동 불가
        table.getTableHeader().setResizingAllowed(false);   // 컬럼 크기 조절 불가
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        pack();

        panel.add(new JLabel("Hello World!"));
        add(panel);

        // 클립 패널 추가
        JPanel clipPanel = new ClipPanel();
        add(clipPanel);
        getContentPane().add(clipPanel,BorderLayout.EAST);

        setTitle("트위치 Application");
        setResizable(false);
        setVisible(true);
        setPreferredSize(new Dimension(1500,1500/12*9));
        setSize(1500,1500/12*9);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static MainFrame getInstance(final String streamersInfo[][], final String header[])
    {
        if(instance == null)
            instance = new MainFrame(streamersInfo,header);

        return instance;
    }
}
