package hr.fer.jmbag0036507836.firstapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageResponse implements Serializable {

    @SerializedName("url_location")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
