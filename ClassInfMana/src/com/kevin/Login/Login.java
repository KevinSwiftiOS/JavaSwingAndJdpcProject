package com.kevin.Login;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

public class Login extends JFrame {
    //链接数据库的操作
    public static final String names = "com.mysql.jdbc.Driver";
    Connection conn = null;
    Statement sta = null;
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 155;
    //设置label 和textField
    private JLabel usernameLabel = new JLabel("用户名");
    private JLabel passWLabel = new JLabel("密码");
    private JTextField usernameTextField = new JTextField();
    private JPasswordField passwoTextField = new JPasswordField();
    private JButton loginBtn = new JButton("确定");
    private JButton closeBtn = new JButton("关闭");
    private JPanel usernamePanel = new JPanel();
    private JPanel passwordPanel = new JPanel();
    private JPanel btnPanel = new JPanel();
    public Login() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("系统登录");
        Container c = getContentPane();
        //设置主布局为网格布局
        c.setLayout(new GridLayout(3, 1, 10, 10));
        //设置用户名panel
        usernamePanel.setLayout(new FlowLayout());
        FlowLayout usernameLayout = (FlowLayout) usernamePanel.getLayout();
        usernameLayout.setAlignment(FlowLayout.CENTER);

        passwordPanel.setLayout(new FlowLayout());
        FlowLayout passwordLayout = (FlowLayout) usernamePanel.getLayout();
        passwordLayout.setAlignment(FlowLayout.CENTER);

        btnPanel.setLayout(new FlowLayout());
        FlowLayout btnPanelLayout = (FlowLayout) btnPanel.getLayout();
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
                //提醒用户输入用户名和密码
                String username = usernameTextField.getText().toString();
                String password = passwoTextField.getText().toString();
                if (username.equals("")) {
                    JOptionPane.showMessageDialog(null, "用户名未填", "警告", JOptionPane.ERROR_MESSAGE);
                } else if (password.equals("")) {
                    JOptionPane.showMessageDialog(null, "密码未填", "警告", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        //提醒用户输入用户名和密码

                        Class.forName(names);

                        try {

                            String jdbcPath = "jdbc:mysql://localhost:3306/student";
                            String user = "root";
                            String pass = "123456";
                            conn = DriverManager.getConnection(jdbcPath, user, pass);
                            sta = conn.createStatement();
                            String sql = "select password from security where username = \'" + username +  "\' and password = " + password;
                            ResultSet res = sta.executeQuery(sql);
                            if (res.next()) {
                              new Main();
                            }else{
                                JOptionPane.showMessageDialog(null, "用户验证失败", "警告", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException e1) {
                            JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.ERROR_MESSAGE);
                            e1.printStackTrace();
                        }
                    } catch (ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.ERROR_MESSAGE);
                    }


                }
            }
        });
     setVisible(true);
    }
    public static void main(String args[]){
        new Login();
    }

}
