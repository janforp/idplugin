package com.janita.idplugin.demo.swing.table;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SimpleTable
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class DefaultTableModelRendererTest {

    private static Map<Integer, User> map = new ConcurrentHashMap<>();

    JFrame jFrame = new JFrame("简单表格");

    Object[] titles = { "姓名", "年龄", "性别" };

    Object[][] data = {
            { new User("李清照", 29, "女") },
            { new User("苏格拉底", 56, "男") },
            { new User("李白", 35, "男") },
            { new User("弄玉", 18, "女") },
            { new User("虎头", 2, "男") },
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

        table.setDefaultRenderer(Object.class, new MyRenderer());

        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jFrame.add(new JScrollPane(table));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private class MyRenderer extends DefaultTableCellRenderer {

        /**
         * TODO 怎么弄？
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            User user = (User) value;
            if (user != null) {
                map.put(row, user);
            }
            if (user == null) {
                user = map.get(row);
            }

            if (column == 0) {
                setText(user.getName());
            }
            if (column == 1) {
                setText(user.getAge() + "");
            }
            if (column == 2) {
                setText(user.getSex());
            }
            return this;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    public static void main(String[] args) {

        new DefaultTableModelRendererTest().init();
    }

    @AllArgsConstructor
    @Data
    private static class User {

        private String name;

        private int age;

        private String sex;
    }
}
