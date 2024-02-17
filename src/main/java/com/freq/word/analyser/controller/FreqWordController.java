package com.freq.word.analyser.controller;


import com.freq.word.analyser.exception.InvalidArgumentException;
import com.freq.word.analyser.model.Response;
import com.freq.word.analyser.request.TopWordRequest;
import com.freq.word.analyser.service.FreqWordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/freq/word")
@Tag(name = "Word Frequency finder", description = "Find the frequency of the word from the document present in AWS S3 bucket")
public class FreqWordController {

    private final FreqWordService freqWordService;

    @Autowired
    public FreqWordController(final FreqWordService freqWordService) {
        this.freqWordService = freqWordService;
    }

    @PostMapping
    public ResponseEntity<Response<Map<String,Integer>>> postData(@Valid @RequestBody final TopWordRequest request) throws InvalidArgumentException {
        return ResponseEntity.ok(freqWordService.processData(request));
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Response<String>> getData(@PathVariable("fileName") String fileName ) throws InvalidArgumentException {
        return ResponseEntity.ok(freqWordService.getRawData(fileName));
    }
}
