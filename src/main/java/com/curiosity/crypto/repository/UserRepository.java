package com.curiosity.crypto.repository;

import com.curiosity.crypto.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
