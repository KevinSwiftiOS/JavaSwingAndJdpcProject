package com.kevin.Main;
import com.kevin.MySQL.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

//处理记录面板
@SuppressWarnings("serial")
public class FirstPanel extends JPanel{
    private JLabel title;
    private JPanel titlePanel;
   private  String titles[] = {"学号","姓名","性别","语文","数学","英语"};
    private String keys[] = {"stuId","name","gender","chinese","math","english"};
    private String objects[][]= null;
    private JTable table = null;
    private JScrollPane scrollPane = null;
    private  JPanel centerPanel = new JPanel();
    //中间的textField
    private JLabel stuIdLabel = new JLabel("学号");
    private JLabel nameLabel = new JLabel("姓名");
    private JLabel genderLabel = new JLabel("性别");
    private JLabel chineseLabel = new JLabel("语文");
    private JLabel mathLabel = new JLabel("数学");
    private JLabel englishLabel = new JLabel("英语");
    private  JTextField stuTextField = new JTextField();
    private  JTextField nameTextField = new JTextField();
    private  JTextField genderTextField = new JTextField();
    private  JTextField chineseTextField = new JTextField();
    private  JTextField mathTextField = new JTextField();
    private  JTextField englishTextField = new JTextField();
    private  JPanel editPanel = new JPanel();
    //南部的按钮
    private JButton insertBtn = new JButton("添加");
    private  JButton updateBtn = new JButton("修改");
    private  JButton deleteBtn = new JButton("删除");
    private  JPanel southPanel = new JPanel();


    private  JDBCMySQL jdbcMySQL = new JDBCMySQL();
    private  int i = 0,j = 0;
    private  int totalRows = 0;
    //被选中的行数
    private  int selectRows = 0;
    public FirstPanel(){
        // 创建标题面板
        titlePanel = new JPanel();

        // 设置标题面板的大小
        titlePanel.setPreferredSize(new Dimension(600, 140));

        // 设置标题面板上下左右的边距
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // 设置标题的字体及大小
        title = new JLabel("学生信息", SwingConstants.CENTER);
        title.setFont(new Font("宋体", Font.BOLD, 28));

        // 把标题加入标题面板
        titlePanel.add(title);
        //进行数据库的链接初始化数组
        jdbcMySQL.getConnection();
        ResultSet res = jdbcMySQL.query("select * from studentInfo");
       try{
       res.last();
           totalRows = res.getRow();
           objects = new String[totalRows][6];
           res.beforeFirst();

           while (res.next()){
               for( j = 0; j < 6;j++)
                   objects[i][j] = res.getString(keys[j]);
               i++;
           }
           DefaultTableModel model = new DefaultTableModel(objects,titles);
           table = new JTable(model);
           table.setPreferredScrollableViewportSize(new Dimension(660, 170));
           table.setRowHeight(30);
           scrollPane = new JScrollPane(table);

           editPanel.add(stuIdLabel);
           stuTextField.setColumns(8);
           editPanel.add(stuTextField);

           editPanel.add(nameLabel);
           nameTextField.setColumns(8);
           editPanel.add(nameTextField);

           editPanel.add(genderLabel);
           genderTextField.setColumns(8);
           editPanel.add(genderTextField);

           editPanel.add(chineseLabel);
           chineseTextField.setColumns(8);
           editPanel.add(chineseTextField);

           editPanel.add(mathLabel);
           mathTextField.setColumns(8);
           editPanel.add(mathTextField);

           editPanel.add(englishLabel);
           englishTextField.setColumns(8);
           editPanel.add(englishTextField);

//
         centerPanel.setLayout(new BorderLayout());
           centerPanel.add(scrollPane);
          centerPanel.add(editPanel,BorderLayout.SOUTH);
           //南部面板
       // southPanel.add(editPanel);
           southPanel.add(insertBtn);
           southPanel.add(updateBtn);
           southPanel.add(deleteBtn);


           // 把标题面板加入first panel面板
           this.add(titlePanel, BorderLayout.NORTH);
           this.add(centerPanel,BorderLayout.CENTER);
           this.add(southPanel,BorderLayout.SOUTH);

//table点击某一行
           table.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   super.mouseClicked(e);
                   if(table.getSelectedRowCount()==1) {
                       int row = table.getSelectedRow();
                       selectRows = row;
                      //获取该行的数据 并且显示在textField上
                       stuTextField.setText(objects[row][0]);
                       nameTextField.setText(objects[row][1]);
                       genderTextField.setText(objects[row][2]);
                       chineseTextField.setText(objects[row][3]);
                       mathTextField.setText(objects[row][4]);
                       englishTextField.setText(objects[row][5]);
                   }
               }
           });
           insertBtn.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   //插入语句 判断数据的有效性

                    if(check()) {
                        String stuId = stuTextField.getText().toString();
                        String name = nameTextField.getText().toString();
                        String gender = genderTextField.getText().toString();
                        Float chinese = Float.valueOf(chineseTextField.getText().toString());
                        Float math = Float.valueOf(mathTextField.getText().toString());
                        Float english = Float.valueOf(englishTextField.getText().toString());
                        String sql = "insert into studentInfo values (\'" + stuId  + "\',\'" + name + "\',\'" + gender + "\'," + chinese + "," + math + "," + english + ")";

                        if(jdbcMySQL.deleteOrInsert(sql) > 0) {
                            JOptionPane.showMessageDialog(null, "添加成功", "恭喜您", JOptionPane.INFORMATION_MESSAGE);
                        //插入行
                            String  newobjects[][] = objects;
                           int tempRow = totalRows;
                            totalRows += 1;

                            objects = new String[totalRows][6];
                          for(i = 0 ; i < tempRow;i++)
                              for (j = 0; j < 6;j++)
                                  objects[i][j] = newobjects[i][j];

                            objects[totalRows - 1][0] = stuId;
                            objects[totalRows - 1][1] = name;
                            objects[totalRows - 1][2] = gender;
                            objects[totalRows - 1][3] = String.valueOf(chinese);
                            objects[totalRows - 1][4] = String.valueOf(math);
                            objects[totalRows - 1][5] = String.valueOf(english);
                            model.setDataVector(objects,titles);
                        }
                        else
                            JOptionPane.showMessageDialog(null, "添加失败", "警告", JOptionPane.WARNING_MESSAGE);
                    }
               }
           });
         //跟新某行
           updateBtn.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   if(check()){
                       String stuId = stuTextField.getText().toString();
                       String name = nameTextField.getText().toString();
                       String gender = genderTextField.getText().toString();
                       Float chinese = Float.valueOf(chineseTextField.getText().toString());
                       Float math = Float.valueOf(mathTextField.getText().toString());
                       Float english = Float.valueOf(englishTextField.getText().toString());
                   String sql = "UPDATE studentInfo SET stuId = \'" + stuId + "\',name=\'" + name + "\',gender=\'" + gender + "\',chinese=" + String.valueOf(chinese)
                           + ",math=" + String.valueOf(math) + ",english=" + String.valueOf(english) + " where stuId = \'" + stuId + "\'";
                       if(jdbcMySQL.update(sql) > 0) {
                           JOptionPane.showMessageDialog(null, "修改成功", "恭喜您", JOptionPane.INFORMATION_MESSAGE);
                           objects[selectRows][0] = stuId;
                           objects[selectRows][1] = name;
                           objects[selectRows][2] = gender;
                           objects[selectRows][3] = String.valueOf(chinese);
                           objects[selectRows][4] = String.valueOf(math);
                           objects[selectRows][5] = String.valueOf(english);
                           model.setDataVector(objects,titles);

                       }else
                           JOptionPane.showMessageDialog(null, "修改失败", "警告", JOptionPane.WARNING_MESSAGE);


                   }
               }
           });
           //删除某行
           deleteBtn.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   //向前移动一行
                   String stuId = stuTextField.getText().toString();
                   String sql = "delete from studentInfo where stuId = \'" + stuId + "\'";
                   if(jdbcMySQL.deleteOrInsert(sql)> 0){
                       JOptionPane.showMessageDialog(null, "删除成功", "恭喜您", JOptionPane.INFORMATION_MESSAGE);
                       //前几行和他一样
                       String newobject[][] = new String[totalRows - 1][6];
                       for(i = 0; i < selectRows;i++)
                           for(j = 0; j < 6;j++)
                               newobject[i][j] = objects[i][j];
                       //向前移动一行
                       for( i = 0; i < 6;i++) {
                           for(j = selectRows; j < totalRows - 1;j++)
                               newobject[j][i] = objects[j+1][i];
                       }
                        totalRows--;
                         objects = new String[totalRows][6];
                       for(int i = 0; i < totalRows;i++)
                           for(int j = 0 ; j < 6;j++) {
                               objects[i][j] = newobject[i][j];

                       }

                        model.setDataVector(objects,titles);
                   }else{
                       JOptionPane.showMessageDialog(null, "删除失败", "警告", JOptionPane.WARNING_MESSAGE);
                   }
               }
           });
       }catch (SQLException e){
           e.printStackTrace();
       }











    }
    //检查有效性
    public  boolean check() {
        String gender = genderTextField.getText().toString();

        Float chinese,math,english;
        if (chineseTextField.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "语文成绩未填", "警告", JOptionPane.WARNING_MESSAGE);
            return  false;
        } else if (chineseTextField.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "数学成绩未填", "警告", JOptionPane.WARNING_MESSAGE);
            return  false;
        } else if (chineseTextField.getText().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "英语成绩未填", "警告", JOptionPane.WARNING_MESSAGE);
            return  false;
        } else {
            try {
                chinese = Float.valueOf(chineseTextField.getText().toString());
                math = Float.valueOf(mathTextField.getText().toString());
                english = Float.valueOf(englishTextField.getText().toString());

                if (!(gender.equals("male") || gender.equals("female"))) {
                    JOptionPane.showMessageDialog(null, "性别填写不正确", "警告", JOptionPane.WARNING_MESSAGE);
                    return false;
                } else if (chinese < 0 || chinese > 100) {
                    JOptionPane.showMessageDialog(null, "语文成绩填写不正确", "警告", JOptionPane.WARNING_MESSAGE);
                    return false;
                } else if (math < 0 || math > 100) {
                    JOptionPane.showMessageDialog(null, "数学成绩填写不正确", "警告", JOptionPane.WARNING_MESSAGE);
                    return false;
                } else if (english < 0 || english > 100) {
                    JOptionPane.showMessageDialog(null, "英语成绩填写不正确", "警告", JOptionPane.WARNING_MESSAGE);
                    return false;
                } else {
                    return true;
                }
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "成绩填写格式不正确", "警告", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
                return  false;
            }
        }
    }
}
