package fr.guddy.iris.sample.networking;

import android.support.annotation.NonNull;

import java.util.List;

import fr.guddy.iris.sample.networking.dto.RepoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("repos")
    Call<RepoDTO> createRepo(@Body @NonNull final RepoDTO repo);

    @PUT("repos")
    Call<RepoDTO> updateRepo(@Body @NonNull final RepoDTO repo);

    @GET("users/{user}/repos")
    Call<List<RepoDTO>> listRepos(@Path("user") @NonNull final String user);

    @DELETE("/users/{id}")
    Call<Void> deleteUser(@Path("id") @NonNull final Long id);

    @FormUrlEncoded
    @POST("/question/new")
    Call<Void> sendQuestion(
            @Field("questionName") @NonNull final String questionName,
            @Field("promptText") @NonNull final String promptText,
            @Field("optionTexts") @NonNull final String[] optionTexts,
            @Field("correctOptionIndex") final int correctOptionIndex
    );
}
