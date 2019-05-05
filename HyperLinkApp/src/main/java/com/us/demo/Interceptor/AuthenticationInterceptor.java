package com.us.demo.Interceptor;

import com.us.demo.util.JwtUtil;
import com.us.demo.dao.UserRepository;
import com.us.demo.annotation.PassToken;
import com.us.demo.annotation.UserLoginToken;
import com.us.demo.entity.User;
import com.us.demo.security.UserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

//自定义拦截器  记得要注册
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    private UserRepository userRepository;

    @Resource
    private UserDetailService userDetailsService;

    @Resource
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "token");


        String token = request.getHeader("token");
        logger.info("进入拦截器");
        if(!(handler instanceof HandlerMethod))
        {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();


        if(method.isAnnotationPresent(PassToken.class))
        {
            logger.info("有pass注解 直接放行");
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required())
            {
                return true;
            }
        }

        if(method.isAnnotationPresent(UserLoginToken.class))
        {
            logger.info("有UserLoginToken注解需要验证\"");
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required())
            {
                if(token == null)
                {
                    throw new RuntimeException("无token请重新登录");
                }
                Map<String,Object>claims = new HashMap<>();
                try {
                    claims = JwtUtil.parseJWT(token);
                }catch (Exception e)
                {
                    throw new RuntimeException("401");
                }
                String username = (String) claims.get("username");
                User user = userRepository.findByUsername(username);
                if(user == null)
                {
                    throw new RuntimeException("用户不存在 请重新登录");
                }
                try {
                    JwtUtil.isVerify(token);
                }catch (Exception e)
                {
                    throw new RuntimeException("401 token已过期");
                }
                //调用这个方法刷新一次token
                logger.info("进入 spring security 的验证中");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.info("去redis中拿 token " + username);
//                redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));
                String redistoken = (String) redisTemplate.opsForValue().get(username);
                if(userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null && redistoken != null)
                {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

                return true;

            }
        }

        logger.info("离开拦截器");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
