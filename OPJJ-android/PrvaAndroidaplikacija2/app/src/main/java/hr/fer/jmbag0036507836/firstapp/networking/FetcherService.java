package hr.fer.jmbag0036507836.firstapp.networking;

import hr.fer.jmbag0036507836.firstapp.data.ImageResponse;
import hr.fer.jmbag0036507836.firstapp.data.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FetcherService {
    @GET("android/image.json")
    Call<ImageResponse> getImage();

    @GET("android/cap.json")
    Call<UserInfo> getUserInfo();
}
