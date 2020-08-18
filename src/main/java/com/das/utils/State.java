package com.das.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim
 */
public class State {
    public static Map<String,Object> reState(String msg, Integer state){
        Map<String,Object> map = new HashMap<>(2);
        map.put("msg",msg);
        map.put("status",state);
        return map;
    }
}
