package com.tecsoft.webclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsoft.webclient.config.UserProps;
import com.tecsoft.webclient.model.User;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static MockWebServer mockWebServer;
    private static ObjectMapper mapper;
    private  static UserService userService;
    @BeforeAll
    @SneakyThrows
    static void setUp(){
        mockWebServer =new MockWebServer();
        mockWebServer.start();
        mapper = new ObjectMapper();
        String baseUrl = String.format("http://localhost:%s",mockWebServer.getPort());
        UserProps userProps = new UserProps();
        userProps.setBaseurl(baseUrl);
        userService = new UserService(userProps, WebClient.builder().build());

    }
    @AfterAll
    @SneakyThrows
    static void tearDown(){
        mockWebServer.shutdown();
    }

    @Test
    @SneakyThrows
    public void getUserTest(){
        User mockUser  = new User(1,"Maharana","Pratap",89);
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(mockUser))
                .addHeader("Content-Type","application/json"));

        User user = userService.getUser(1);
        assertEquals(mockUser.getFirstName(),user.getFirstName());
    }
    @Test
    @SneakyThrows
    public void getAllUserTest(){
        List<User> mockUsers = Arrays.asList(
                new User(1,"Maharana","Pratap",89),
                new User(2,"Shivaji","Maharaj",89));
        //User mockUser  = new User(1,"Maharana","Pratap",89);
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(mockUsers))
                .addHeader("Content-Type","application/json"));

        List<User> users = userService.getAllUser();
        assertEquals(mockUsers.size(), users.size());
    }
    @Test
    @SneakyThrows
    public void addUserTest(){
        User newUser  = new User(null,"Maharana","Pratap",89);
        User mockUserResponse =new User(111,"Maharana","Pratap",89);
        mockWebServer.enqueue(new MockResponse().setBody(mapper.writeValueAsString(mockUserResponse))
                .addHeader("Content-Type","application/json"));

        User user = userService.addUser(newUser);
        assertEquals(mockUserResponse.getId(),user.getId());
    }
}
