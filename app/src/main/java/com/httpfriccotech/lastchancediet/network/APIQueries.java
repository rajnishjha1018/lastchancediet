package com.httpfriccotech.lastchancediet.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.httpfriccotech.lastchancediet.Blog.BlogData;
import com.httpfriccotech.lastchancediet.Exercise.ExcerciseMainResponseModel;
import com.httpfriccotech.lastchancediet.Exercise.ExcerciseResponseModel;
import com.httpfriccotech.lastchancediet.Food.AddFoodDataResponse;
import com.httpfriccotech.lastchancediet.Food.FoodDetailResponseModel;
import com.httpfriccotech.lastchancediet.Food.SelectFoodData;
import com.httpfriccotech.lastchancediet.ReadMore.BlogByIdResponseData;
import com.httpfriccotech.lastchancediet.model.AdminDashBordModel;
import com.httpfriccotech.lastchancediet.model.GenericRequestModel;
import com.httpfriccotech.lastchancediet.model.LoginModel;
import com.httpfriccotech.lastchancediet.model.LoginResponseModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RAJNISH on 09/08/2018.
 */

public interface APIQueries {
    @POST("jwt-auth/v1/token")
    Observable<LoginResponseModel> goLogin(@Body LoginModel jsonObject);
    @POST("wp/v2/authenticate")
    Observable<JsonObject> doLoginCallBack(@Query("user") String username, @Query("pass") String pass);
    @POST("wp/v2/UserFoodDetail")
    Observable<JsonObject> doGetDashBoard(@Body GenericRequestModel genericRequestModel);
    @POST("wp/v2/FoodList")
    Observable<List<SelectFoodData>> doGetRecipies(@Query("search") String query);
    @POST("wp/v2/getRecipes")
    Observable<JsonArray> doGetRecipieList();
    @POST("wp/v2/getWorkouts")
    Observable<JsonArray> doGetWorkoutList();

    @POST("wp/v2/DeleteFoodItem")
    Observable<JsonObject> doDeleteFoodItem(@Query("postId") String postId, @Query("userId") String userId, @Query("CurrentDate") String selectedDate, @Query("time") long time);

    @POST("wp/v2/DeleteExercisItem")
    Observable<JsonObject> doDeleteExercisItem(@Query("postId") String postId, @Query("userId") String userId, @Query("CurrentDate") String selectedDate, @Query("time") long time);

    @POST("wp/v2/ExercisrList")
    Observable<ExcerciseMainResponseModel> doGetExercisrSearchList(@Query("postName") String postName,@Query("time") long time);

    @POST("wp/v2/getBlogList")
    Observable<List<BlogData>> doGetBlogs();

    @POST("wp/v2/ExerciseDetail")
    Observable<ExcerciseResponseModel> doGetExcercises(@Query("userId") String uid, @Query("CurrentDate") String date, @Query("time") long time);

    @POST("wp/v2/FoodDetailBycategory")
    Observable<FoodDetailResponseModel> doGetFoodDetails(@Query("userId") String uid, @Query("CurrentDate") String date, @Query("time") long time);

    @POST("wp/v2/FoodDetailAdd")
    Observable<AddFoodDataResponse> doAddFoodData(@Query("userId") String uid, @Query("post_id") String post_id, @Query("is_type") String is_type, @Query("is_type_value") String is_type_value, @Query("fat_points") String fat_points, @Query("protein_points") String protein_points, @Query("carb_points") String carb_points, @Query("food_id") String food_id, @Query("title") String title, @Query("fiber") String fiber, @Query("selectedDate") String selectedDate);

    @POST("wp/v2/ExerciseAdd")
    Observable<JsonObject> doAddExercise(@Query("userId") String uid,
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


    @POST("wp/v2/getBlog")
    Observable<List<BlogByIdResponseData>> doGetBlogById(@Query("postId") String blogId);
    @POST("wp/v2/getWorkout")
    Observable<List<BlogByIdResponseData>> doGetWorkoutById(@Query("postId") String blogId);
    @POST("wp/v2/getRecipe")
    Observable<List<BlogByIdResponseData>> doGetRecipeById(@Query("postId") String blogId);
    @POST("wp/v2/ProgramList")
    Observable<JsonObject> getProgramList(@Query("userId") String userId);
    @POST("wp/v2/ProgramListById")
    Observable<JsonArray> getProgramDetail(@Query("postId") String userId);
    @POST("wp/v2/UserProfile")
    Observable<JsonObject> getUserProfileDetail(@Query("userId") String userId);
    @POST("wp/v2/ContactUs")
    Observable<JsonObject> getContactUs();
    @POST("wp/v2/AdminDashboard")
    Observable<AdminDashBordModel> getAdminDashBord(@Query("userId") String userId);
    @POST("wp/v2/PartnerDashboard")
    Observable<AdminDashBordModel> getPartnerDashBord(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("init_session")
    Observable<List<SelectFoodData>> doAddFood(@Field("food_type") String foodTYpe, @Field("protien") String protien, @HeaderMap Map<String, String> headerData);
}
