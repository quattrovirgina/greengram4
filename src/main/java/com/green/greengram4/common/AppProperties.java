package com.green.greengram4.common;

import com.green.greengram4.Greengram4Application;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public class Jwt {
        private String secret; // DaeguGreenArtAcademy...
        private String headerSchemeName; // authorization
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenExpiry = refreshTokenExpiry / 1000;
        }


    }
}
