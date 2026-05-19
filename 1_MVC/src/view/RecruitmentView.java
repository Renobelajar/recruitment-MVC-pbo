package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecruitmentView extends JFrame {
    public JTable table;
    public DefaultTableModel tableModel;
    public JTextField tfName, tfWriting, tfCoding, tfInterview;
    public JComboBox<String> cbPath;
    public JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public RecruitmentView() {
        setTitle("Sistem Rekrutmen PT. OOP");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PANEL KIRI: TABEL DATA ---
        String[] columns = {"Name", "Path", "Writing", "Coding", "Interview", "Score", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL KANAN: FORM INPUT & BUTTONS ---
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelRight.setPreferredSize(new Dimension(220, 500));

        // Inisialisasi Form Fields
        tfName = new JTextField();
        String[] paths = {"Android Dev", "Web Dev"};
        cbPath = new JComboBox<>(paths);
        tfWriting = new JTextField();
        tfCoding = new JTextField();
        tfInterview = new JTextField();

        // Menyusun Komponen Form ke Panel Kanan
        panelRight.add(new JLabel("Name"));
        panelRight.add(tfName);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(new JLabel("Path"));
        panelRight.add(cbPath);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(new JLabel("Writing"));
        panelRight.add(tfWriting);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(new JLabel("Coding"));
        panelRight.add(tfCoding);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(new JLabel("Interview"));
        panelRight.add(tfInterview);
        panelRight.add(Box.createVerticalStrut(15));

        // Inisialisasi Tombol Aksi
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        // Menyamakan Ukuran Tombol
        Dimension btnSize = new Dimension(200, 30);
        btnAdd.setMaximumSize(btnSize);
        btnUpdate.setMaximumSize(btnSize);
        btnDelete.setMaximumSize(btnSize);
        btnClear.setMaximumSize(btnSize);

        // Memasukkan Tombol ke Panel Kanan
        panelRight.add(btnAdd);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(btnUpdate);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(btnDelete);
        panelRight.add(Box.createVerticalStrut(5));
        panelRight.add(btnClear);

        add(panelRight, BorderLayout.EAST);
    }
}