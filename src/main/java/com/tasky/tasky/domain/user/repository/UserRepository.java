package com.tasky.tasky.domain.user.repository;

import com.tasky.tasky.domain.user.entity.Provider;
import com.tasky.tasky.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByProviderIdAndProvider(String providerId, Provider provider);

    Optional<Users> findByProviderId(String providerId);
}