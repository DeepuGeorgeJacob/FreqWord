package com.freq.word.analyser.request;

import lombok.Data;

@Data
public class TopWordRequest {
    private final String fileUrl;
    private final int kValue;
}
