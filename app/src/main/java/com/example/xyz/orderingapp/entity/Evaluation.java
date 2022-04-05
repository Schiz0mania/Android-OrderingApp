package com.example.xyz.orderingapp.entity;

import java.util.List;

/**
 * Created by xhh on 2022/4/3.
 */

public class Evaluation {
    private String name ;
    private String comments;
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    /*
    * private List<Comment> evaluations;

    public void setEvaluations(List<Comment> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Comment> getEvaluations() {
        return evaluations;
    }
    public List<Comment> getComment(){
        return this.evaluations;
    }

    public static class Comment{
        private String name ;
        private String comments;
        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getComments() {
            return comments;
        }

        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


    }
    *
    * */


}
