package com.example.ships;

public class User {

    private String id;
    private String name;
    private String email;
    private int score;
    private int noOfGames;

    public User() {
    }



    public User(String id, String name, String email, int score, int noOfGames) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.score = score;
        this.noOfGames = noOfGames;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNoOfGames() {
        return noOfGames;
    }

    public void setNoOfGames(int noOfGames) {
        this.noOfGames = noOfGames;
    }
}
