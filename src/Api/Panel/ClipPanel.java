package Api.Panel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class ClipPanel extends JPanel {

    public ClipPanel()
    {

        JFXPanel fxPanel = new JFXPanel();
        JFXPanel fxPanel2 = new JFXPanel();
        JFXPanel fxPanel3 = new JFXPanel();
        JFXPanel fxPanel4 = new JFXPanel();

        setLayout(new GridLayout(2,2));


        // 쓰레드 4개로 돌려서 작업해야함
        AddWebView(fxPanel);
        AddWebView(fxPanel2);
        AddWebView(fxPanel3);
        AddWebView(fxPanel4);
    }

    private void initFX(JFXPanel fxPanel)
    {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);

        WebView webView = new WebView();

        group.getChildren().add(webView);
        webView.setMinSize(500, 400);
        webView.setMaxSize(500, 400);

        WebEngine webEngine = webView.getEngine();

        webEngine.load("https://www.twitch.tv/hanryang1125/clip/GoodTolerantGrassPipeHype-0cBJ9PbUZlkVfvft?filter=clips&range=30d&sort=time");
    }

    public void AddWebView(final JFXPanel fxPanel)
    {
        Platform.runLater(() -> {
            initFX(fxPanel);
        });

        add(fxPanel);
    }

}
