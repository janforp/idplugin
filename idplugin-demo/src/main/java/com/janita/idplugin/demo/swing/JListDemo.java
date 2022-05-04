package com.janita.idplugin.demo.swing;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JListDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class JListDemo {

    JFrame jf = new JFrame("列表框测试");

    String[] books = { "java自学宝典", "轻量级javaEE企业应用实战", "Android基础教程", "jQuery实战教程", "Spring" };

    JPanel layoutPanel = new JPanel();

    ButtonGroup layoutGroup = new ButtonGroup();

    JPanel selectModePanel = new JPanel();

    ButtonGroup selectModeGroup = new ButtonGroup();

    JTextArea favorite = new JTextArea(4, 40);

    JList<String> bookList;

    JComboBox<String> bookSelector;

    public void init() {
        bookList = new JList<>(books);
        addBtn2LayoutPanel("纵向滚动", JList.VERTICAL);
        addBtn2LayoutPanel("纵向换行", JList.VERTICAL_WRAP);
        addBtn2LayoutPanel("横向换行", JList.HORIZONTAL_WRAP);

        addBtn2SelectedModelPanel("无限制", ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addBtn2SelectedModelPanel("单选", ListSelectionModel.SINGLE_SELECTION);
        addBtn2SelectedModelPanel("单范围", ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        bookList.setVisibleRowCount(3);
        bookList.setSelectionInterval(2, 4);

        Box bookListBox = Box.createVerticalBox();
        bookListBox.add(layoutPanel);
        bookListBox.add(selectModePanel);

        bookSelector = new JComboBox<>(books);

        bookSelector.setEditable(true);
        bookSelector.setMaximumRowCount(4);

        Box topBox = Box.createHorizontalBox();
        topBox.add(bookListBox);
        topBox.add(bookSelector);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        bottomPanel.add(new JLabel("您最喜欢的图书："), BorderLayout.NORTH);
        bottomPanel.add(favorite);

        Box holeBox = Box.createVerticalBox();
        holeBox.add(topBox);
        holeBox.add(bottomPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setPreferredSize(new Dimension(600, 400));
        jf.pack();
        jf.setVisible(true);
    }

    private void addBtn2LayoutPanel(String name, int layoutType) {
        layoutPanel.setBorder(new TitledBorder(new EtchedBorder(), "确定标题选项布局"));
        JRadioButton button = new JRadioButton(name);
        layoutPanel.add(button);

        if (layoutGroup.getButtonCount() == 0) {
            button.setSelected(true);
        }

        layoutGroup.add(button);

        button.addActionListener(e -> bookList.setLayoutOrientation(layoutType));
    }

    private void addBtn2SelectedModelPanel(String name, int selectMode) {
        selectModePanel.setBorder(new TitledBorder(new EtchedBorder(), "确定选择模式"));

        JRadioButton button = new JRadioButton(name);
        if (selectModeGroup.getButtonCount() == 0) {
            button.setSelected(true);
        }

        selectModeGroup.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookList.setSelectionMode(selectMode);
            }
        });
    }

    public static void main(String[] args) {
        new JListDemo().init();
    }
}