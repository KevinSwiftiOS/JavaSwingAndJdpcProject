package  com.kevin.Login;
import com.kevin.Login.FirstPanel;
import com.kevin.Login.SecondPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    //统计菜单的按钮 学生人数 平均分item 最高分item  最低分item
   private  JMenuItem stuNum,avgItem,highItem,lowItem;
    //外观菜单的item  3个风格的item
  private  JRadioButtonMenuItem  metalItem,metifItem,windowItem;

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
        MenuItemListener itemListener = new MenuItemListener();
        fromFileItem.addActionListener(itemListener);
        toFileItem.addActionListener(itemListener);

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

}
//创建系统外观菜单
public void buildPreMenu() {
    preMenu = new JMenu("系统外观");
    metalItem = new JRadioButtonMenuItem("Metal风格");
    metifItem = new JRadioButtonMenuItem("Metif风格");
    windowItem = new JRadioButtonMenuItem("Windows风格");
    metalItem.setSelected(true);
    preMenu.add(metalItem);
    preMenu.add(metifItem);
    preMenu.add(windowItem);

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
    private class MenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ex) {
           System.out.println("click");
        }

    }

    public static void main(String[] args) {
      new Main();
    }

}
