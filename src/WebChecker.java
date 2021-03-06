import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class WebChecker implements ActionListener {

    private JFrame frame;
    private Label statusLabel, validLabel, userLabel, passLabel, connStatus, commandLabel, exitCode;
    public JTextField insertURL, username, servCommand;
    public String newURL, susername, scommand;
    public JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem sshItem;
    private Button btnConnect;
    private JPasswordField passwordField;
    public char[] spassword;

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
        frame.setBounds(50, 50, 930, 600);
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
        insertURL.setBounds(50, 50, 300, 30);
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
        Color color = new Color(57, 62, 70);
        textArea.setBackground(color);
        textArea.setForeground(Color.WHITE);
        textArea.setBounds(50, 160, 800, 300);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        frame.getContentPane().add(textArea);

        //Add new button for checking URL via HTTPClient class
        Button btnChange = new Button("Check URL");
        btnChange.setBounds(380, 50, 117, 29);
        btnChange.setActionCommand("OK");
        btnChange.addActionListener(new ButtonClickListener());
        frame.getContentPane().add(btnChange);

        // SSH Connection fields
        userLabel = new Label();
        userLabel.setText("Username:");
        userLabel.setBounds(40,40,70,30);

        username = new JTextField();
        username.setBounds(110, 40, 300, 30);

        passLabel = new Label();
        passLabel.setText("Password:");
        passLabel.setBounds(40,80,70,30);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 80, 300, 30);

        commandLabel = new Label();
        commandLabel.setText("Command:");
        commandLabel.setBounds(40,120,70,30);

        servCommand= new JTextField();
        servCommand.setBounds(110, 120, 300, 30);

        connStatus = new Label();
        connStatus.setBounds(40,215,400,20);

        exitCode = new Label();
        exitCode.setBounds(40,240,400,20);

        // Connect button
        btnConnect = new Button("Connect");
        btnConnect.setBounds(175, 170, 115, 30);
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
            newFrame.getContentPane().add(passwordField);
            newFrame.getContentPane().add(userLabel);
            newFrame.getContentPane().add(passLabel);
            newFrame.getContentPane().add(commandLabel);
            newFrame.getContentPane().add(servCommand);
            newFrame.getContentPane().add(btnConnect);
            newFrame.getContentPane().add(connStatus);
            newFrame.getContentPane().add(exitCode);

            // Reset labels
            insertURL.setText("Enter URL");
            validLabel.setText(null);
            statusLabel.setText(null);
        }
    }

    // Button for SSH Connection
    public class SSHButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            susername = username.getText();
            spassword = passwordField.getPassword();
            scommand = servCommand.getText();
            if (command.equals("OK")) {
                SSH sshConn = new SSH();
                sshConn.ConnectandExecute(susername, spassword, scommand);
                if (sshConn.successfulConn){
                    connStatus.setText("Connected !");
                    exitCode.setText("Exit Status: " + sshConn.channelStatus);

                    // Deleting old text from URL connection
                    textArea.setText(null);
                    textArea.append(new String(sshConn.tmp));
                }else{
                    connStatus.setText("Error: " + sshConn.error);
                }
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