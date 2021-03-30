import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JPanel {
    JProgressBar progressBar;
    Label status;

    float progressMax = 0;

    public ProgressBar(int min,int max) {
        setLayout(new BorderLayout());
        progressMax = max;

        progressBar = new JProgressBar();
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
        progressBar.setValue(min);
        progressBar.setStringPainted(true);

        status = new Label("");
        System.out.println("min : " + min + " max : " + max);
        add(progressBar,"Center");
        add(status, "South");
    }

    public void setValue(int i) {
        float percent = (float) ((float) i/progressMax) * 100;
        System.out.println("Percent : " + (int)percent);
        progressBar.setValue((int)percent);
        status.setText((int)percent+"%");
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 80);
    }
}
