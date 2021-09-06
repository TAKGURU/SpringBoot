package com.example.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
class UserInfoControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void beforeEach() {
        objectMapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modules(new JavaTimeModule())
            .build();
    }
    
    // 유저 등록 테스트
    @Test
    public void setTest() throws Exception {
    	UserInfoDto userInfoDto = new UserInfoDto();
    	userInfoDto.setName("tak7");
    	userInfoDto.setAge(12);

        String content = objectMapper.writeValueAsString(userInfoDto);
        log.info(content);

        String url = "/userinfo";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> { 
            	MockHttpServletResponse response = result.getResponse();
                log.info(response.getContentAsString());
            });
    }
    
    // 유저 조회 테스트
    @Test
    public void getTest() throws Exception {
        String url = "/userinfo/tak7";
        mockMvc.perform(MockMvcRequestBuilders.get(url)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(result -> {
                MockHttpServletResponse response = result.getResponse();
                log.info(response.getContentAsString());
            });
    }
}
