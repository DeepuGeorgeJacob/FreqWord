package com.freq.word.analyser.service;

import com.amazonaws.services.s3.AmazonS3;
import com.freq.word.analyser.exception.InvalidArgumentException;
import com.freq.word.analyser.request.TopWordRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    @Value("${aws.bucket.name}")
    private String buketName;

    private final AmazonS3 amazonS3;

    @Autowired
    public FreqWordService(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Cacheable("wordFrequencyCache")
    public Map<String, Integer> processData(final TopWordRequest topWordRequest) throws InvalidArgumentException {
        if (!amazonS3.doesObjectExist(buketName, topWordRequest.getFileName())) {
            throw new InvalidArgumentException(topWordRequest.getFileName()+ " Is not found");
        }
        return getTopKWords(countWordFrequencyParallel(amazonS3.getObjectAsString(buketName, topWordRequest.getFileName())), topWordRequest.getK());
    }


    @Cacheable("wordFrequencyCache")
    private Map<String, Integer> countWordFrequencyParallel(final String text) {
        final Map<String, Integer> wordFrequency = new ConcurrentHashMap<>();

        final String[] words = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        Arrays.asList(words).parallelStream()
                .forEach(word -> wordFrequency.merge(word, 1, Integer::sum));
        return wordFrequency;
    }

    @Cacheable("wordFrequencyCache")
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
        return amazonS3.getObjectAsString(buketName, "test.txt");
    }
}
