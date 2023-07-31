package com.youtube.Youtube.Service.Implementation;

import Utilities.JwtUtil;
import Utilities.Utils;
import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.DTO.UserDTO;
import com.youtube.Youtube.Entity.User;
import com.youtube.Youtube.PersistenceService.ObjectifyInitializer;
import com.youtube.Youtube.Repository.UserRepository;
import com.youtube.Youtube.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectifyInitializer objectifyInitializer;

    @Override
    public String createUser(UserDTO userDTO) {

        User user = User.builder()
                .userEmail(userDTO.getUserEmail())
                .userId(UUID.randomUUID().toString())
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword())
                .build();

        userRepository.save(user);
        return JwtUtil.generateToken(user.getUserId());
    }

    @Override
    public String loginUser(UserDTO userDTO) throws Exception {
        User user ;
        user = userRepository.findByUserEmail("userDTO.getUserEmail()");

//        ObjectifyInitializer.init();
        User user1 = ObjectifyInitializer.ofy().load().type(User.class)
                .filter("userEmail",userDTO.getUserEmail()).first().now();

//        System.out.println(user1.toString());

        if(user1 == null)  {
            throw new Exception("User Not Found");
        }else if (!user1.getPassword().equals(userDTO.getPassword())) {
            throw new Exception("Invalid Password");
        }
        return JwtUtil.generateToken(user1.getUserId());
    }
}
