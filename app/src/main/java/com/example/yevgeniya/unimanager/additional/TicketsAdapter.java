package com.example.yevgeniya.unimanager.additional;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yevgeniya.unimanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevgeniya on 18.12.2017.
 */

public class TicketsAdapter extends BaseAdapter {
    /*private ArrayList<TicketItem> list;
    private Context context;

    public TicketsAdapter(Context context,int textViewResourceId, ArrayList<TicketItem> list){
        super(context,textViewResourceId,list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.ticket_item,null);
        }
        return super.getView(position, convertView, parent);
    }*/

    private ArrayList<TicketItem> list;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private Context context;

    public TicketsAdapter(Context context, ArrayList<TicketItem> list) {
        this.context = context;
        this.list = list;
        //this.inflater = LayoutInflater.from(context);
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public TicketItem getTicketItem(int position){
        return ((TicketItem)getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.ticket_item,parent,false);
            TicketItem ticketItem = getTicketItem(position);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.ticketIV);
            holder.ticketInfo = (TextView)convertView.findViewById(R.id.ticketInfoTV);
            //holder.imageView.setImageResource(ticketItem.getImage());
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.imageView.setBackgroundResource(list.get(position).getImage());
        holder.ticketInfo.setText(list.get(position).getTicketText());
        return convertView;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView ticketInfo;

    }
}
