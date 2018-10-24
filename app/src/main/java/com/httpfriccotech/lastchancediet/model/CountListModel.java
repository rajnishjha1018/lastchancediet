package com.httpfriccotech.lastchancediet.model;

import com.google.gson.annotations.SerializedName;

public class CountListModel {
    @SerializedName("TOTALUSERS")
    String TOTALUSERS;
    @SerializedName("PARTNER")
    String PARTNER;
    @SerializedName("NEWUSERS")
    String NEWUSERS;
    @SerializedName("USERSLEFT")
    String USERSLEFT;

    public String getTOTALUSERS() {
        return TOTALUSERS;
    }

    public void setTOTALUSERS(String TOTALUSERS) {
        this.TOTALUSERS = TOTALUSERS;
    }

    public String getPARTNER() {
        return PARTNER;
    }

    public void setPARTNER(String PARTNER) {
        this.PARTNER = PARTNER;
    }

    public String getNEWUSERS() {
        return NEWUSERS;
    }

    public void setNEWUSERS(String NEWUSERS) {
        this.NEWUSERS = NEWUSERS;
    }

    public String getUSERSLEFT() {
        return USERSLEFT;
    }

    public void setUSERSLEFT(String USERSLEFT) {
        this.USERSLEFT = USERSLEFT;
    }
}
