import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WebChecker {

    private JFrame frame;
    private Label statusLabel;
    public JTextField insertURL;
    public String newURL;

    public WebChecker(){
        prepareGUI();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WebChecker window = new WebChecker();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void prepareGUI(){

        // Main frame for application
        frame = new JFrame("WebChecker");
        frame.setBounds(500, 500, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);


        // Text field for entering URL
        insertURL = new JTextField();
        insertURL.setText("Enter URL");
        insertURL.setBounds(50, 50, 134, 30);
        frame.getContentPane().add(insertURL);
        insertURL.setColumns(1);


        // Label for showing status code of succeededStatus (OK, Not found etc)
        statusLabel = new Label();
        statusLabel.setBounds(50,100,1000,50);
        frame.getContentPane().add(statusLabel);

        //Add new button for checking URL via HTTPClient class
        Button btnChange = new Button("Check URL");
        btnChange.setBounds(220, 50, 117, 29);
        btnChange.setActionCommand("OK");
        btnChange.addActionListener(new ButtonClickListener());
        frame.getContentPane().add(btnChange);
    }

    public class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if( command.equals( "OK" ))  {
                newURL = insertURL.getText();
                HTTPClient httpClient = new HTTPClient();
                try {
                    httpClient.validateUrl();  // Kako poslati string iz textualnog polja u drugu klasu na obradu ?
                                                // Ideja je uzeti string iz polja koje se unese, prilikom pritiska gumba ga spremit u varijablu
                                                // i poslat ga u klasu HTTPClient na obradu gdje se validira cijeli URL. Baca mi masu grešaka, tipa nUllPointerException
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // Prints out status code of an URL
                statusLabel.setText(newURL);
            }

        }
    }
}