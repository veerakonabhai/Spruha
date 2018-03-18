package com.example.veerareddykonabhai.spruha;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lenovo on 12/13/2017.
 */

public class donoradapter extends RecyclerView.Adapter<donorholder> {
    List<Card> list;
    Context context;
    private int expandedposition=-1;
    String utype="";
    int f=0;
    public donoradapter(List<Card> list,Context context,int type){
        this.list=list;
        this.context=context;
        this.f=type;
        f=0;
    }

   /* public donoradapter(List<Reciever> list, Main2Activity context, int i) {
        this.list=list;
        this.context=context;
        this.f=i;
    }*/

    @Override
    public donorholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.cardview,parent,false);
        donorholder Holder=new donorholder(v);
        return Holder;
    }

    @Override
    public void onBindViewHolder(donorholder holder, int position) {
        final Card list2=list.get(position);
        String m;
        holder.tv1.setText(list2.getName());
        holder.tv2.setText(list2.getBlood());
        holder.tv3.setText(list2.getContact());
        holder.tv4.setText(list2.getLocality());
        holder.tv5.setText(list2.getAddress());

     }

    @Override
    public int getItemCount() {
        int arr=0;

        try{
            if(list.size()==0) arr=0;
            else arr=list.size();

        }
        catch (Exception e){

        }
        return arr;
    }
}
