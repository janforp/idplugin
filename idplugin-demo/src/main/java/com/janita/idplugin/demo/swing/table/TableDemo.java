package com.janita.idplugin.demo.swing.table;

import lombok.AllArgsConstructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * TableDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class TableDemo {

    public static void main(String[] args) {
        JTable table = new JTable();

        Vector<Book> books = new Vector<>();
        books.add(new Book("历史", 100D));
        books.add(new Book("语文", 10D));
        DefaultTableModel model = new DefaultTableModel(books, 10);
        table.setModel(model);

        JFrame frame = new JFrame();
        frame.add(table);

        frame.pack();
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setVisible(true);
    }

    @AllArgsConstructor
    private static class Book {

        private String name;

        private Double price;
    }
}
