package com.skillseekr;

import java.time.LocalDateTime;

public class CalendarActivity {
    private LocalDateTime offerDate;
    private String offerTitle;

    public CalendarActivity(LocalDateTime offerDate, String offerTitle) {
        this.offerDate = offerDate;
        this.offerTitle = offerTitle;
    }

    public LocalDateTime getOfferDate() {
        return offerDate;
    }

    public String getOfferTitle() {
        return offerTitle;
    }
}