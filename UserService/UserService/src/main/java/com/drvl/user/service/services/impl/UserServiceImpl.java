package com.drvl.user.service.services.impl;

import com.drvl.user.service.entities.Hotel;
import com.drvl.user.service.entities.Rating;
import com.drvl.user.service.entities.User;
import com.drvl.user.service.exceptions.ResourceNotFoundException;
import com.drvl.user.service.repositories.UserRepository;
import com.drvl.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        String randomuserId = UUID.randomUUID().toString();
        user.setUserId(randomuserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> listOfUser = userRepository.findAll();

        listOfUser.forEach(user -> {
            // Fetch the list of ratings for the user using ParameterizedTypeReference
            ResponseEntity<List<Rating>> responseEntity = restTemplate.exchange(
                    "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rating>>() {}
            );

            List<Rating> ratingsOfUser = responseEntity.getBody();

            if (ratingsOfUser != null) {
                // Fetch and set the hotel for each rating
                ratingsOfUser.forEach(rating -> {
                    Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                    rating.setHotel(hotel);
                });

                user.setRatings(ratingsOfUser);
            }
        });

        return listOfUser;
    }

    @Override
    public User getUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with given id is not found on server !! : " +
                        userId));

        ResponseEntity<List<Rating>> responseEntity = restTemplate.exchange(
                "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {}
        );

        List<Rating> ratingsOfUser = responseEntity.getBody();

        if (ratingsOfUser != null) {
            // Fetch and set the hotel for each rating
            ratingsOfUser.forEach(rating -> {
                Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                rating.setHotel(hotel);
            });

            user.setRatings(ratingsOfUser);
        }

        return user;
    }
}
