package com.yuanjun.weixindemo.bean;

/**
 * 获取素材列表调用接口所需要的参数实体类
 * @author xingwei
 *
 */
public class MaterialParam {
private String type;//素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
    
    private int offset;//从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
    
    private int count;//返回素材的数量，取值在1到20之间

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
