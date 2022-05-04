package com.janita.idplugin.demo.swing.table;

import javax.swing.*;

/**
 * SimpleTable
 *
 * @author zhucj
 * @since 20220324
 */
public class SimpleTable {

    JFrame jFrame = new JFrame("简单表格");

    Object[] titles = { "姓名", "年龄", "性别" };

    Object[][] data = {
            { "李清照", 29, "女" },
            { "苏格拉底", 56, "男" },
            { "李白", 35, "男" },
            { "弄玉", 18, "女" },
            { "虎头", 2, "男" },
    };

    private void init() {

        JTable table = new JTable(data, titles);

        jFrame.add(new JScrollPane(table));

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {

        new SimpleTable().init();
    }
}
