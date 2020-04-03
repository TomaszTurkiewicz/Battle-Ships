package com.example.ships.classes;

public class User {

    private String id;
    private String name;
    private String email;
    private int score;
    private int noOfGames;
    private FightIndex index;
    private String idToken;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }



    public User() {
    }

    public FightIndex getIndex() {
        return index;
    }

    public void setIndex(FightIndex index) {
        this.index = index;
    }

    public User(String id, String name, String email, int score, int noOfGames, FightIndex index) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.score = score;
        this.noOfGames = noOfGames;
        this.index = index;
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

    public void makeUser(User user){
        this.id=user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.score = getScore();
        this.noOfGames = user.getNoOfGames();
        this.index = user.getIndex();
    }
}
