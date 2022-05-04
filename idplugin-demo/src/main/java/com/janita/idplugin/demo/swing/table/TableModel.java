package com.janita.idplugin.demo.swing.table;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

/**
 * SimpleTable
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class TableModel {

    JFrame jFrame = new JFrame("简单表格");

    Object[] titles = { "姓名", "年龄", "性别" };

    Object[][] data = {
            { "李清照", 29, "女" },
            { "苏格拉底", 56, "男" },
            { "李白", 35, "男" },
            { "弄玉", 18, "女" },
            { "虎头", 2, "男" },
    };

    private final Vector<Object> titlesV = new Vector<>();

    private final Vector<Vector<Object>> dataV = new Vector<>();

    private void init() {
        titlesV.addAll(Arrays.asList(titles));
        for (Object[] datum : data) {
            Vector<Object> t = new Vector<>(Arrays.asList(datum));
            dataV.add(t);
        }

        MyTableModel myTableModel = new MyTableModel();

        JTable table = new JTable(myTableModel);

        JButton button = new JButton("获取选中行数据");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedColumn = table.getSelectedColumn();
                int selectedRow = table.getSelectedRow();
                System.out.println("地区选中行 = " + selectedRow + " , 列 = " + selectedColumn);

                Object value = myTableModel.getValueAt(selectedRow, selectedColumn);

                System.out.println("选中的内容 " + value);
            }
        });

        jFrame.add(button, BorderLayout.SOUTH);

        jFrame.add(new JScrollPane(table));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private class MyTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return dataV.size();
        }

        @Override
        public int getColumnCount() {
            return titlesV.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return dataV.get(rowIndex).get(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return (String) titlesV.get(column);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }

    public static void main(String[] args) {

        new TableModel().init();
    }
}
