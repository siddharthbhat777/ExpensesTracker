package com.project.expensestracker;

public class ModelClass {
    private String desc, dAndT;
    private long amt;

    ModelClass(){}

    public ModelClass(String desc, String dAndT, long amt) {
        this.desc = desc;
        this.dAndT = dAndT;
        this.amt = amt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getdAndT() {
        return dAndT;
    }

    public void setdAndT(String dAndT) {
        this.dAndT = dAndT;
    }

    public long getAmt() {
        return amt;
    }

    public void setAmt(long amt) {
        this.amt = amt;
    }
}
