package com.kevin.Main;

/**
 * Created by hcnucai on 2016/12/10.
 */
import com.kevin.MySQL.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

@SuppressWarnings("serial")
//查询记录面板
public class SecondPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel;
    //文本查询框
    private TextArea textArea = new TextArea();
    private JButton button = new JButton("查询");
    private JPanel centerPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private  JScrollPane scrollPane = null;
    private JPanel southPanel = new JPanel();
    private String titles[] = {"学号","姓名","性别","语文","数学","英语"};
    private  String objects[][] = null;
    public SecondPanel(){
        // 创建标题面板
        titlePanel = new JPanel();
        // 设置标题面板的大小
        titlePanel.setPreferredSize(new Dimension(600, 140));
        // 设置标题面板上下左右的边距
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        // 设置标题的字体及大小
        title = new JLabel("输入查询条件", SwingConstants.CENTER);
        title.setFont(new Font("宋体", Font.BOLD, 28));
        // 把标题加入标题面板
        titlePanel.add(title);
       //中间面板的布局
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(textArea,BorderLayout.CENTER);
        buttonPanel.add(button);
        centerPanel.add(buttonPanel,BorderLayout.SOUTH);
        //南部面板
        objects = new String[4][6];
        DefaultTableModel model = new DefaultTableModel(objects,titles);
        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(660, 120));
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        southPanel.setLayout(new BorderLayout());
        southPanel.add(scrollPane,BorderLayout.CENTER);
        // 把标题面板加入first panel面板
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(centerPanel,BorderLayout.CENTER);
        this.add(southPanel,BorderLayout.SOUTH);
        //添加动作进行数据库的查询
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //进行数据库的查询
                JDBCMySQL jdbcMySQL = new JDBCMySQL();
                jdbcMySQL.getConnection();
                ResultSet res = jdbcMySQL.query(textArea.getText().toString());
                if(res == null)
                    JOptionPane.showMessageDialog(null, "查询失败", "警告", JOptionPane.WARNING_MESSAGE);
             else   try {
                   java.text.DecimalFormat   df = new   java.text.DecimalFormat("#.##");
                   String keys[] = {"stuId","name","gender","chinese","math","english"};
                   //用Map进行初始化
                    HashMap<String,Boolean> map = new HashMap<String, Boolean>();
                    int cnt = 0;
                    map.put("stuId",false);
                    map.put("name",false);
                    map.put("gender",false);
                    map.put("chinese",false);
                    map.put("math",false);
                    map.put("english",false);
                    ResultSetMetaData metaData = res.getMetaData();
                    for(int i = 1; i <= metaData.getColumnCount();i++)
                        map.put(metaData.getColumnName(i),true);
                    int i = 0,j = 0;
                    //object数组
                    res.last();
                    objects = new String[res.getRow()][6];
                    res.beforeFirst();
                    while (res.next()) {
                        for( j = 0; j < 6;j++) {
                        if (j < 3) {
                                if(map.get(keys[j]))
                            objects[i][j] = res.getString(keys[j]);
                            else
                                objects[i][j] = null;
                        } else {
                            if(map.get(keys[j]))
                            objects[i][j] = df.format(res.getFloat(keys[j]));
                          else
                              objects[i][j] = null;
                        }
                    }
                        i++;
                   }
                   model.setDataVector(objects,titles);
               }catch (SQLException e1){
                   e1.printStackTrace();
                   JOptionPane.showMessageDialog(null, "数据库链接失败", "警告", JOptionPane.WARNING_MESSAGE);
               }

            }
        });
    }
}
