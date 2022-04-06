package com.example.xyz.orderingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.entity.Evaluation;
import com.example.xyz.orderingapp.event.CommentEvent;
import com.example.xyz.orderingapp.fragment.CommentFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by xhh on 2022/4/5.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private List<Evaluation.Comment> commentData;
    private Context context;
    @Override
        public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment,
                viewGroup, false);

        return new CommentAdapter.ViewHolder(view);
    }
    public CommentAdapter(Context context,List<Evaluation.Comment> data){
        this.context=context;
        this.commentData=data;
    }

    @Override public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {

        Evaluation.Comment bean = commentData.get(position);
        holder.evaName.setText(bean.getName());
        holder.evaComment.setText(bean.getComments());



    }
    @Override
    public int getItemCount() {

        return commentData.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder  {

        private final TextView evaName;
        private final TextView evaComment;

        public ViewHolder(View itemView) {
            super(itemView);
            evaName = (TextView) itemView.findViewById(R.id.eva_name);
            evaComment=itemView.findViewById(R.id.eva_comment);


        }


    }

}
