package com.tasky.tasky.domain.users.repository;

import com.tasky.tasky.domain.users.entity.Provider;
import com.tasky.tasky.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUidAndProvider(String uid, Provider provider);

    Optional<Users> findByUid(String uid);

}
