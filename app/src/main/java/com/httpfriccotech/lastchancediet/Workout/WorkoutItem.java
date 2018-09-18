package com.httpfriccotech.lastchancediet.Workout;

/**
 * Created by RAJNISH on 08/16/2018.
 */

public class WorkoutItem {
    private String workoutName;
    private String workoutDescription;
    private String workoutImage;
    private int workoutPostId;

    public WorkoutItem(String rn, String rd, String ri, int rpi){
        workoutName = rn;
        workoutDescription = rd;
        workoutImage = ri;
        workoutPostId = rpi;
    }

    public String getWorkoutName(){
        return workoutName;
    }
    public String getWorkoutDescription(){
        return workoutDescription;
    }
    public String getWorkoutImage(){
        return workoutImage;
    }
    public int getWorkoutPostId(){
        return workoutPostId;
    }

    public void setRecepieName(String workoutName){
        this.workoutName = workoutName;
    }
    public void setrecepieDescription(String workoutDescription){
        this.workoutDescription = workoutDescription;
    }
    public void setrecepieImage(String workoutImage){
        this.workoutImage = workoutImage;
    }
    public void setrecepiePostId(int workoutPostId){
        this.workoutPostId = workoutPostId;
    }

}