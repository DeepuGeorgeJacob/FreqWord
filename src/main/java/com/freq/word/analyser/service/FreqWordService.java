package com.freq.word.analyser.service;

import com.amazonaws.services.s3.AmazonS3;
import com.freq.word.analyser.request.TopWordRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// https://stackoverflow.com/questions/58911797/downloading-aws-s3-file-as-a-stream-in-spring-boot

@Service
public class FreqWordService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonS3 amazonS3;

    @Autowired
    public FreqWordService(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public Map<String, Integer> processData(final TopWordRequest topWordRequest) {
        return getTopKWords(countWordFrequencyParallel( amazonS3.getObjectAsString("mybucket","test.txt")), topWordRequest.getKValue());
    }


    private Map<String, Integer> countWordFrequencyParallel(final String text) {
        final Map<String, Integer> wordFrequency = new ConcurrentHashMap<>();

        final String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        Arrays.asList(words).parallelStream()
                .forEach(word -> wordFrequency.merge(word, 1, Integer::sum));
        return wordFrequency;
    }

    private Map<String, Integer> getTopKWords(Map<String, Integer> wordFrequency, int k) {
        return wordFrequency.entrySet()
                .stream()
                .sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                        .reversed().thenComparing(Map.Entry::getKey))
                .limit(k)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    public String getRawData() {
        return amazonS3.getObjectAsString("mybucket","test.txt");
    }
}
