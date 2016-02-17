package com.example.vova.ratingsystemtneu;

import java.util.List;

/**
 * Created by vova on 26.01.2016.
 */
public class Subjects {
    private String name;
    private String controlType;
    private int totalScore;
    private List<Modules> modulList;

    public List<Modules> getModulList(int position) {
        return modulList;
    }

    public void setModulList(List<Modules> modulList) {
        this.modulList = modulList;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }


    //--------------------------------------------------------------------------------------------
    public static class Modules{
        private String date;
        private Integer weight;
        private Integer score;


        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }


    }
}
