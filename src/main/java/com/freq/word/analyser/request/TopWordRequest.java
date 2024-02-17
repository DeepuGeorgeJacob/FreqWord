package com.freq.word.analyser.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopWordRequest {

    @NotEmpty(message = "File name should not be empty")
    @NotNull(message = "File Field Should not be null")
    private final String fileName;
    @Min(value = 1, message = "K must be positive")
    private final int k;
}
