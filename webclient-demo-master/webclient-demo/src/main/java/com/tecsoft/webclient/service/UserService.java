package com.tecsoft.webclient.service;

import com.tecsoft.webclient.config.UserProps;
import com.tecsoft.webclient.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private UserProps userProps;
    private WebClient webClient;

    public UserService(UserProps userProps, WebClient webClient) {
        this.userProps = userProps;
        this.webClient = WebClient.create(userProps.getBaseurl());
    }

    public User getUser(Integer id){
        log.info("baser url :{}",userProps.getBaseurl());
        User user = webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        return user;
    }
    public List<User> getAllUser(){
        User[] users = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(User[].class)
                .block();
        return Arrays.stream(users).toList();
    }
    public User addUser(User user){
        User responseUser = webClient.post()
                .uri("/")
                .body(Mono.just(user),User.class)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        return responseUser;
    }
}
