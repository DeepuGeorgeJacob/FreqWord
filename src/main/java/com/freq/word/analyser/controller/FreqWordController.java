package com.freq.word.analyser.controller;


import com.freq.word.analyser.request.TopWordRequest;
import com.freq.word.analyser.service.FreqWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/freq/word")
public class FreqWordController {

    private final FreqWordService freqWordService;

    @Autowired
    public FreqWordController(final FreqWordService freqWordService) {
        this.freqWordService = freqWordService;
    }

    @PostMapping
    public ResponseEntity<Map<String,Integer>> postData(@RequestBody final TopWordRequest request) {
        return ResponseEntity.ok(freqWordService.processData(request));
    }

    @GetMapping
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok(freqWordService.getRawData());
    }
}
