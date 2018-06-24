package com.example.yevgeniya.unimanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.StackView;

import com.example.yevgeniya.unimanager.additional.TicketItem;
import com.example.yevgeniya.unimanager.additional.TicketsAdapter;

import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity {
    private Intent intent;
    private ArrayList<String>tickets;
    private StackView ticketStackView;
    private ArrayList<TicketItem>ticketItems;
    private TicketsAdapter ticketsAdapter;
    private static Integer[]cards = {R.drawable.funny_logo1,R.drawable.funny_logo2,R.drawable.funny_logo3,R.drawable.funny_logo4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        intent = getIntent();
        tickets = intent.getStringArrayListExtra("tickets");
        ticketStackView = (StackView)findViewById(R.id.ticketsSV);
        ticketItems = new ArrayList<>();
        for(int i=0;i<tickets.size();i++){
            ticketItems.add(new TicketItem(cards[i],tickets.get(i)));
        }
        ticketsAdapter = new TicketsAdapter(this,ticketItems);
        ticketStackView.setAdapter(ticketsAdapter);
        ticketsAdapter.notifyDataSetChanged();
    }
}
