package com.webengineering.fratcher.util;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
@Configuration
public class AddressService implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private int port;

    /**
     * Return the host address as an IP address.
     *
     * @return address
     */
    public String getHostAddress() {
        return InetAddress.getLoopbackAddress().getHostAddress();
    }

    /**
     * Return the host address as a DNS-resolvable name.
     *
     * @return address
     */
    public String getHostName() {
        return InetAddress.getLoopbackAddress().getHostName();
    }

    /**
     * This method is called when a particular event (noted in the interface) is executed. In our case, the event
     * was the start of the embedded application container as statet in the generic parameter.
     *
     * @param event the occurred event
     */
    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        port = event.getEmbeddedServletContainer().getPort();
    }

    /**
     * Return the port of the application.
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Return server URL with http:// prefix.
     *
     * @return server URL.
     */
    public String getServerURL() {
        return "http://" + getHostName() + ":" + getPort();
    }
}
