package com.example.ships.classes;

public class Ranking {
    private User[]ranking;

    public User[] getRanking() {
        return ranking;
    }

    public User getRanking(int a){
        return ranking[a];
    }

    public void setRanking(User[] ranking) {
        this.ranking = ranking;
    }

    Ranking(){
    }

    public Ranking(int lenght){
        ranking = new User[lenght];
    }

    public void addUsers(User user, int i){
        ranking[i]=user;
    }

    public void sortRanking(){
        User tmp;
        boolean sort;
        do{
        sort=false;
        for(int i=ranking.length-1;i>0;i--){
            if(ranking[i].getScore()>ranking[i-1].getScore()){
                tmp=ranking[i-1];
                ranking[i-1]=ranking[i];
                ranking[i]=tmp;
                sort=true;
            }else{
            }
        }
        }while(sort);
    }


}
