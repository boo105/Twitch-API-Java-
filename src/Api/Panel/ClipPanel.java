package Api.Panel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClipPanel extends JPanel {

    private static ClipPanel instance;
    private WebEngine webEngine;

    public ClipPanel()
    {
        JFXPanel fxPanel = new JFXPanel();

        //setLayout(new GridLayout(2,2));

        Platform.runLater(() -> {
            initFX(fxPanel);
        });
        add(fxPanel);
    }

    public static ClipPanel getInstance()
    {
        if(instance == null)
            instance = new ClipPanel();

        return instance;
    }

    private void initFX(JFXPanel fxPanel)
    {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);

        WebView webView = new WebView();

        group.getChildren().add(webView);
        webView.setMinSize(800, 700);
        webView.setMaxSize(800, 700);

        webEngine = webView.getEngine();
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
    }

    public void AddWebView(String url)
    {
        Platform.runLater(() -> {
            webEngine.load(url);
        });
    }

}
