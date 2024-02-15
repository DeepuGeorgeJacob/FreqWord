package com.freq.word.analyser.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfiguration {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.s3.service.endpoint}")
    private String serviceEndPoint;

    // https://docs.aws.amazon.com/general/latest/gr/s3.html

    @Bean
    public AmazonS3 initS3Client() {
        final AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        final AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(serviceEndPoint, Regions.EU_CENTRAL_1.getName());
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(config)
                .build();
    }


}
