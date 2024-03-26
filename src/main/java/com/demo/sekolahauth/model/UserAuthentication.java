package com.demo.sekolahauth.model;

import com.demo.sekolahauth.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RedisHash(value = "authentication")
@Getter
@Setter
@Builder
public class UserAuthentication {

    @Id
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
    private Long userid;
    private String username;
    @TimeToLive
    private Long timeToLeave;

}
