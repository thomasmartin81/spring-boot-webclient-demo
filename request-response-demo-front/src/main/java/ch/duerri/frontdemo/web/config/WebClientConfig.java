package ch.duerri.frontdemo.web.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient createWebClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(2))
                                .addHandlerLast(new WriteTimeoutHandler(2)));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
