import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class WebChecker implements ActionListener {

    private JFrame frame;
    private Label statusLabel, validLabel, userLabel, passLabel;
    public JTextField insertURL, username, password;
    public String newURL, susername, spassword;
    public JTextArea textArea;
    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem sshItem;
    public Button btnConnect;

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
        frame.setBounds(50, 50, 930, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);


        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Connect");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //SSH menu item
        sshItem = new JMenuItem("SSH Connection");
        sshItem.addActionListener(this);
        menu.add(sshItem);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);


        // Text field for entering URL
        insertURL = new JTextField();
        insertURL.setText("Enter URL");
        insertURL.setBounds(50, 50, 134, 30);
        frame.getContentPane().add(insertURL);
        insertURL.setColumns(1);

        // Label for showing status code of succeededStatus (OK, Not found etc)
        validLabel = new Label();
        validLabel.setBounds(50,100,1000,20);
        frame.getContentPane().add(validLabel);

        statusLabel = new Label();
        statusLabel.setBounds(50,130,1000,20);
        frame.getContentPane().add(statusLabel);

        // Text area for response headers
        textArea = new JTextArea(5, 20);
        textArea.setBounds(50, 160, 800, 300);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        frame.getContentPane().add(textArea);

        //Add new button for checking URL via HTTPClient class
        Button btnChange = new Button("Check URL");
        btnChange.setBounds(220, 50, 117, 29);
        btnChange.setActionCommand("OK");
        btnChange.addActionListener(new ButtonClickListener());
        frame.getContentPane().add(btnChange);

        // SSH Connection fields
        userLabel = new Label();
        userLabel.setText("Username");
        userLabel.setBounds(130,40,500,20);

        username = new JTextField();
        username.setBounds(130, 65, 200, 30);
        username.setColumns(1);

        passLabel = new Label();
        passLabel.setText("Password");
        passLabel.setBounds(130,110,500,20);

        password = new JTextField();
        password.setBounds(130, 135, 200, 30);
        password.setColumns(1);

        // Connect button
        btnConnect = new Button("Connect");
        btnConnect.setBounds(175, 180, 115, 30);
        btnConnect.setActionCommand("OK");
        btnConnect.addActionListener(new SSHButtonListener());

    }

    // New frame for SSH connection
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sshItem){
            JFrame newFrame = new JFrame();
            newFrame.setVisible(true);
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setBounds(50, 50, 480, 320);
            newFrame.setTitle("SSH Connection");
            newFrame.getContentPane().setLayout(null);

            // Add text fields and labels to a newframe
            newFrame.getContentPane().add(username);
            newFrame.getContentPane().add(password);
            newFrame.getContentPane().add(userLabel);
            newFrame.getContentPane().add(passLabel);
            newFrame.getContentPane().add(btnConnect);

            // Store username and password in strings
            susername = username.getText();
            spassword = password.getText();

        }
    }

    // Button for SSH Connection
    public class SSHButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("OK")) {


            }
        }
    }

    public class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("OK")) {

                // Store data from text field into new variable and send it to HTTPClient for validation
                newURL = insertURL.getText();
                HTTPClient httpClient = new HTTPClient();
                Map<String, List<String>> map = new HashMap<>();
                try {
                    map = httpClient.validateUrl(newURL);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                // Deleting old text
                textArea.setText(null);

                // Checking for incorrect URL and printing out status codes
                if (httpClient.incorrectURLS != 0) {
                    statusLabel.setText("Incorrect URL, please try again!");
                } else {
                    // Prints out status code of an URL
                    if (httpClient.succeededStatus == "OK") {
                        validLabel.setText("URL valid !");
                        statusLabel.setText("Status code: " + httpClient.succeededStatus);
                        for (Map.Entry<String, List<String>> entry : map.entrySet())
                        {
                            textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
                        }
                    } else {
                        validLabel.setText("URL valid !");
                        statusLabel.setText("Status code: " + httpClient.failedStatus);
                        for (Map.Entry<String, List<String>> entry : map.entrySet())
                        {
                            textArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
                        }
                    }
                }
            }
        }
    }
}