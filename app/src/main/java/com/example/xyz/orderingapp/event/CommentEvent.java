package com.example.xyz.orderingapp.event;

import com.example.xyz.orderingapp.entity.Evaluation;

/**
 * Created by xyz on 2022/4/2.
 */


/*
*
* MainActivity内添加评论后发布给CommentAdapter完成
* 数据更新与适配刷新
*
* */
public class CommentEvent {
    private Evaluation.Comment newComment;
    public CommentEvent(Evaluation.Comment newdate){
        this.newComment=newdate;
    }

    public Evaluation.Comment getNewComment() {
        return newComment;
    }
}
