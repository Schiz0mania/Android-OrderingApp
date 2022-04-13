package com.example.xyz.orderingapp.entity;



import java.util.List;

/**
 * Created by xyz on 2022/4/1.
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

    public static class Comment{
        private String name ;  // 用户名
        private String comments;  // 对应评论
        private boolean isNewPosted;  // 是否为本次启动期间发布，用于后续删除操作
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
