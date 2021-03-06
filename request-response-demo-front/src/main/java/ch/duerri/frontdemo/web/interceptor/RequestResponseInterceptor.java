package ch.duerri.frontdemo.web.interceptor;

import ch.duerri.frontdemo.core.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component
public class RequestResponseInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = Optional.ofNullable(request.getHeader(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID)).orElse(UUID.randomUUID().toString());
        request.setAttribute(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID, requestId);
        response.setHeader(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID, requestId);

        logger.info("Incoming request. method={}, uri={}, requestId={}", request.getMethod(), getFullRequestUri(request), requestId);
        return true;
    }

    private String getFullRequestUri(HttpServletRequest request) {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append(request.getRequestURL().toString());
        if (request.getQueryString() != null) {
            uriBuilder.append("?").append(request.getQueryString());
        }
        return uriBuilder.toString();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String requestId = request.getAttribute(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID).toString();
        logger.info("Outgoing response. method={}, uri={}, requestId={}, statusCode={}", request.getMethod(), getFullRequestUri(request), requestId, response.getStatus());
    }
}
