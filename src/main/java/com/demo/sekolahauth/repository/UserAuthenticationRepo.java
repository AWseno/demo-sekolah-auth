package com.demo.sekolahauth.repository;

import com.demo.sekolahauth.model.UserAuthentication;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthenticationRepo extends CrudRepository<UserAuthentication, String> {
}
