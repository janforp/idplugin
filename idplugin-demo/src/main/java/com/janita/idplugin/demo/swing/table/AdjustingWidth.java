package com.janita.idplugin.demo.swing.table;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * SimpleTable
 *
 * @author zhucj
 * @since 20220324
 */
public class AdjustingWidth {

    JFrame jFrame = new JFrame("调整表格");

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

        // 设置选择模式
        ListSelectionModel model = table.getSelectionModel();

        // 没限制
        // model.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // 单个连续范围
        // model.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 设置列宽
        TableColumn name = table.getColumn("姓名");
        name.setMinWidth(40);

        TableColumn sex = table.getColumn("性别");
        sex.setMaxWidth(50);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {

        new AdjustingWidth().init();
    }
}
