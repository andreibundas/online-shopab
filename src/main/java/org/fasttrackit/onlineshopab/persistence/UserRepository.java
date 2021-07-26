package org.fasttrackit.onlineshopab.persistence;

import org.fasttrackit.onlineshopab.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    Page<User> findByFirstNameContains(String partialFirstName, Pageable pageable);

    Page<User> findByLastNameContains(String partialLastName, Pageable pageable);

    Page<User> findByFirstNameContainsAndLastNameContains(String partialFirstName, String partialLastName, Pageable pageabel);


}
