package ru.codebing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class App extends javax.swing.JFrame {


    public App() {
        initComponents();
    }
    //TODO: Глобальные переменные
    public String FSc;
    public String addId;
    public String addSem1;
    public String addSem2;

    public int maxSem1 = 0;
    public int maxSem2 = 0;
    public float maxMean = 0;

    public static int i = 0;


    //TODO: Arrays

        static String tempData[] = {null, null, null, null, null};
        static float tempMaxData[][] = {{0, 0, 0}, {0, 0, 0}};

        static String data[][] = new String[1024][5];
        static String columns[] = {"ФИО", "№Студ. Билета", "Cеместр №1", "Семестр №2", "Среднее"};

        static String dataMaxTable[][] = new String[2][3];
        static String columnsMaxTable[] = {"Семестр #1", "Семестр #2", "Средняя"};

        static String dataTop[][] = new String[3][2];
        static String columnsDataTop[] = {"ФИО", "№ Студ. Билета"};

    public void reloadMaxTable() {
        dataMaxTable[0][0] = tempMaxData[0][0] / i + "";
        dataMaxTable[0][1] = tempMaxData[0][1] / i + "";
        dataMaxTable[0][2] = tempMaxData[0][2] / i + "";

        dataMaxTable[1][0] = maxSem1+"";
        dataMaxTable[1][1] = maxSem2+"";
        dataMaxTable[1][2] = maxMean+"";

        DefaultTableModel maxTableMode = new DefaultTableModel(dataMaxTable, columnsMaxTable);
        maxTable.setModel(maxTableMode);

    }

    public void PerformanceTable(){
        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn2 = DriverManager.getConnection("jdbc:sqlite:F:/Jtable/tusur.db");
            String query2 = "SELECT * FROM tusur ORDER BY Amean DESC;";

            Statement stm = conn2.createStatement();
            ResultSet res = stm.executeQuery(query2);
            for (int j = 0; j < 3; j++) {
                res.next();
                dataTop[j][0] = res.getString("FSc");
                dataTop[j][1] = res.getString("ID");
                System.out.println(dataTop[j][0] + " " + dataTop[j][1]);
            }
            conn2.close();
            DefaultTableModel performanceTableModel = new DefaultTableModel(dataTop, columnsDataTop);
            tablePerformance.setModel(performanceTableModel);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void reloadTable() {
        try {
            Class.forName("org.sqlite.JDBC");

            Connection conn = DriverManager.getConnection("jdbc:sqlite:F:/Jtable/tusur.db");
            String query = "SELECT * FROM tusur;";

            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(query);

            while (res.next()) {
                int sem1 = res.getInt("Semestr1");
                int sem2 = res.getInt("Semestr2");
                float ArMean = ((float) sem1 + (float) sem2) / 2;

                data[i][0] = res.getString("FSc");
                data[i][1] = res.getInt("ID")+"";
                data[i][2] = sem1+"";
                tempMaxData[0][0]+= sem1;
                data[i][3] = sem2+"";
                tempMaxData[0][1]+= sem2;
                data[i][4] = ArMean+"";
                tempMaxData[0][2]+=ArMean;

                if(sem1>maxSem1){
                    maxSem1=sem1;
                }
                if(sem2>maxSem2){
                    maxSem2=sem2;
                }
                if(ArMean>maxMean){
                    maxMean=ArMean;
                }

                i++;
            }
           // data = (String[][])resizeArray(data, i); //resizeArray from i;

            DefaultTableModel dataTableMode = new DefaultTableModel(data, columns);
            dataTable.setModel(dataTableMode);

            countTitle.setText(Integer.toString(i));
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void inputDialog() {
        tempData[0] = JOptionPane.showInputDialog("ФИО ученика:", JOptionPane.QUESTION_MESSAGE);
        tempData[1] = JOptionPane.showInputDialog("№Студ. Билета:", JOptionPane.QUESTION_MESSAGE);
        tempData[2] = JOptionPane.showInputDialog("Оценка за 1 семестра", JOptionPane.QUESTION_MESSAGE);
        tempData[3] = JOptionPane.showInputDialog("Оценка за 2 семестра", JOptionPane.QUESTION_MESSAGE);
        tempData[4] = ((((float)Integer.parseInt(tempData[2]))+((float)Integer.parseInt(tempData[3])))/2.0)+"";
    }

    public void editFolder() {
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(discipline)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(titleDiscipline)
                                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(titleDiscipline)
                                        .addComponent(discipline))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(dataTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(count)
                                                .addGap(26, 26, 26)
                                                .addComponent(countTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(count)
                                        .addComponent(countTitle)
                                        .addComponent(addButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(24, Short.MAX_VALUE))
        );
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(titleName))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(Performance))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(maxTitle)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(titleName)
                                        .addComponent(Performance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(maxTitle)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
    }

    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        count = new javax.swing.JLabel();
        countTitle = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        titleDiscipline = new javax.swing.JLabel();
        discipline = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        maxTable = new javax.swing.JTable();
        titleName = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePerformance = new javax.swing.JTable();
        Performance = new javax.swing.JLabel();
        maxTitle = new javax.swing.JLabel();

        //TODO: txt name
        {
            titleName.setText("NTM v0.1");
            titleDiscipline.setText("Математическое моделирование ");
            discipline.setText("Дисциплина: ");
            addButton.setText("Добавить студентов");
            count.setText("Количество студентов:");
            countTitle.setText(Integer.toString(i));
            Performance.setText("Наивысшая успеваимость");
            maxTitle.setText("Максимальный и средний балл для всех");
        }
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //TODO: Кнопка добавления студентов
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
                inputDialog();
                //TODO: Запись данных с Dialog
                {
                    data[i][0] = tempData[0];
                    data[i][1] = tempData[1];
                    data[i][2] = tempData[2];
                    tempMaxData[0][0] += Integer.parseInt(tempData[2]);
                    data[i][3] = tempData[3];
                    tempMaxData[0][1] += Integer.parseInt(tempData[3]);
                    data[i][4] = tempData[4];
                    tempMaxData[0][2] += Float.parseFloat(tempData[4]);
                }
                //TODO: Нахождение максимума
                {
                    if (Integer.parseInt(tempData[2]) > maxSem1) {
                        maxSem1 = Integer.parseInt(tempData[2]);
                    }
                    if (Integer.parseInt(tempData[3]) > maxSem2) {
                        maxSem2 = Integer.parseInt(tempData[3]);
                    }
                    if (Float.parseFloat(tempData[4]) > maxMean) {
                        maxMean = Float.parseFloat(tempData[4]);
                    }
                }
                i++;

                reloadMaxTable();

                DefaultTableModel dataTableMode = new DefaultTableModel(data, columns);
                countTitle.setText(Integer.toString(i));
                dataTable.setModel(dataTableMode);
                try {
                    Class.forName("org.sqlite.JDBC");

                    Connection conn = DriverManager.getConnection("jdbc:sqlite:F:/Jtable/tusur.db");
                    Statement stm = conn.createStatement();
                    stm.executeQuery("INSERT INTO tusur VALUES('"+tempData[0]+"','"+tempData[1]+"','"+tempData[2]+"','"+tempData[3]+"','"+tempData[4]+"');");
                    conn.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                PerformanceTable();
            }
        });

        //TODO: РАЗМЕТКА БЛОКОВ
        editFolder();
        //TODO: ПЕРЕЗАГРУЗКА ТАБЛИЦ
        reloadTable();
        reloadMaxTable();

        DefaultTableModel maxTableMode = new DefaultTableModel(dataMaxTable, columnsMaxTable);
        maxTable.setModel(maxTableMode);


        TableModel modelMax = new DefaultTableModel(dataMaxTable, columnsMaxTable);
        maxTable.setModel(modelMax);
        jScrollPane2.setViewportView(maxTable);

        //TODO: ТАБЛИЦА ТОП 3 УЧЕНИКА

        DefaultTableModel performanceTableModel = new DefaultTableModel(dataTop, columnsDataTop);
        tablePerformance.setModel(performanceTableModel);
        jScrollPane4.setViewportView(tablePerformance);
        PerformanceTable();

        pack();
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) { }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // TODO: Variables declaration
    private javax.swing.JLabel Performance;
    private javax.swing.JButton addButton;
    private javax.swing.JLabel count;
    private javax.swing.JLabel countTitle;
    private javax.swing.JTable dataTable;
    private javax.swing.JLabel discipline;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable maxTable;
    private javax.swing.JLabel maxTitle;
    private javax.swing.JTable tablePerformance;
    private javax.swing.JLabel titleDiscipline;
    private javax.swing.JLabel titleName;
}
