package com.gbaraujosouza.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Configuration
public class AWSConfig {
    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public S3Client createS3Instance() {
        return S3Client.builder().region(Region.of(awsRegion)).build();
    }
}
