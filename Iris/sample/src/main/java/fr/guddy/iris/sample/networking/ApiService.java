package fr.guddy.iris.sample.networking;

import android.support.annotation.NonNull;

import java.util.List;

import fr.guddy.iris.sample.networking.dto.RepoDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{user}/repos")
    Call<List<RepoDTO>> listRepos(@NonNull @Path("user") final String user);
}
