package com.example.ships.classes;

public class User {

    private String id;
    private String name;
    private String email;
    private int score;
    private int noOfGames;
    private FightIndex index;
    private SinglePlayerMatch singlePlayerMatch;


    public User() {
    }

    public SinglePlayerMatch getSinglePlayerMatch() {
        return singlePlayerMatch;
    }

    public void setSinglePlayerMatch(SinglePlayerMatch singlePlayerMatch) {
        this.singlePlayerMatch = singlePlayerMatch;
    }

    public FightIndex getIndex() {
        return index;
    }

    public void setIndex(FightIndex index) {
        this.index = index;
    }

    public User(String id, String name, String email, int score, int noOfGames, FightIndex index, SinglePlayerMatch singlePlayerMatch, String profileImage) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.score = score;
        this.noOfGames = noOfGames;
        this.index = index;
        this.singlePlayerMatch = singlePlayerMatch;
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
// TODO default constructor with fields
// TODO get rid off email from database