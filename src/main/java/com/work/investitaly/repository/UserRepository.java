package com.work.investitaly.repository;

import com.work.investitaly.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

}
