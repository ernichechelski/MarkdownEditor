package com.example.ernestchechelski.markdowntest.barAction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ernestchechelski.markdowntest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ErnestChechelski on 30.10.2017.
 */

public class BarActionAdapter extends RecyclerView.Adapter<BarActionAdapter.MyViewHolder> {

    private List<BarAction> items;
    int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button button;

        public MyViewHolder(View view) {
            super(view);
            button = (Button) view.findViewById(R.id.button);
        }

    }

    public void addAction(BarAction barAction){
        barAction.parentAdapter =this;
        items.add(barAction);
    }

    public void addAction(BarAction barAction,BarAction afterItem){
        items.add(items.indexOf(afterItem)+1,barAction);
    }




    public BarActionAdapter(List<BarAction> items) {
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buttons_recycle_view_button, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BarAction movie = getOuterArray().get(position);
        holder.button.setText(movie.getName());
        holder.button.setOnClickListener(movie.getOnClickListener());

    }

    public List<BarAction> getOuterArray(){
        List<BarAction> result = new ArrayList<>();
        result.addAll(iterateArray(items));
        return result;
    }

    private List<BarAction> iterateArray(List<BarAction> array){
        List<BarAction> result = new ArrayList<>();
        for(BarAction b:array){
            result.add(b);
            if(b.expanded) result.addAll(iterateArray(b.children));
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return getOuterArray().size();
    }
}
