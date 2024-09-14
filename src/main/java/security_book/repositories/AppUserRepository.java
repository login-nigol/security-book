package security_book.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import security_book.models.AppUser;


    @Repository
    public interface AppUserRepository extends JpaRepository<AppUser, Integer>{
        Optional<AppUser> findByEmail(String email);
    }

