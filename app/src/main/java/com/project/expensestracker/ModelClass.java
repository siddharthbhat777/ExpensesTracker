package com.project.expensestracker;

public class ModelClass {
    String desc, amt;

    ModelClass(){}

    public ModelClass(String desc, String amt) {
        this.desc = desc;
        this.amt = amt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
