package cn.xiao.zufang.web.dto;

public final class QiNiuResultSet {
    public String key;
    public String hash;
    public String bucket;
    public String width;
    public String height;

    @Override
    public String toString() {
        return "QiNiuResultSet{" +
                "key='" + key + '\'' +
                ", hash='" + hash + '\'' +
                ", bucket='" + bucket + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
