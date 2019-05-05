package com.us.demo.util;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

public enum Singleton {
    INSTANCE;
    private RestTemplate restTemplate;
    private Gson gson;

    Singleton()
    {
        this.gson = new Gson();
        this.restTemplate = new RestTemplate();
    }

    public Gson getGson() {
        return gson;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
