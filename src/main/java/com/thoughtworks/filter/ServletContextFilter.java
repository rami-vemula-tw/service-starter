package com.thoughtworks.filter;

import com.thoughtworks.prometheus.Prometheus;
import io.micrometer.core.instrument.Counter;
import io.prometheus.client.Gauge;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Configuration
public class ServletContextFilter implements Filter {
    @Autowired
    private Prometheus prometheus;

    @Bean
    public ServletContextRequestLoggingFilter logFilter() throws ServletException {
        ServletContextRequestLoggingFilter filter
                = new ServletContextRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setAfterMessagePrefix("PAYMENT ");
        return filter;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put("request.id", String.valueOf(UUID.randomUUID()));
        Gauge paymentRequestTime;
        Counter paymentsCounter;
        paymentsCounter = prometheus.getPaymentsCounter();
        paymentsCounter.increment();
        paymentRequestTime = prometheus.getPaymentRequestTime();
        Gauge.Timer timer = paymentRequestTime.startTimer();
        try {
            filterChain.doFilter(servletRequest, servletResponse);

        } finally {
            timer.setDuration();
        }
    }
}
