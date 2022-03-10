package com.project.expensestracker;

public class ModelClass {
    private String desc;
    private long amt, dAndT;

    ModelClass() {
    }

    public ModelClass(String desc, long amt, long dAndT) {
        this.desc = desc;
        this.amt = amt;
        this.dAndT = dAndT;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getAmt() {
        return amt;
    }

    public void setAmt(long amt) {
        this.amt = amt;
    }

    public long getdAndT() {
        return dAndT;
    }

    public void setdAndT(long dAndT) {
        this.dAndT = dAndT;
    }

}
