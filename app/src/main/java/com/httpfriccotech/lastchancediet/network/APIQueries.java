package com.httpfriccotech.lastchancediet.network;

import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Blog.BlogData;
import com.httpfriccotech.lastchancediet.Exercise.ExcerciseMainResponseModel;
import com.httpfriccotech.lastchancediet.Exercise.ExcerciseResponseModel;
import com.httpfriccotech.lastchancediet.Food.AddFoodDataResponse;
import com.httpfriccotech.lastchancediet.Food.FoodDetailResponseModel;
import com.httpfriccotech.lastchancediet.Food.SelectFoodData;
import com.httpfriccotech.lastchancediet.ReadMore.BlogByIdResponseData;
import com.httpfriccotech.lastchancediet.Recepies.RecepieItem;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public interface APIQueries {
    @GET("getRecipes")
    Observable<List<SelectFoodData>> doGetRecipies();

    @GET("getRecipes")
    Observable<List<RecepieItem>> doGetRecipieList();

    @GET("DeleteFoodItem")
    Observable<JsonObject> doDeleteFoodItem(@Query("user") String username, @Query("pass") String pass, @Query("postId") String postId, @Query("userId") String userId, @Query("CurrentDate") String selectedDate, @Query("time") long time);

    @GET("DeleteExercisItem")
    Observable<JsonObject> doDeleteExercisItem(@Query("user") String username, @Query("pass") String pass, @Query("postId") String postId, @Query("userId") String userId, @Query("CurrentDate") String selectedDate);

    @GET("ExercisrList")
    Observable<ExcerciseMainResponseModel> doGetExercisrSearchList(@Query("postName") String postName,@Query("time") long time);

    @GET("getBlogList")
    Observable<List<BlogData>> doGetBlogs();

    @GET("ExerciseDetail")
    Observable<ExcerciseResponseModel> doGetExcercises(@Query("userId") String uid, @Query("CurrentDate") String date, @Query("time") long time);

    @GET("FoodDetailBycategory")
    Observable<FoodDetailResponseModel> doGetFoodDetails(@Query("userId") String uid, @Query("CurrentDate") String date, @Query("time") long time);

    @GET("FoodDetailAdd")
    Observable<AddFoodDataResponse> doAddFoodData(@Query("user") String uid, @Query("pass") String pass, @Query("post_id") String post_id, @Query("is_type") String is_type, @Query("is_type_value") String is_type_value, @Query("fat_points") String fat_points, @Query("protein_points") String protein_points, @Query("carb_points") String carb_points, @Query("food_id") String food_id, @Query("title") String title, @Query("fiber") String fiber, @Query("selectedDate") String selectedDate);

    @GET("ExerciseAdd")
    Observable<JsonObject> doAddExercise(@Query("user") String uid,
                                        @Query("pass") String pass,
                                        @Query("post_id") String post_id,
                                        @Query("food_id") String exr_id,
                                        @Query("title") String title,
                                        @Query("is_etype") String is_type,
                                        @Query("how_long") String howLong,
                                        @Query("fat_points") String fatPoint,
                                        @Query("user_how_many_set") String sets,
                                        @Query("user_how_many_reps_set") String reps_sets,
                                        @Query("user_how_many_weight_set") String weight_sets,
                                        @Query("selectedDate") String selectedDate);


    @GET("getBlog")
    Observable<List<BlogByIdResponseData>> doGetBlogById(@Query("postId") String blogId, @Query("postType") String postType);

    @FormUrlEncoded
    @POST("init_session")
    Observable<List<SelectFoodData>> doAddFood(@Field("food_type") String foodTYpe, @Field("protien") String protien, @HeaderMap Map<String, String> headerData);
}
