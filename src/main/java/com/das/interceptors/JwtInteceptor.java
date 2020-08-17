package com.das.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.das.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim
 */
public class JwtInteceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map = new HashMap<>(16);
        //获取请求头中的令牌
        String token = request.getHeader("token");

        try{
            //验证令牌
            JwtUtils.verify(token);
            //放行请求
            return true;
        }catch (SignatureVerificationException e){//签名不一致
            e.printStackTrace();
            map.put("msg","签名不一致");
        }catch (TokenExpiredException e){//过期
            e.printStackTrace();
            map.put("msg","令牌过期");
        }catch (AlgorithmMismatchException e){//算法不匹配
            e.printStackTrace();
            map.put("msg","算法不匹配");
        }catch (InvalidClaimException e){//失效的payload异常
            e.printStackTrace();
            map.put("msg","失效的payload异常");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","无效签名");
        }
        //设置状态
        map.put("state",false);
        //将map转位json
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
