package com.mycompany.clientshell.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    Gson gson() {
        return new Gson();
    }

}
