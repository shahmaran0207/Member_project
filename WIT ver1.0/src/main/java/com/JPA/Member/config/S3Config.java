package com.JPA.Member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.BasicAWSCredentials;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;


    @Bean
    public AmazonS3Client amazonS3Client() {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }

}