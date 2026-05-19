/*
 * KELAS   : IF-F
 * NIM     : 123240248
 * NAMA    : Reno Miftahudin
 */

import model.CandidateDAO;
import view.RecruitmentView;
import controller.RecruitmentController;

public class Main {
    public static void main(String[] args) {
        // Mengubah Look and Feel UI agar tombolnya modern soft-blue/light mirip mockup
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat LookAndFeel: " + e.getMessage());
        }

        // Instansiasi komponen MVC
        CandidateDAO model = new CandidateDAO();
        RecruitmentView view = new RecruitmentView();
        new RecruitmentController(model, view);

        // Menampilkan Window Form GUI ke layar
        view.setVisible(true);
    }
}