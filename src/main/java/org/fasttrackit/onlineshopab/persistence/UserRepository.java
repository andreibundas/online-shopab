package org.fasttrackit.onlineshopab.persistence;

import org.fasttrackit.onlineshopab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
