package controller;

import model.Candidate;
import model.CandidateDAO;
import view.RecruitmentView;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RecruitmentController {
    private CandidateDAO dao;
    private RecruitmentView view;

    public RecruitmentController(CandidateDAO dao, RecruitmentView view) {
        this.dao = dao;
        this.view = view;
        
        // Membuka data pertama kali saat aplikasi dinyalakan
        loadData(); 

        // Menghubungkan Event Listener ke Tombol GUI
        this.view.btnAdd.addActionListener(e -> addCandidate());
        this.view.btnUpdate.addActionListener(e -> updateCandidate());
        this.view.btnDelete.addActionListener(e -> deleteCandidate());
        this.view.btnClear.addActionListener(e -> clearForm());

        // Event click baris tabel untuk memindahkan data ke kolom form kanan
        this.view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table.getSelectedRow();
                if (row != -1) {
                    view.tfName.setText(view.table.getValueAt(row, 0).toString());
                    view.cbPath.setSelectedItem(view.table.getValueAt(row, 1).toString());
                    view.tfWriting.setText(view.table.getValueAt(row, 2).toString());
                    view.tfCoding.setText(view.table.getValueAt(row, 3).toString());
                    view.tfInterview.setText(view.table.getValueAt(row, 4).toString());
                    view.tfName.setEditable(false); // Mengunci kolom nama saat mode edit/update
                }
            }
        });
    }

    private void loadData() {
        // 1. Bersihkan baris lama di model tabel agar data tidak menduplikat
        view.tableModel.setRowCount(0);
        
        // 2. Ambil data terbaru dari database SQL
        List<Candidate> list = dao.getAll();
        
        // 3. Masukkan data dari list ke baris tabel visual
        for (Candidate c : list) {
            Object[] row = {
                c.getName(), 
                c.getPath(), 
                c.getWriting(), 
                c.getCoding(), 
                c.getInterview(), 
                String.format("%.2f", c.getScore()), 
                c.getStatus()
            };
            view.tableModel.addRow(row);
        }
        
        // 4. Memaksa Java Swing untuk me-refresh total visual komponen tabel saat itu juga
        view.tableModel.fireTableDataChanged();
        view.table.revalidate();
        view.table.repaint();
    }

    private void addCandidate() {
        try {
            // Error Handling: Validasi jika ada form input yang dibiarkan kosong
            if(view.tfName.getText().trim().isEmpty() || view.tfWriting.getText().isEmpty() || view.tfCoding.getText().isEmpty() || view.tfInterview.getText().isEmpty()) {
                throw new Exception("Semua kolom nama dan nilai tes wajib diisi!");
            }

            String name = view.tfName.getText().trim();
            String path = view.cbPath.getSelectedItem().toString();
            double writing = Double.parseDouble(view.tfWriting.getText());
            double coding = Double.parseDouble(view.tfCoding.getText());
            double interview = Double.parseDouble(view.tfInterview.getText());

            // Error Handling: Validasi batas rentang nilai (0 - 100)
            if (writing < 0 || writing > 100 || coding < 0 || coding > 100 || interview < 0 || interview > 100) {
                throw new IllegalArgumentException("Rentang nilai tes harus berada di antara 0 sampai 100!");
            }

            // Membuat objek Kandidat baru & menghitung skor/kelulusan secara otomatis di Model
            Candidate candidate = new Candidate(name, path, writing, coding, interview);
            
            // Masukkan data ke database melalui DAO
            dao.insert(candidate);
            
            // Pop-up sukses muncul ke pengguna
            JOptionPane.showMessageDialog(view, "Data kandidat berhasil ditambahkan!");
            
            // Jalankan sinkronisasi ulang agar tabel kiri langsung terisi data baru
            loadData();
            clearForm();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Input nilai tes harus berupa angka valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCandidate() {
        try {
            if(view.tfName.getText().trim().isEmpty()) {
                throw new Exception("Pilih salah satu data pada tabel terlebih dahulu untuk diubah!");
            }
            
            String name = view.tfName.getText().trim();
            String path = view.cbPath.getSelectedItem().toString();
            double writing = Double.parseDouble(view.tfWriting.getText());
            double coding = Double.parseDouble(view.tfCoding.getText());
            double interview = Double.parseDouble(view.tfInterview.getText());

            if (writing < 0 || writing > 100 || coding < 0 || coding > 100 || interview < 0 || interview > 100) {
                throw new IllegalArgumentException("Rentang nilai tes harus berada di antara 0 sampai 100!");
            }

            Candidate candidate = new Candidate(name, path, writing, coding, interview);
            dao.update(candidate);
            
            JOptionPane.showMessageDialog(view, "Data kandidat berhasil diperbarui!");
            loadData();
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Input nilai tes harus berupa angka valid!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCandidate() {
        try {
            if(view.tfName.getText().trim().isEmpty()) {
                throw new Exception("Pilih salah satu data pada tabel terlebih dahulu untuk dihapus!");
            }
            String name = view.tfName.getText().trim();
            
            int confirm = JOptionPane.showConfirmDialog(view, "Apakah Anda yakin ingin menghapus data kandidat: " + name + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                dao.delete(name);
                JOptionPane.showMessageDialog(view, "Data berhasil dihapus dari database!");
                loadData();
                clearForm();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        view.tfName.setText("");
        view.tfWriting.setText("");
        view.tfCoding.setText("");
        view.tfInterview.setText("");
        view.cbPath.setSelectedIndex(0);
        view.tfName.setEditable(true); // Membuka kembali kunci kolom nama
        view.table.clearSelection();
    }
}