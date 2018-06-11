package zyc.com.replugin.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


/**
 * @author zhangyongchun
 */
public interface DownloadApi {

    @GET
    Call<ResponseBody> retrofitDownload(@Url String url);
}
