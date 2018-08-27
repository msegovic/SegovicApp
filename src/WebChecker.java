import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class WebChecker implements ActionListener {

    private JFrame frame;
    private Label statusLabel;
    private Label validLabel;
    public JTextField insertURL;
    public String newURL;
    public JTextArea textArea;
    private JScrollPane scroll;
    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem sshItem;

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
        frame.setBounds(50, 50, 1280, 720);
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

        textArea = new JTextArea(5, 20);
        textArea.setBounds(50, 160, 800, 300);
        frame.getContentPane().add(textArea);
        textArea.setEditable(false);
        textArea.setLineWrap(true);

        //Add new button for checking URL via HTTPClient class
        Button btnChange = new Button("Check URL");
        btnChange.setBounds(220, 50, 117, 29);
        btnChange.setActionCommand("OK");
        btnChange.addActionListener(new ButtonClickListener());
        frame.getContentPane().add(btnChange);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sshItem){
            JFrame newFrame = new JFrame();
            newFrame.setVisible(true);
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setBounds(50, 50, 640, 480);
            newFrame.setTitle("SSH Connection");
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

                // Text area for printing out response headers



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
                    }
                }
            }
        }
    }
}