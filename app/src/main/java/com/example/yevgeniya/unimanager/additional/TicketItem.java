package com.example.yevgeniya.unimanager.additional;

/**
 * Created by Yevgeniya on 18.12.2017.
 */

public class TicketItem {
    private Integer image;
    private String ticketText;

    public TicketItem(Integer image, String ticketText) {
        this.image = image;
        this.ticketText = ticketText;
    }

    public Integer getImage() {
        return image;
    }

    public String getTicketText() {
        return ticketText;
    }
}
