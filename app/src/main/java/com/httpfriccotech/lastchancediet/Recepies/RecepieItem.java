package com.httpfriccotech.lastchancediet.Recepies;

import java.io.Serializable;

/**
 * Created by RAJNISH on 08/16/2018.
 */

public class RecepieItem implements Serializable{
    private String recepieName;
    private String recepieDescription;
    private String recepieImage;
    private int recepiePostId;

    public RecepieItem(String rn,String rd,String ri, int rpi){
        recepieName = rn;
        recepieDescription = rd;
        recepieImage = ri;
        recepiePostId = rpi;
    }

    public String getRecepieName(){
        return recepieName;
    }
    public String getRecepieDescription(){
        return recepieDescription;
    }
    public String getRecepieImage(){
        return recepieImage;
    }

    public int getRecepiePostId(){
        return recepiePostId;
    }

    public void setRecepieName(String recepieName){
        this.recepieName = recepieName;
    }
    public void setrecepieDescription(String recepieDescription){
        this.recepieDescription = recepieDescription;
    }
    public void setrecepieImage(String recepieImage){
        this.recepieImage = recepieImage;
    }
    public void setrecepiePostId(int recepiePostId){
        this.recepiePostId = recepiePostId;
    }

}