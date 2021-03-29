import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame{
    private JButton btnLogin;
    private JButton btnInit;
    private JTextField userText;
    private boolean bLoginCheck;

    public LoginView() {
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
    }

    public void placeLoginPanel(JPanel panel){
        panel.setLayout(null);
        JLabel userLabel = new JLabel("트위치 ID");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);


        btnLogin = new JButton("Login");
        btnLogin.setBounds(160, 80, 100, 25);
        panel.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLoginCheck();
            }
        });
    }

    public void isLoginCheck(){
        String userId = Main.twitchApi.getUserId(userText.getText());

        if(userId != null)
        {
            bLoginCheck = true;
            Main.twitchApi.setUserId(userId);
            Main.showMainFrame();
        }
        else
            JOptionPane.showMessageDialog(null,"잘못된 id입니다");

        /*
        if(userText.getText().equals("test") && new String(passText.getPassword()).equals("1234")){
            JOptionPane.showMessageDialog(null, "Success");
            bLoginCheck = true;

            // 로그인 성공이라면 매니져창 뛰우기
            if(isLogin()){
                main.showFrameTest(); // 메인창 메소드를 이용해 창뛰우기
            }
        }else{
            JOptionPane.showMessageDialog(null, "Faild");
        }*/
    }

    public boolean isLogin() {
        return bLoginCheck;
    }
}