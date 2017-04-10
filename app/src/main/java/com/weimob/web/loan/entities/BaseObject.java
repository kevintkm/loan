package com.weimob.web.loan.entities;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by chris on 15/9/15.
 */
public class BaseObject implements Serializable {

    public String toJson(){
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
