package com.webengineering.fratcher.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AddressService  {
    @Value("${addressService.address}")
    private String serverAddress;

    /**
     * Return server URL.
     *
     * @return server URL.
     */
    public String getServerURL() {
        return serverAddress;
    }
}
