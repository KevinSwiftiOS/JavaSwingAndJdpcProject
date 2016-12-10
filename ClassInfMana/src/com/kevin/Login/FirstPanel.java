package com.kevin.Login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//处理记录面板
@SuppressWarnings("serial")
public class FirstPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel;

    public FirstPanel(){
        // 创建标题面板
        titlePanel = new JPanel();

        // 设置标题面板的大小
        titlePanel.setPreferredSize(new Dimension(600, 140));

        // 设置标题面板上下左右的边距
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // 设置标题的字体及大小
        title = new JLabel("First Panel", SwingConstants.CENTER);
        title.setFont(new Font("宋体", Font.BOLD, 28));

        // 把标题加入标题面板
        titlePanel.add(title);

        // 把标题面板加入first panel面板
        this.add(titlePanel, BorderLayout.NORTH);
    }
}
