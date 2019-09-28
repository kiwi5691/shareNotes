package cn.sharenotes.db.domain;

import java.util.Date;

public class UserFormid {
    private Integer id;

    private String formid;

    private Boolean isprepay;

    private Integer useamount;

    private Date expireTime;

    private String openid;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid == null ? null : formid.trim();
    }

    public Boolean getIsprepay() {
        return isprepay;
    }

    public void setIsprepay(Boolean isprepay) {
        this.isprepay = isprepay;
    }

    public Integer getUseamount() {
        return useamount;
    }

    public void setUseamount(Integer useamount) {
        this.useamount = useamount;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}