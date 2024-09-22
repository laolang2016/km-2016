package com.laolang.km.framework.web.handler;

import cn.hutool.core.util.StrUtil;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

@Slf4j
public class CustomServletHttpRequestHandler extends DefaultServletHttpRequestHandler {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * Default Servlet name used by Tomcat, Jetty, JBoss, and GlassFish.
     */
    private static final String COMMON_DEFAULT_SERVLET_NAME = "default";

    /**
     * Default Servlet name used by Google App Engine.
     */
    private static final String GAE_DEFAULT_SERVLET_NAME = "_ah_default";

    /**
     * Default Servlet name used by Resin.
     */
    private static final String RESIN_DEFAULT_SERVLET_NAME = "resin-file";

    /**
     * Default Servlet name used by WebLogic.
     */
    private static final String WEBLOGIC_DEFAULT_SERVLET_NAME = "FileServlet";

    /**
     * Default Servlet name used by WebSphere.
     */
    private static final String WEBSPHERE_DEFAULT_SERVLET_NAME = "SimpleFileServlet";

    @Nullable
    private String defaultServletName;

    @Nullable
    private ServletContext servletContext;

    /**
     * Set the name of the default Servlet to be forwarded to for static resource
     * requests.
     */
    public void setDefaultServletName(String defaultServletName) {
        this.defaultServletName = defaultServletName;
    }

    /**
     * If the {@code defaultServletName} property has not been explicitly set,
     * attempts to locate the default Servlet using the known common
     * container-specific names.
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        if (!StringUtils.hasText(this.defaultServletName)) {
            if (this.servletContext.getNamedDispatcher(COMMON_DEFAULT_SERVLET_NAME) != null) {
                this.defaultServletName = COMMON_DEFAULT_SERVLET_NAME;
            } else if (this.servletContext.getNamedDispatcher(GAE_DEFAULT_SERVLET_NAME) != null) {
                this.defaultServletName = GAE_DEFAULT_SERVLET_NAME;
            } else if (this.servletContext.getNamedDispatcher(RESIN_DEFAULT_SERVLET_NAME) != null) {
                this.defaultServletName = RESIN_DEFAULT_SERVLET_NAME;
            } else if (this.servletContext.getNamedDispatcher(WEBLOGIC_DEFAULT_SERVLET_NAME) != null) {
                this.defaultServletName = WEBLOGIC_DEFAULT_SERVLET_NAME;
            } else if (this.servletContext.getNamedDispatcher(WEBSPHERE_DEFAULT_SERVLET_NAME) != null) {
                this.defaultServletName = WEBSPHERE_DEFAULT_SERVLET_NAME;
            } else {
                throw new IllegalStateException("Unable to locate the default servlet for serving static content. "
                        + "Please set the 'defaultServletName' property explicitly.");
            }
        }
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleException(request, response);

        origionHandleRequest(request, response);
    }

    /**
     * 原处理方法
     */
    private void origionHandleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Assert.state(this.servletContext != null, "No ServletContext set");
        RequestDispatcher rd = this.servletContext.getNamedDispatcher(this.defaultServletName);
        if (rd == null) {
            throw new IllegalStateException("A RequestDispatcher could not be located for the default servlet '"
                    + this.defaultServletName + "'");
        }
        rd.forward(request, response);
    }

    /**
     * 处理异常
     */
    private void handleException(HttpServletRequest request, HttpServletResponse response)
            throws IOException, NoHandlerFoundException {
        String requestURI = request.getRequestURI();
        HandlerMethod handlerMethod = findHandlerMethod(requestURI);
        if (Objects.isNull(handlerMethod)) {
            if (StrUtil.startWith(requestURI, "/api")) {
                log.info("接口 404");
                throw new NoHandlerFoundException(request.getMethod(), requestURI, null);
            } else {
                log.info("页面 404");
                if (StrUtil.startWith(requestURI, "/admin")) {
                    response.sendRedirect("/admin/error/404");
                } else if(StrUtil.startWith(requestURI, "/portal")) {
                    response.sendRedirect("/portal/error/404");
                }
                response.sendRedirect("/portal/error/404");
            }
        }
    }

    /**
     * 根据请求地址获取 HandlerMethod
     */
    private HandlerMethod findHandlerMethod(String requestURI) {
        return requestMappingHandlerMapping.getHandlerMethods().entrySet().stream()
                .filter(entry -> entry.getKey().getPatternsCondition().getPatterns().stream()
                        .anyMatch(pattern -> PatternMatchUtils.simpleMatch(pattern, requestURI)))
                .map(Map.Entry::getValue).findFirst().orElse(null);
    }
}
