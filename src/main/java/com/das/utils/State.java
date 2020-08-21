package com.das.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim
 */
public class State {
    private static Map<String,Object> reState(String msg,Integer state){
        Map<String,Object> map = new HashMap<>(2);
        map.put("msg",msg);
        map.put("status",state);
        return map;
    }

    public static Map<String, Object> packet(Object obj,String msg,int state){
        Map<String,Object> ans = new HashMap<>(4);
        ans.put("data",obj);
        ans.put("meta",reState(msg,state));
        return ans;
    }
}
