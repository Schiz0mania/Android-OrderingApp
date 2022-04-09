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
    public void addDate(Comment e){
        this.data.add(e);

    }
    public void addData(Comment newComment){
        this.data.add(newComment);

    }

    public static class Comment{// 用户名，用户评论，是否最新发布（相对于本次启动而言）
        private String name ;
        private String comments;
        private boolean isNewPosted;
        public Comment(){

        }
        public Comment(String cName,String cComment){
            this.name=cName;
            this.comments=cComment;
            this.isNewPosted=true;

        }

        public boolean isNewPosted() {
            return isNewPosted;
        }

        public void setNewPosted(boolean newPosted) {
            isNewPosted = newPosted;
        }

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
