package com.example.xyz.orderingapp.entity;



import java.util.List;

/**
 * Created by xhh on 2022/4/3.
 */

public class Evaluation {
    private List<Comment> data;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
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



}
