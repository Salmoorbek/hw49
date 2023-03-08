package com.example.hw49.service;

import com.example.hw49.model.Candidate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CandidateService {
    private ArrayList<Candidate> candidates;
    private static Integer allVotes = 0;

    public CandidateService() {
        candidates = new ArrayList<>(List.of(readFile()));
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Candidate getCurrentCandidate(String id) {
        int num;
        num = Integer.parseInt(id);
        return candidates.get(num - 1);
    }
    public static Candidate[] readFile() {
        Path path = Paths.get("./src/main/resources/static/candidates.json");
        String json = "";
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(json, Candidate[].class);
    }
    public static void writeFile(List<Candidate> employee){
        Path path = Paths.get("./src/main/resources/static/candidates.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Candidate[] result = employee.toArray(Candidate[]::new);
        String json = gson.toJson(result);
        try{
            byte[] jsonBytes = json.getBytes();
            Files.write(path, jsonBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getVote(Candidate candidate){
        candidate.setVote(candidate.getVote() + 1);
        allVotes++;
    }
    public int getPrecent(Candidate candidate){
        return candidate.getVote() * 100 / allVotes;
    }

    public Integer getAllVotes() {
        return allVotes;
    }

    public List<Candidate> sortLost(CandidateService candidate){
        return candidates.stream()
                .sorted((Comparator.comparing(Candidate::getVote)).reversed())
                .collect(Collectors.toList());
    }
}
