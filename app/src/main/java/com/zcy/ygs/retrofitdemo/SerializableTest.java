package com.zcy.ygs.retrofitdemo;

import java.io.Serializable;

/**
 * Created by ygs on 2018/5/8.
 */

public class SerializableTest implements Serializable {
    private static final long serialVersionUID=1l;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
