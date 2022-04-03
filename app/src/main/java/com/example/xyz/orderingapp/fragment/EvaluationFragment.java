package com.example.xyz.orderingapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.xyz.orderingapp.R;
import com.example.xyz.orderingapp.entity.Evaluation;
import com.example.xyz.orderingapp.event.GoodsListEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhh on 2022/4/3.
 */

public class EvaluationFragment extends BaseFragment {
    private String keywords;
    private StickyHeadersItemDecoration top;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {


    }


    private List<Evaluation.Comment> getData() {

        List<Evaluation.Comment> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            Evaluation.Comment bean = new Evaluation.Comment();

           bean.setName("unknowuser" + i);
           bean.setComments("好吃欸！！！！");
            list.add(bean);
        }

        return list;
    }


    public static EvaluationFragment getInstance(String mTitle) {
        EvaluationFragment tabFragment = null;


        if (tabFragment == null) {
            tabFragment = new EvaluationFragment();
        }
        tabFragment.keywords = mTitle;
        return tabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluate, null);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

        init(recyclerView);

        return view;
    }


    private void init(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAapter());
    }




    private class MyAapter extends RecyclerView.Adapter<MyAapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,
                    viewGroup, false);
            return new ViewHolder(view);
        }


        @Override public void onBindViewHolder(ViewHolder holder, int position) {

            Evaluation.Comment bean = getData().get(position);

            holder.evaName.setText(bean.getName());
            holder.evaComment.setText(bean.getComments());

        }


        @Override public int getItemCount() {
            return getData().size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private final TextView evaName;
            private final TextView evaComment;



            public ViewHolder(View itemView) {
                super(itemView);
                evaName = (TextView) itemView.findViewById(R.id.eva_name);
                evaComment=itemView.findViewById(R.id.eva_comment);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击："+getPosition(),Toast.LENGTH_LONG).show();
            }
        }



    }
    
}
