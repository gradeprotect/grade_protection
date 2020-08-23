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

    /**
     * 打包数据
     * @param obj 待发送的数据对象
     * @param msg 提示信息
     * @param state 状态码
     * @return
     */
    public static Map<String, Object> packet(Object obj,String msg,int state){
        Map<String,Object> ans = new HashMap<>(4);
        ans.put("data",obj);
        ans.put("meta",reState(msg,state));
        return ans;
    }
}
