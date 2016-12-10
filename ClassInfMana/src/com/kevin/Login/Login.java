package com.kevin.Login;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JFrame{
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 155;
    //设置label 和textField
    private JLabel usernameLabel = new JLabel("用户名");
    private JLabel passWLabel = new JLabel("密码");
    private JTextField usernameTextField = new JTextField();
    private  JPasswordField passwoTextField = new JPasswordField();
    private  JButton loginBtn = new JButton("确定");
    private  JButton closeBtn = new JButton("关闭");
    private  JPanel usernamePanel = new JPanel();
    private  JPanel passwordPanel = new JPanel();
    private  JPanel btnPanel = new JPanel();
    public Login(){
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("系统登录");
        Container c = getContentPane();
        //设置主布局为网格布局
        c.setLayout(new GridLayout(3,1,10,10));
        //设置用户名panel
        usernamePanel.setLayout(new FlowLayout());
        FlowLayout usernameLayout = (FlowLayout)usernamePanel.getLayout();
        usernameLayout.setAlignment(FlowLayout.CENTER);

        passwordPanel.setLayout(new FlowLayout());
        FlowLayout passwordLayout = (FlowLayout)usernamePanel.getLayout();
        passwordLayout.setAlignment(FlowLayout.CENTER);

        btnPanel.setLayout(new FlowLayout());
        FlowLayout btnPanelLayout = (FlowLayout)usernamePanel.getLayout();
        btnPanelLayout.setAlignment(FlowLayout.CENTER);
        //加元素
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        usernameTextField.setColumns(18);
        passwordPanel.add(passWLabel);
        passwordPanel.add(passwoTextField);
        passwoTextField.setColumns(18);
        btnPanel.add(loginBtn);
        btnPanel.add(closeBtn);
        c.add(usernamePanel);
        c.add(passwordPanel);
        c.add(btnPanel);
       //增加监听事件
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
      loginBtn.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              new Main();
//              JOptionPane.showMessageDialog(null, "输入密码错误");
          }
      });
        setVisible(true);
    }

    public static void main(String args[]){
        new Login();
    }

}
