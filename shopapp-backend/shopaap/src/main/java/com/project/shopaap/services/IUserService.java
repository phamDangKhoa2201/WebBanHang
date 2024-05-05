package com.project.shopaap.services;

import com.project.shopaap.dtos.UserDTO;
import com.project.shopaap.exceptions.DataNotFoundException;
import com.project.shopaap.models.User;



public interface IUserService {
    User createUser(UserDTO userDTO)throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}
