package com.example.xyz.orderingapp.event;

import com.example.xyz.orderingapp.entity.Evaluation;

/**
 * Created by xhh on 2022/4/6.
 */

public class CommentEvent {
    private Evaluation.Comment newComment;
    public CommentEvent(Evaluation.Comment newdate){
        this.newComment=newdate;
    }

    public Evaluation.Comment getNewComment() {
        return newComment;
    }
}
