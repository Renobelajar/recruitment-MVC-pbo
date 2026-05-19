package model;

public class Candidate {
    private String name;
    private String path;
    private double writing;
    private double coding;
    private double interview;
    private double score;
    private String status;

    // Constructor untuk penambahan data baru (menghitung nilai otomatis)
    public Candidate(String name, String path, double writing, double coding, double interview) {
        this.name = name;
        this.path = path;
        this.writing = writing;
        this.coding = coding;
        this.interview = interview;
        calculateScoreAndStatus();
    }

    // Constructor Overloading untuk mengambil data yang sudah jadi dari database
    public Candidate(String name, String path, double writing, double coding, double interview, double score, String status) {
        this.name = name;
        this.path = path;
        this.writing = writing;
        this.coding = coding;
        this.interview = interview;
        this.score = score;
        this.status = status;
    }

    // Logika Hitung Nilai Rata-rata & Penentuan Kelulusan (Batas minimum 85)
    public void calculateScoreAndStatus() {
        this.score = (this.writing + this.coding + this.interview) / 3.0;
        if (this.score >= 85.0) {
            this.status = "DITERIMA";
        } else {
            this.status = "TIDAK DITERIMA";
        }
    }

    // Encapsulation: Getter dan Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public double getWriting() { return writing; }
    public void setWriting(double writing) { this.writing = writing; }

    public double getCoding() { return coding; }
    public void setCoding(double coding) { this.coding = coding; }

    public double getInterview() { return interview; }
    public void setInterview(double interview) { this.interview = interview; }

    public double getScore() { return score; }
    public String getStatus() { return status; }
}