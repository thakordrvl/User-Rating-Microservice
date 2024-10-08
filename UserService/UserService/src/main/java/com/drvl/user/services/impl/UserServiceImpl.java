package com.drvl.user.services.impl;

import com.drvl.user.entities.Hotel;
import com.drvl.user.entities.Rating;
import com.drvl.user.entities.User;
import com.drvl.user.exceptions.ResourceNotFoundException;
import com.drvl.user.external.service.HotelService;
import com.drvl.user.external.service.RatingService;
import com.drvl.user.repositories.UserRepository;
import com.drvl.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;
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
//            // Fetch the list of ratings for the user using ParameterizedTypeReference
//            ResponseEntity<List<Rating>> responseEntity = restTemplate.exchange(
//                    "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<Rating>>() {}
//            );
//
//            List<Rating> ratingsOfUser = responseEntity.getBody();
            List<Rating> ratingsOfUser = ratingService.getRatingsByUserId(user.getUserId());

            if (ratingsOfUser != null) {
                // Fetch and set the hotel for each rating
                ratingsOfUser.forEach(rating -> {
//                    Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                    Hotel hotel = hotelService.getHotel(rating.getHotelId());
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

//        ResponseEntity<List<Rating>> responseEntity = restTemplate.exchange(
//                "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Rating>>() {}
//        );
//
//        List<Rating> ratingsOfUser = responseEntity.getBody();
        List<Rating> ratingsOfUser = ratingService.getRatingsByUserId(user.getUserId());

        if (ratingsOfUser != null) {
            // Fetch and set the hotel for each rating
            ratingsOfUser.forEach(rating -> {
//                Hotel hotel = restTemplate.getForObject("http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);
            });

            user.setRatings(ratingsOfUser);
        }

        return user;
    }
}
