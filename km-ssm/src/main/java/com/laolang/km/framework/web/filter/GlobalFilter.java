package com.laolang.km.framework.web.filter;

import cn.hutool.core.util.IdUtil;
import com.laolang.km.framework.common.consts.GloablConsts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class GlobalFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            MDC.put(GloablConsts.TID_NAME, IdUtil.fastSimpleUUID());
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
