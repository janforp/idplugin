package com.janita.idplugin.demo.swing.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
public class DefaultTableModelTest {

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

        DefaultTableModel tableModel = new DefaultTableModel(dataV, titlesV);
        JTable table = new JTable(tableModel);

        JButton addRow = new JButton("添加一行");
        JButton addColumn = new JButton("添加一列");
        JButton deleteRow = new JButton("删除一行");

        addRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[] { "柳岩", 18, "女" });
            }
        });

        addColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addColumn("职业");
            }
        });

        deleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                tableModel.removeRow(selectedRow);
            }
        });

        JPanel panel = new JPanel();
        panel.add(addRow);
        panel.add(addColumn);
        panel.add(deleteRow);

        jFrame.add(panel, BorderLayout.SOUTH);

        jFrame.add(new JScrollPane(table));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {

        new DefaultTableModelTest().init();
    }
}
