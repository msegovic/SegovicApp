import java.awt.*;
import java.awt.event.*;

public class CheckboxDemo {

    private Frame mainFrame;
    private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;

    public CheckboxDemo(){
        prepareGUI();
    }

    public static void main(String[] args){
        CheckboxDemo  CheckboxDemo = new CheckboxDemo();
        CheckboxDemo.showCheckBoxDemo();
    }

    private void prepareGUI(){
        mainFrame = new Frame("Java AWT Examples");
        mainFrame.setSize(400,400);
        //GridLayout smjesta komponente u ćelije, a komponenta zauzima sav dostupan prostor
        // postoje jos Border, Box, Card, Flow, GridBag, Group i Spring
        mainFrame.setLayout(new GridLayout(3, 1));
        //WindowListener definira metode koje obrađuju većinu događaja prozora, kao što su
        //zapisi za otvaranje i zatvaranje prozora, aktivaciju i deaktivaciju prozora...
        //ostali događaji s prozorima su WindowFocusListener i WindowStateListener
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        statusLabel = new Label();
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350,100);

        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    private void showCheckBoxDemo(){

        headerLabel.setText("Control in action: CheckBox");

        Checkbox chkApple = new Checkbox("Apple");
        Checkbox chkMango = new Checkbox("Mango");
        Checkbox chkPeer = new Checkbox("Peer");


        chkApple.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Apple Checkbox: "
                        + (e.getStateChange()==1?"checked":"unchecked"));
            }
        });

        chkMango.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Mango Checkbox: "
                        + (e.getStateChange()==1?"checked":"unchecked"));
            }
        });

        chkPeer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Peer Checkbox: "
                        + (e.getStateChange()==1?"checked":"unchecked"));
            }
        });

        controlPanel.add(chkApple);
        controlPanel.add(chkMango);
        controlPanel.add(chkPeer);

        mainFrame.setVisible(true);
    }
}