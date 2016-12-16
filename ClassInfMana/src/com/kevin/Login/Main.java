package  com.kevin.Login;
import com.kevin.Login.FirstPanel;
import com.kevin.Login.SecondPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;
//主界面
public class Main extends JFrame {
    private static final long serialVersionUID = 1L;
    private int WINDOW_WIDTH = 1000;
    private int WINDOW_HEIGHT = 600;
    //左侧的导航栏
    private JPanel navigatorPanel;
    //三个面板
    private SecondPanel secondPanel;
    private FirstPanel firstPanel;
    private ThirdPanel thirdPanel;
    //设置三个label 为nav所有 分别为处理记录  查询记录 重置密码
    private JLabel firstLabel, secondLabel,thirdLabel;
    //设置上方的菜单栏
    private  JMenuBar menuBar;
    //文件菜单
    private  JMenu fileMenu;
    //统计菜单 还有一个子菜单 分数菜单
    private  JMenu cntMenu,scoreMenu;
    //外观菜单
    private JMenu preMenu;
    //文件菜单的三个item  分别为从文件导入 导出到文件 退出系统
    private JMenuItem fromFileItem,toFileItem,exitItem;
    private JPanel processPanel;
    private  JProgressBar progressBar;
    //统计菜单的按钮 学生人数 平均分item 最高分item  最低分item
   private  JMenuItem stuNum,avgItem,highItem,lowItem;
    //外观菜单的item  3个风格的item
  private  JRadioButtonMenuItem  metalItem,metifItem,windowItem;
//导入导出时候的文件的
    private File selectedFile;
    private  String fileName;
    public Main(){
        //设置一些默认值
        // set title
        this.setTitle("班级信息管理");

        // set size for the form
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // set layout for the frame
        this.setLayout(new BorderLayout(10, 10));

        // specify the operation for the close button
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // build navigator panel
        buildNavigatorPanel();

        // build first panel and second panel
        // 把要显示的两个面板对象都建立起来
        buildOtherPanels();

        // add the created panels to the frame
        //加载panel
        this.add(navigatorPanel, BorderLayout.WEST);

        // 把要显示的两个面板对象到放到相同区域，在窗体中显示的为最后放入的面板。
        // 这样做的目的是把两个对象都和该窗体关联起来。只有关联起来，之后应用外观风格（例如Motif）才会作用于这两个面板对象。
        this.add(secondPanel, BorderLayout.CENTER);
        this.add(firstPanel, BorderLayout.CENTER);
        this.add(thirdPanel,BorderLayout.CENTER);
        // show the window
        //progressBar的使用
        processPanel = new JPanel();
        progressBar = new JProgressBar(JProgressBar.HORIZONTAL,0,100);
        progressBar.setPreferredSize(new Dimension(400,30));
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
       processPanel.add(progressBar);
       processPanel.setVisible(false);
        this.add(processPanel);


        this.setVisible(true);

    }


    public void buildNavigatorPanel() {
        // create a panel for navigator labels
        navigatorPanel = new JPanel();

        // 设置边框来控制外观以及上下左右边距
        Border insideBorder = BorderFactory.createEmptyBorder(20, 0, 0, 0);
        Border outsideBorder = BorderFactory.createLoweredBevelBorder();
        navigatorPanel.setBorder(BorderFactory.createCompoundBorder(
                outsideBorder, insideBorder));

        // set the size for the navigator panel
        navigatorPanel.setPreferredSize(new Dimension(100, 500));
        //3个label的创建
        // create label objects for navigator
        firstLabel = new JLabel("处理记录", SwingConstants.CENTER);
        firstLabel.setPreferredSize(new Dimension(100, 30));
        firstLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 当鼠标移到标签上时显示手型图标

        secondLabel = new JLabel("查询记录", SwingConstants.CENTER);
        secondLabel.setPreferredSize(new Dimension(100, 30));
        secondLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 当鼠标移到标签上时显示手型图标

        thirdLabel = new JLabel("重置密码", SwingConstants.CENTER);
        thirdLabel.setPreferredSize(new Dimension(100, 30));
        thirdLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 当鼠标移到标签上时显示手型图标

        // add the labels to the panel
        navigatorPanel.add(firstLabel);
        navigatorPanel.add(secondLabel);
        navigatorPanel.add(thirdLabel);

        // register action listener for the navigator labels
        firstLabel.addMouseListener(new LabelClick());
        secondLabel.addMouseListener(new LabelClick());
        thirdLabel.addMouseListener(new LabelClick());
    }

    public void buildOtherPanels() {
        // 下面创建两个面板对象，它们将会显示在同一个区域，但同一时刻只有一个可见
        secondPanel = new SecondPanel();
        secondPanel.setVisible(false);

        firstPanel = new FirstPanel();
        firstPanel.setVisible(true);

        thirdPanel = new ThirdPanel();
        thirdPanel.setVisible(false);
        //加载菜单栏
        menuBar = new JMenuBar();
        //创建文件菜单
        buildFileMenu();
        //创建统计菜单
        buildCntMenu();
        //创建系统外观菜单
        buildPreMenu();
        //加载
        menuBar.add(fileMenu);
        menuBar.add(cntMenu);
        menuBar.add(preMenu);
        setJMenuBar(menuBar);
        //各个item点击的事件
        FileMenuItemListener itemListener = new FileMenuItemListener();
        fromFileItem.addActionListener(itemListener);
        toFileItem.addActionListener(itemListener);
         exitItem.addActionListener(itemListener);
    }
    //文件菜单创建
public  void  buildFileMenu() {
    fileMenu = new JMenu("文件");
    fromFileItem = new JMenuItem("从文件导入");
    toFileItem = new JMenuItem("导出到文件");
    exitItem = new JMenuItem("退出系统");
    fileMenu.add(fromFileItem);
    fileMenu.add(toFileItem);
    //separator bar的使用
    fileMenu.addSeparator();
    fileMenu.add(exitItem);
    //退出系统
    exitItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

}
//统计菜单创建
public  void buildCntMenu() {
    cntMenu = new JMenu("统计");
    buildScoreMenu();
   stuNum = new JMenuItem("学生总人数");
    buildScoreMenu();
    cntMenu.add(scoreMenu);
    cntMenu.addSeparator();
    cntMenu.add(stuNum);
    CntMenuItemListener itemListener = new CntMenuItemListener();
    stuNum.addActionListener(itemListener);
}
//统计菜单子菜单下的score菜单的创建
public  void  buildScoreMenu() {
    scoreMenu = new JMenu("各科成绩");
    avgItem = new JMenuItem("各门课程平均分");
    highItem = new JMenuItem("各门课程最高分");
    lowItem = new JMenuItem("各门课程最低分");
    ButtonGroup group = new ButtonGroup();
    group.add(avgItem);
    group.add(highItem);
    group.add(lowItem);
    scoreMenu.add(avgItem);
    scoreMenu.add(highItem);
    scoreMenu.add(lowItem);
    CntMenuItemListener itemListener = new CntMenuItemListener();
    avgItem.addActionListener(itemListener);
   highItem.addActionListener(itemListener);
    lowItem.addActionListener(itemListener);
}
//创建系统外观菜单
public void buildPreMenu() {
    preMenu = new JMenu("系统外观");
    metalItem = new JRadioButtonMenuItem("Metal风格");
    metifItem = new JRadioButtonMenuItem("Metif风格");
    windowItem = new JRadioButtonMenuItem("Windows风格");
    ButtonGroup group = new ButtonGroup();
    group.add(metalItem);
    group.add(metifItem);
    group.add(windowItem);
    metalItem.setSelected(true);
    preMenu.add(metalItem);
    preMenu.add(metifItem);
    preMenu.add(windowItem);
    PreMenuItemListener itemListener = new PreMenuItemListener();
    metifItem.addActionListener(itemListener);
    metalItem.addActionListener(itemListener);
    windowItem.addActionListener(itemListener);

}
//三个label的点击事件 切换不同的panel
    public class LabelClick extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == firstLabel) {
                // the label clicked is set to red, other labels are black
                firstLabel.setForeground(Color.red);
                secondLabel.setForeground(Color.black);
                thirdLabel.setForeground(Color.black);
                // show the first panel
                // 把要显示的面板放入指定区域
                if (!firstPanel.isVisible()) {
                    Main.this.add(firstPanel, BorderLayout.CENTER);
                    firstPanel.setVisible(true);
                }
                // 原先显示的面板设置为不可见
                secondPanel.setVisible(false);
                thirdPanel.setVisible(false);
            } else if (e.getSource() == secondLabel) {
                // the label clicked is set to red, other labels are black
                secondLabel.setForeground(Color.red);
                firstLabel.setForeground(Color.black);
                thirdLabel.setForeground(Color.black);
                // show the second panel
                if (!secondPanel.isVisible()) {
                    Main.this.add(secondPanel, BorderLayout.CENTER);
                    secondPanel.setVisible(true);
                }
                firstPanel.setVisible(false);
                thirdPanel.setVisible(false);
            }else if(e.getSource() == thirdLabel){
                // the label clicked is set to red, other labels are black
                thirdLabel.setForeground(Color.red);
                firstLabel.setForeground(Color.black);
                secondLabel.setForeground(Color.black);
                // show the second panel
                if (!thirdPanel.isVisible()) {
                    Main.this.add(thirdPanel, BorderLayout.CENTER);
                    thirdPanel.setVisible(true);
                }
                firstPanel.setVisible(false);
                secondPanel.setVisible(false);
            }
        }
    }
    //文件菜单栏下的item的点击事件
    private  class FileMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ex) {
            if(ex.getSource() == fromFileItem){
            //打开文件选择器
                JFileChooser fileChooser = new JFileChooser();
                //显示打开文件对话框
                int status = fileChooser.showOpenDialog(null);
                if(status == JFileChooser.APPROVE_OPTION){
                    //获取用户所选择的文件对象
                     selectedFile = fileChooser.getSelectedFile();
                    //获取用户所选择的文件的路径
                     fileName = selectedFile.getPath();
                    //显示该路径
                 //创建线程 开始启动
                    Thread fromFileThread = new Thread(new FileOperate());
                    fromFileThread.start();

                }
            }
            if(ex.getSource() == toFileItem){
                //打开保存到文件的对话框
             JFileChooser fileChooser = new JFileChooser();
                int status = fileChooser.showSaveDialog(null);
                if(status == JFileChooser.APPROVE_OPTION){
                    //获取用户所选择的文件对象
                    File selectedFile = fileChooser.getSelectedFile();
                    //获取用户所选择的文件的路径
                    String filename = selectedFile.getPath();
                    //显示该路径
                    JOptionPane.showMessageDialog(null,"you select " + filename);
                }
                if(status == JFileChooser.CANCEL_OPTION){
                    System.out.println("cancel");
                }
            }
            if(ex.getSource() == exitItem){
                System.exit(0);
            }

            System.out.println("click");
        }
    }
  //统计菜单栏下的item的点击事件
    private  class CntMenuItemListener  implements  ActionListener{
      @Override
      public void actionPerformed(ActionEvent ex) {
          JDBCMySQL jdbcMySQL = new JDBCMySQL();
          jdbcMySQL.getConnection();
          if(ex.getSource() == stuNum){
            //获取学生总人数
           String sql = "select * from studentInfo";
         ResultSet res =  jdbcMySQL.query(sql);
              //人数
              try {
                  res.last();
                  String stuNumStr = "学生人数:" + res.getRow();
                  JOptionPane.showMessageDialog(null, stuNumStr, "学生人数", JOptionPane.WARNING_MESSAGE);
              }catch (SQLException e){
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.WARNING_MESSAGE);
              }
          }
          if(ex.getSource() == avgItem){
               //查询成绩
              String chineseAvgSql = "select avg(chinese) as avg from studentInfo";
              String mathAvgSql = "select avg(math) as avg from studentInfo";
              String englishAvgSql = "select avg(english) as avg from studentInfo";
              ResultSet chineseRes   =   jdbcMySQL.query(chineseAvgSql);
              ResultSet mathRes   =   jdbcMySQL.query(mathAvgSql);
              ResultSet englishRes   =   jdbcMySQL.query(englishAvgSql);
              String chinese = "语文:",math = "数学:",english = "英语:";
             try {
                 java.text.DecimalFormat   df = new   java.text.DecimalFormat("#.##");
                 //保留两位小数
                 if (chineseRes.next()) {
                  chinese +=  df.format(chineseRes.getFloat("avg"));
                 }
                 if (mathRes.next()) {
                     math +=  df.format(mathRes.getFloat("avg"));
                 }
                 if (englishRes.next()) {
                     english +=  df.format(englishRes.getFloat("avg"));
                 }
                 JOptionPane.showMessageDialog(null, chinese + "\n" + math + "\n" + english, "各科平均分", JOptionPane.WARNING_MESSAGE);
             }catch (SQLException e){
                 JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.WARNING_MESSAGE);
                 e.printStackTrace();
             }
          }
          if(ex.getSource() == highItem){
                //查询最高分
              //查询成绩
              String chineseHighSql = "select max(chinese) as max from studentInfo";
              String mathHighSql = "select max(math) as max from studentInfo";
              String englishHighSql = "select max(english) as max from studentInfo";
              ResultSet chineseRes   =   jdbcMySQL.query(chineseHighSql);
              ResultSet mathRes   =   jdbcMySQL.query(mathHighSql);
              ResultSet englishRes   =   jdbcMySQL.query(englishHighSql);
              String chinese = "语文:",math = "数学:",english = "英语:";
              try {
                  java.text.DecimalFormat   df = new   java.text.DecimalFormat("#.##");
                  //保留两位小数
                  if (chineseRes.next()) {
                      chinese +=  df.format(chineseRes.getFloat("max"));
                  }
                  if (mathRes.next()) {
                      math +=  df.format(mathRes.getFloat("max"));
                  }
                  if (englishRes.next()) {
                      english +=  df.format(englishRes.getFloat("max"));
                  }
                  JOptionPane.showMessageDialog(null, chinese + "\n" + math + "\n" + english, "各科最高分", JOptionPane.WARNING_MESSAGE);
              }catch (SQLException e){
                  JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.WARNING_MESSAGE);
                  e.printStackTrace();
              }
          }
          if(ex.getSource() == lowItem){
              //查询最低分
              //查询成绩
              String chineseLowSql = "select min(chinese) as min from studentInfo";
              String mathLowSql = "select min(math) as min from studentInfo";
              String englishLowSql = "select min(english) as min from studentInfo";
              ResultSet chineseRes   =   jdbcMySQL.query(chineseLowSql);
              ResultSet mathRes   =   jdbcMySQL.query(mathLowSql);
              ResultSet englishRes   =   jdbcMySQL.query(englishLowSql);
              String chinese = "语文:",math = "数学:",english = "英语:";
              try {
                  java.text.DecimalFormat   df = new   java.text.DecimalFormat("#.##");
                  //保留两位小数
                  if (chineseRes.next()) {
                      chinese +=  df.format(chineseRes.getFloat("min"));
                  }
                  if (mathRes.next()) {
                      math +=  df.format(mathRes.getFloat("min"));
                  }
                  if (englishRes.next()) {
                      english +=  df.format(englishRes.getFloat("min"));
                  }
                  JOptionPane.showMessageDialog(null, chinese + "\n" + math + "\n" + english, "各科最低分", JOptionPane.WARNING_MESSAGE);
              }catch (SQLException e){
                  JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.WARNING_MESSAGE);
                  e.printStackTrace();
              }
          }

      }
  }
    //外观菜单栏下的item的点击事件
    private  class PreMenuItemListener  implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent ex) {
            if(ex.getSource() == metalItem){
                //设置窗体的风格
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(navigatorPanel);
                    SwingUtilities.updateComponentTreeUI(menuBar);
                    SwingUtilities.updateComponentTreeUI(firstPanel);
                    SwingUtilities.updateComponentTreeUI(secondPanel);
                    SwingUtilities.updateComponentTreeUI(thirdPanel);

                } catch(Exception e){
                    JOptionPane.showMessageDialog(null,
                            "Error setting the look and feel.");
                    System.exit(0);

                }
            }
            if(ex.getSource() == metifItem) {
                try {
         UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(navigatorPanel);
                    SwingUtilities.updateComponentTreeUI(menuBar);
                    SwingUtilities.updateComponentTreeUI(firstPanel);
                    SwingUtilities.updateComponentTreeUI(secondPanel);
                    SwingUtilities.updateComponentTreeUI(thirdPanel);

                } catch(Exception e){
                    JOptionPane.showMessageDialog(null,
                            "Error setting the look and feel.");
                    System.exit(0);

                }
            }
            if(ex.getSource() == windowItem){
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(navigatorPanel);
                    SwingUtilities.updateComponentTreeUI(menuBar);
                    SwingUtilities.updateComponentTreeUI(firstPanel);
                    SwingUtilities.updateComponentTreeUI(secondPanel);
                    SwingUtilities.updateComponentTreeUI(thirdPanel);

                } catch(Exception e){
                    JOptionPane.showMessageDialog(null,
                            "Error setting the look and feel.");
                    System.exit(0);

                }
            }

            System.out.println("clickPre");
        }
    }
    //创建线程来读入文件
    private  class FileOperate implements Runnable{
        @Override
        public void run() {
     if(fileName.length() == 0){
         JOptionPane.showMessageDialog(null,"没有选择文件夹","警告",JOptionPane.WARNING_MESSAGE);

     }else{
         //看文件是否选择
         if(selectedFile.length() == 0){
             progressBar.setMaximum(100);
             progressBar.setValue(100);
         }else{
             progressBar.setMaximum((int)selectedFile.length());
         }
         try {
             processPanel.setVisible(true);
             boolean isFinished = false;
             FileInputStream fin = new FileInputStream(selectedFile);
             System.out.print(selectedFile.length());
             InputStreamReader reader = new InputStreamReader(fin,"utf-8"); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
             BufferedReader br = new BufferedReader(reader);
             int count = 0, ans;
             byte[] buff = new byte[1024];
             while ((ans = fin.read(buff)) > 0) {
                 count += ans;
                 progressBar.setValue(count);
                 String str = new String(buff,0,ans,"UTF-8");
                  System.out.print(str);

                 try {
                     Thread.sleep(1);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }

             isFinished = true;
             if(isFinished){
               //  processPanel.setVisible(false);
                 JOptionPane.showMessageDialog(null,"导入成功","成功",JOptionPane.INFORMATION_MESSAGE);
             }
         }catch (IOException e){
             e.printStackTrace();
         }
     }
        }
    }



    public static void main(String[] args) {
      new Main();
    }

}
