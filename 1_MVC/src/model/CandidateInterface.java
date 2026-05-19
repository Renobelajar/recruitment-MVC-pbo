package model;

import java.util.List;

public interface CandidateInterface {
    public void insert(Candidate candidate);
    public void update(Candidate candidate);
    public void delete(String name);
    public List<Candidate> getAll();
}