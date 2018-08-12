package com.abani.exercise.android.bakingapp.networking;

import com.abani.exercise.android.bakingapp.constants.Constants;
import com.abani.exercise.android.bakingapp.models.BakingItemResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET(Constants.CONTENT_URL_PATH)
    Call<List<BakingItemResponse>> getRecipes();
}
