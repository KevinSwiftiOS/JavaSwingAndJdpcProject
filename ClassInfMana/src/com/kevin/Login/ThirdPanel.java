package com.kevin.Login;

/**
 * Created by hcnucai on 2016/12/10.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
//重置密码面板
public class ThirdPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel;
    //旧密码
    private  JLabel oldPassLabel = new JLabel("请输入旧密码:");
    //新密码 要输两次
    private JLabel newPassLabel = new JLabel("请输入新密码:");
    private JLabel configNewLabel = new JLabel("再次输入新密码:");
    //三个输入框
    private JPasswordField oldTextField = new JPasswordField();
    private JPasswordField newTextField = new JPasswordField();
    private  JPasswordField configTextField = new JPasswordField();
    private  JPanel oldPanel,newPanel,configPanel,btnPanel;

    private  JPanel centerPanel = new JPanel();
    private JButton configBtn = new JButton("确定");


    public ThirdPanel(){
        this.setLayout(new BorderLayout());
        // 创建标题面板
        titlePanel = new JPanel();

        // 设置标题面板的大小
        titlePanel.setPreferredSize(new Dimension(600, 140));

        // 设置标题面板上下左右的边距
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // 设置标题的字体及大小
        title = new JLabel("重置密码", SwingConstants.CENTER);
        title.setFont(new Font("宋体", Font.BOLD, 28));

        // 把标题加入标题面板
        titlePanel.add(title);

        // 把标题面板加入first panel面板
        this.add(titlePanel, BorderLayout.NORTH);
        centerPanel.setLayout(new GridLayout(4,1,-5,-5));

        oldPanel = new JPanel();
        oldPanel.setLayout(new FlowLayout());
        FlowLayout oldLayout = (FlowLayout)oldPanel.getLayout();
        oldLayout.setAlignment(FlowLayout.CENTER);
        oldPanel.add(oldPassLabel);
        oldTextField.setColumns(18);
        oldPanel.add(oldTextField);

        newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());
        FlowLayout newLayout = (FlowLayout)newPanel.getLayout();
        newLayout.setAlignment(FlowLayout.CENTER);
        newPanel.add(newPassLabel);
        newTextField.setColumns(18);
        newPanel.add(newTextField);

        configPanel = new JPanel();
        configPanel.setLayout(new FlowLayout());
        FlowLayout configLayout = (FlowLayout)configPanel.getLayout();
        configLayout.setAlignment(FlowLayout.CENTER);
        configPanel.add(configNewLabel);
        configTextField.setColumns(18);
        configPanel.add(configTextField);

        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        FlowLayout btnPanelLayout = (FlowLayout)btnPanel.getLayout();
        btnPanelLayout.setAlignment(FlowLayout.CENTER);
        btnPanel.add(configBtn);

       centerPanel.add(oldPanel);
        centerPanel.add(newPanel);
        centerPanel.add(configPanel);
         centerPanel.add(btnPanel);
        this.add(centerPanel,BorderLayout.CENTER);
//点击事件
        configBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("222");
            }
        });


    }
}
