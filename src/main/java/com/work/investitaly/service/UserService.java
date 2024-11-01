package com.work.investitaly.service;

import com.work.investitaly.model.UserModel;

public interface UserService {

    UserModel save(UserModel user);
    UserModel findById(Long id);
    UserModel findByUsername(String username);
    void deleteById(Long id);
    void delete(UserModel user);

}
