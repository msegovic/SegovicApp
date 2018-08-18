

import java.awt.*;
import java.awt.event.*;

public class ListDemo {

    private Frame mainFrame;
    private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;

    public ListDemo(){
        prepareGUI();
    }

    public static void main(String[] args){
        ListDemo  ListDemo = new ListDemo();
        ListDemo.showListDemo();
    }

    private void prepareGUI(){
        mainFrame = new Frame("Java AWT Examples");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new GridLayout(3, 1));
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

    private void showListDemo(){

        headerLabel.setText("Control in action: List");
        final List fruitList = new List(4,false);

        fruitList.add("Apple");
        fruitList.add("Grapes");
        fruitList.add("Mango");
        fruitList.add("Peer");

        final List vegetableList = new List(4,true);

        vegetableList.add("Green Bean");
        vegetableList.add("Onion");
        vegetableList.add("Potato");
        vegetableList.add("Tomato");

        Button showButton = new Button("Show");

        showButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String data = "Fruits Selected: "
                        + fruitList.getItem(fruitList.getSelectedIndex());
                data += ", Vegetables selected: ";
                for(String vegetable:vegetableList.getSelectedItems()){
                    data += vegetable + " ";
                }
                statusLabel.setText(data);
            }
        });

        controlPanel.add(fruitList);
        controlPanel.add(vegetableList);
        controlPanel.add(showButton);

        mainFrame.setVisible(true);
    }
}