package com.example.doancuoikymonandroid2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryAPI {
    @GET("entries/en/{word}")
    Call<List<DictionaryEntry>> getWordDetails(@Path("word") String word);
}