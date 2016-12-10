package com.kevin.Login;

/**
 * Created by hcnucai on 2016/12/10.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MenuWindow2 extends JFrame {
    final int WINDOW_WIDTH = 500;
    final int WINDOW_HEIGHT = 200;

    private JMenuBar menuBar;
    private JMenu fileMenu, textMenu, colorMenu;
    private JMenuItem exitItem;
    private JRadioButtonMenuItem blackItem, redItem, blueItem;
    private JCheckBoxMenuItem visibleItem;

    private JLabel colorMsg;

    public MenuWindow2() {
        // set title for the window
        setTitle("Menu Window");

        // set size and location for the window
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // specify the action for the close button
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set the font for the menu and menuItem
        // UIManager.put("Menu.font", new Font(Font.DIALOG, Font.BOLD, 16));
        // UIManager.put("MenuItem.font", new Font(Font.DIALOG, Font.BOLD, 14));

        // create a menu bar
        menuBar = new JMenuBar();

        // create a File menu
        buildFileMenu();

        // create a Text menu
        buildTextMenu();

        // add the file menu and text menu to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(textMenu);

        // add the menu bar to the window
        setJMenuBar(menuBar);

        // create a label for coloring
        colorMsg = new JLabel("Give you some color see see", JLabel.CENTER);
        colorMsg.setFont(new Font(Font.DIALOG, Font.BOLD+Font.ITALIC, 20));

        // add the label to the window
        add(colorMsg);

        // regist events
        MenuItemListener itemListener = new MenuItemListener();
        exitItem.addActionListener(itemListener);
        blackItem.addActionListener(itemListener);
        redItem.addActionListener(itemListener);
        blueItem.addActionListener(itemListener);
        visibleItem.addActionListener(itemListener);

        // show the window
        setVisible(true);
    }

    public void buildFileMenu() {
        // create file menu with the title "File"
        fileMenu = new JMenu("File");

        // set the font of the fileMenu
        fileMenu.setFont(new Font(Font.DIALOG, Font.BOLD, 16));

        // create exit menu item
        exitItem = new JMenuItem("exit");

        // set the font of the exit menu item
        exitItem.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        // add the exit menu item to the file menu
        fileMenu.add(exitItem);

    }

    public void buildTextMenu() {
        // create text menu with the title "Text"
        textMenu = new JMenu("Text");
        textMenu.setFont(new Font(Font.DIALOG, Font.BOLD, 16));

        buildColorMenu();

        // create visible checkBox menu item
        visibleItem = new JCheckBoxMenuItem("visible", true);

        // set the font of the visible checkBox menu item
        visibleItem.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        // put the color menu to the text menu
        textMenu.add(colorMenu);

        // put the visible menu item to the text menu
        textMenu.add(visibleItem);
    }

    private void buildColorMenu() {
        // create color menu
        colorMenu = new JMenu("color");

        // set the font of the color menu
        colorMenu.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        // create the black, red, and blue radio button menu items
        blackItem = new JRadioButtonMenuItem("black", true);
        redItem = new JRadioButtonMenuItem("red");
        blueItem = new JRadioButtonMenuItem("blue");

        // set the fonts of the black menu item, red menu item, and blue menu item
        blackItem.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        redItem.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
        blueItem.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));

        // create a group for these radioButton menu items
        ButtonGroup group = new ButtonGroup();
        group.add(blackItem);
        group.add(redItem);
        group.add(blueItem);

        // put the items to the color menu
        colorMenu.add(blackItem);
        colorMenu.add(redItem);
        colorMenu.add(blueItem);
    }

    private class MenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ex) {
            if(ex.getSource()==exitItem){
                System.exit(0);
            }
            else if(ex.getSource()==visibleItem){
                if(visibleItem.isSelected()){
                    colorMsg.setVisible(true);
                }
                else{
                    colorMsg.setVisible(false);
                }
            }
            else if(ex.getActionCommand().equals("black")){
                colorMsg.setForeground(Color.BLACK);
            }
            else if(ex.getActionCommand().equals("red")){
                colorMsg.setForeground(Color.RED);
            }
            else{
                colorMsg.setForeground(Color.BLUE);
            }
        }

    }



    public static void main(String[] args) {
        new MenuWindow2();
    }

}

