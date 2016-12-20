package com.kevin.File;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import com.kevin.MySQL.*;
/**
 * Created by hcnucai on 2016/12/17.
 */
public class FileInOperate extends JFrame implements Runnable {
    //面板大小和progressbar
    private static final long serialVersionUID = 1L;
    private int WINDOW_WIDTH = 300;
    private int WINDOW_HEIGHT = 100;
    private JPanel processPanel;
    private JProgressBar progressBar;
    String filePath;
    File selectedFile;
    public FileInOperate(File selectedFile) {
        this.selectedFile = selectedFile;
        this.filePath = selectedFile.getPath();
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // set layout for the frame
        this.setLayout(new BorderLayout(10, 10));
        // specify the operation for the close button
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        processPanel = new JPanel();
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        progressBar.setPreferredSize(new Dimension(400, 30));
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
        processPanel.add(progressBar);

        this.add(processPanel);
        setVisible(true);
    }
    @Override
    public void run() {
        if (filePath.length() == 0) {
            JOptionPane.showMessageDialog(null, "没有选择文件夹", "警告", JOptionPane.WARNING_MESSAGE);
        } else {
            //看文件是否选择
            if (selectedFile.length() == 0) {
                progressBar.setMaximum(100);
                progressBar.setValue(100);
                JOptionPane.showMessageDialog(null, "文件夹内容为空", "警告", JOptionPane.WARNING_MESSAGE);
            } else {
                progressBar.setMaximum((int) selectedFile.length());
                try {
                    FileInputStream inputStream = new FileInputStream(filePath);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    JDBCMySQL jdbcMysql = new JDBCMySQL();
                    jdbcMysql.getConnection();
                    String s;
                    //首先删除数据库
                    int res = jdbcMysql.deleteOrInsert("delete from studentInfo");
                    if(res >= 0) {
                        String buff = "";
                        int count = 0;
                        try {
                            while ((buff = bufferedReader.readLine()) != null) {
                                //readLine自动忽略换行 因此要+1
                                count += buff.length() + 1;
                                s = new String(buff);
                                String tempStr[] = s.split(" ");
                                if (tempStr.length == 6) {
                                    String sql = "insert into studentInfo values (\'" + tempStr[0] + "\',\'" + tempStr[1] + "\',\'" + tempStr[2] + "\'," + tempStr[3] + "," + tempStr[4] + "," + tempStr[5] + ");";
                                    if (jdbcMysql.deleteOrInsert(sql) == 0) {
                                        break;
                                    }
                                    progressBar.setValue(count);
                                }
                            }
                           if(count == selectedFile.length()) {
                               this.setVisible(false);
                               JOptionPane.showMessageDialog(null, "导入成功", "恭喜您", JOptionPane.INFORMATION_MESSAGE);
                           }
                           else{
                                JOptionPane.showMessageDialog(null, "导入失败", "警告", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "导入失败", "警告", JOptionPane.WARNING_MESSAGE);
                            e.printStackTrace();
                        }
                    }



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}


