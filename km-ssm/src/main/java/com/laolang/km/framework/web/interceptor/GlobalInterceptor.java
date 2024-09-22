package com.laolang.km.framework.web.interceptor;

import com.laolang.km.framework.web.threadlocal.GlobalInterceptorContext;
import com.laolang.km.framework.web.threadlocal.GlobalThreadLocal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        GlobalThreadLocal.setContext(new GlobalInterceptorContext(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        log.info("{} 耗时:{} ms", request.getRequestURL().toString(), GlobalThreadLocal.getWatchTime());
        GlobalThreadLocal.removeContext();
    }
}
