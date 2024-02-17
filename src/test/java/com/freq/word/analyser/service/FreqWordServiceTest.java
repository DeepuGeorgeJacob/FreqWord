package com.freq.word.analyser.service;

import com.amazonaws.services.s3.AmazonS3;
import com.freq.word.analyser.exception.InvalidArgumentException;
import com.freq.word.analyser.request.TopWordRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FreqWordServiceTest {

    @Mock
    private AmazonS3 amazonS3;


    @InjectMocks
    private FreqWordService freqWordService;

    @Test
    void very_date_read_and_processed() throws IOException, InvalidArgumentException {

        when(amazonS3.getObjectAsString(null,"test.txt")).thenReturn(readTextFile());
        when(amazonS3.doesObjectExist(null,"test.txt")).thenReturn(true);
        final TopWordRequest topWordRequest = new TopWordRequest("test.txt",3);
        final Map<String,Integer> fileContent = freqWordService.processData(topWordRequest);
        assertThat(fileContent).isNotEmpty();
        assertThat(freqWordService.getRawData()).isNotEmpty();
    }

    private String readTextFile() throws IOException {
        final String fileName = "test.txt";
        final Resource resource = new ClassPathResource(fileName);
        try (InputStream inputStream = resource.getInputStream()) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}