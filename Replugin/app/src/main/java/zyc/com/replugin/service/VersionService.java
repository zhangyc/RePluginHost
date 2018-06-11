package zyc.com.replugin.service;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import zyc.com.replugin.bean.HotFixInfo;
import zyc.com.replugin.bean.PublicResponseEntity;
import zyc.com.replugin.contact.PublicContact;

/**
 * @author zhangyongchun
 */
public interface VersionService {
    @GET(PublicContact.VERSIONINFOLISTS)
    Observable<PublicResponseEntity<HotFixInfo>> getVersionInfo(@Query("key") String key);

    @GET(PublicContact.VERSIONFIXLISTS)
    Observable<PublicResponseEntity<List<HotFixInfo>>> getHotFix(@Query("appKey") String key,
                                                                @Query("appVersion") String version);
}