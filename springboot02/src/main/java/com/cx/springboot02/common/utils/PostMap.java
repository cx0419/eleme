package com.cx.springboot02.common.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class PostMap<K,V>  extends LinkedHashMap<K,V> {

    @Override
    public V get(Object key) {
        return super.get(key.toString());
    }

    public <T> T get(Object key, Class<T> cls){
        return cls.cast(get(key));
    }
}
