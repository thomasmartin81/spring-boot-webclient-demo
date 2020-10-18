package ch.duerri.frontdemo.web.config;

import ch.duerri.frontdemo.core.ApplicationConstants;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class WebClientConfig {

    @Resource
    private HttpServletRequest request;

    @Bean
    public WebClient createWebClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(2))
                                .addHandlerLast(new WriteTimeoutHandler(2)));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .filter((request, next) -> {
                    ClientRequest filtered = ClientRequest.from(request)
                            .header(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID, getRequestId())
                            .header(HttpHeaders.AUTHORIZATION, getAuthorization())
                            .build();
                    return next.exchange(filtered);
                })
                .build();
    }

    protected String getRequestId() {
        return request.getAttribute(ApplicationConstants.HTTP_HEADER_PARAMETER_REQUEST_ID).toString();
    }

    protected String getAuthorization() {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
