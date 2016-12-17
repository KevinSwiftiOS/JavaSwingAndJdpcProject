package com.kevin.File;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.kevin.MySQL.*;

//创建线程读出文件
public class FileOutOperate extends JFrame implements Runnable{
    //创建面板
    private static final long serialVersionUID = 1L;
    private int WINDOW_WIDTH = 300;
    private int WINDOW_HEIGHT = 100;
    private JPanel processPanel;
    private  JProgressBar progressBar;
    String filePath;
    public FileOutOperate(String filePath){
        // set size for the form
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // set layout for the frame
        this.setLayout(new BorderLayout(10, 10));
        // specify the operation for the close button
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.filePath = filePath;
        processPanel = new JPanel();
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
        progressBar.setPreferredSize(new Dimension(400,30));
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
        processPanel.add(progressBar);

        this.add(processPanel);
        setVisible(true);
    }
    @Override
    public void run() {
        //首先读取数据库中的信息
        JDBCMySQL jdbcMySQL = new JDBCMySQL();
        jdbcMySQL.getConnection();
        ResultSet res = jdbcMySQL.query("select * from studentInfo");
        try {
            res.last();
            String objects[][] = new String[res.getRow()][6];
            res.beforeFirst();
            String keys[] = {"stuId","name","gender","chinese","math","english"};
            int i = 0,j = 0;
            while (res.next()){
                for(j = 0; j < 6;j++) {
                    objects[i][j] = res.getString(keys[j]);
                }
                i++;
            }
            //写出到文件
            res.last();
            try {
                FileOutputStream outputStream = new FileOutputStream(this.filePath);
                //进行写文件的操作
                progressBar.setValue(res.getRow() * 6);
                for ( i = 0; i < res.getRow(); i++) {
                    for (j = 0; j <  6; j++) {
                        //进度条
                        progressBar.setValue((i + 1) * (j + 1));
                        outputStream.write((String.valueOf(objects[i][j]) + " ").getBytes());
                    }
                    outputStream.write(String.valueOf("\n").getBytes());
                }
                this.setVisible(false);
                JOptionPane.showMessageDialog(null,"导出成功","恭喜您",JOptionPane.INFORMATION_MESSAGE);

                outputStream.close();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "导出失败", "警告", JOptionPane.WARNING_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "导出失败", "警告", JOptionPane.WARNING_MESSAGE);
            }

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "导出失败", "警告", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }

    }
}


