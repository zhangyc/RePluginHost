package zyc.com.replugin.bean;

/**
 * Created by zhangyc on 2018/5/18.
 */

public class HotFixInfo {

    /**
     * code : plugin1
     * createTime : 1526633465000
     * downloadUrl : http://10.130.212.39/client/h/8e9604a40dde4a109bb485a7e7043a86/2acb1495802240398d6ac07e875e081c/app-debug.apk
     * id : 78
     * info :
     * md5 : d73278776635932ed0280b6170694160
     * no :
     * operator : 15011381883
     * remark :
     * uri : plugin1
     * versionId : 115
     */

    private String code;
    private long createTime;
    private String downloadUrl;
    private int id;
    private String info;
    private String md5;
    private String no;
    private String operator;
    private String remark;
    private String uri;
    private int versionId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
}
