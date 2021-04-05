package Api.Frame;

import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JFrame {
    JPanel panel;
    JProgressBar progressBar;

    float progressMax = 0;

    public ProgressBar(int min,int max) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        progressMax = max;

        progressBar = new JProgressBar();
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
        progressBar.setValue(min);
        progressBar.setBounds(0,0,300,50);
        progressBar.setStringPainted(true);

        panel.add(progressBar,"Center");

        add(panel);
        setTitle("데이터 로딩중...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,80);
        setVisible(true);
    }

    public void setValue(int percent) {
        progressBar.setValue(percent);
    }
}
