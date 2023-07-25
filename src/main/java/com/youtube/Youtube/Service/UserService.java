package com.youtube.Youtube.Service;


import com.youtube.Youtube.DTO.PlayListDTO;
import com.youtube.Youtube.DTO.UserDTO;

public interface UserService {

    public String createUser(UserDTO userDTO);

    public String loginUser(UserDTO userDTO) throws Exception;
}
