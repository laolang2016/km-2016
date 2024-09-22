package com.laolang.km.framework.web.threadlocal;

import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.StopWatch;

@Data
public class GlobalInterceptorContext {

    private HttpServletRequest request;

    private StopWatch stopWatch;

    public GlobalInterceptorContext() {
    }

    public GlobalInterceptorContext(HttpServletRequest request) {
        this.request = request;
        stopWatch = new StopWatch(request.getRequestURL().toString());
        stopWatch.start();
    }
}
