package com.zcy.ygs.retrofitdemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ygs on 2018/5/5.
 */

public class GithubRepos {
    public int id;
    public String name;
    public List<Follow> follows=new ArrayList<>();
    public class Follow{
        public String name;
        public int age;
    }
}
