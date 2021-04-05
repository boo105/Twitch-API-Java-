package Api.Frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private static MainFrame instance;

    public MainFrame(final String streamersInfo[][],final String header[])
    {
        JPanel panel = new JPanel();

        // 스트리머 정보 테이블 설정
        JTable table = new JTable(streamersInfo,header) { //수정불가
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);     // 컬럼들 이동 불가
        table.getTableHeader().setResizingAllowed(false);   // 컬럼 크기 조절 불가
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        pack();

        panel.add(new JLabel("Hello World!"));
        add(panel);

        setTitle("트위치 Application");
        setResizable(false);
        setVisible(true);
        setPreferredSize(new Dimension(840,840/12*9));
        setSize(840,840/12*9);
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
