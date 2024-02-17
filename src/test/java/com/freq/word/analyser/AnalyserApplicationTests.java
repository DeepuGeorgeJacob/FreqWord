package com.freq.word.analyser;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
@Testcontainers
@AutoConfigureMockMvc
class AnalyserApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AmazonS3 amazonS3;

    @Container
    private static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
            .withCopyFileToContainer(MountableFile.forClasspathResource("script.sh", 0775),
                    "/etc/localstack/init/ready.d/")
            .withServices(LocalStackContainer.Service.S3);

    @BeforeAll
    public static void beforeAll() throws IOException, InterruptedException {
        System.setProperty("aws.s3.service.endpoint", "http://s3.localhost.localstack.cloud:" + localStackContainer.getMappedPort(4566));
    }


   @Test
    void postVerify() throws Exception {
        amazonS3.putObject("freqwordbucket", "test.txt", readTextFile());
        mockMvc.perform(MockMvcRequestBuilders.post("/freq/word")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"fileName\": \"test.txt\", \"k\":\"1\" }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getVerify() throws Exception {
        amazonS3.putObject("freqwordbucket", "test.txt", readTextFile());

        String s3Response = amazonS3.getObjectAsString("freqwordbucket", "test.txt");
        assertThat(s3Response).isEqualTo(readTextFile());

		mockMvc.perform(MockMvcRequestBuilders.get("/freq/word")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
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
