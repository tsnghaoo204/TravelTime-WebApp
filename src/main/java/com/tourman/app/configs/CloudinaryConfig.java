package com.tourman.app.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    private String cloudName;
    @Value(value = "${CLOUDINARY_API_KEY}")
    private String apiKey;

    @Value(value = "${CLOUDINARY_API_SECRET}")
    private String apiSecret;

    @Value(value = "${CLOUDINARY_NAME}")
    private String getCloudName;
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", getCloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }
}