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

    private ClipPanel()
    {
        JFXPanel fxPanel = new JFXPanel();

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
        webView.setMinSize(1000, 420);
        webView.setMaxSize(1000, 420);

        webEngine = webView.getEngine();
    }

    public void AddWebView(String url)
    {
        Platform.runLater(() -> {
            webEngine.load(url);
        });
    }

}
