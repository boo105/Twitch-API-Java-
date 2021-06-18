package Api.Frame;

import Api.Twitch.TwitchMain;
import Api.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginView extends JFrame{
    private static LoginView instance;

    private JButton btnLogin;
    private JTextField userText;
    private boolean bLoginCheck;

    private TwitchMain twitchApi;

    private LoginView() {
        // setting
        setTitle("login");
        setSize(280, 150);
        setResizable(false);
        setLocation(800, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // panel
        JPanel panel = new JPanel();
        placeLoginPanel(panel);

        // add
        add(panel);

        // visiible
        setVisible(true);

        twitchApi = TwitchMain.getInstance();
    }

    public static LoginView getInstance()
    {
        if(instance == null)
            instance = new LoginView();
        return instance;
    }

    public void placeLoginPanel(JPanel panel){
        panel.setLayout(null);
        JLabel userLabel = new JLabel("트위치 ID");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);
        userText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { isLoginCheck(); }
        });


        btnLogin = new JButton("Login");
        btnLogin.setBounds(10, 55, 250, 40);
        panel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLoginCheck();
            }
        });
    }

    public void isLoginCheck(){
        String userId = twitchApi.getUserId(userText.getText());

        if(userId != null)
        {
            Main main = Main.getInstance();
            bLoginCheck = true;
            twitchApi.setUserId(userId);
            twitchApi.getUserFollows(userId);
            dispose();

            main.DataProcess();
        }
        else
            JOptionPane.showMessageDialog(null,"잘못된 id입니다");
    }
}