package unisinos.unitunes.authentication.infrastructure.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import unisinos.unitunes.authentication.domain.entity.CustomUser;
import unisinos.unitunes.authentication.infrastructure.jpa.CustomUserJpa;

import java.util.Optional;

@Repository
public class CustomUserRepository {

  @Autowired
  CustomUserJpa jpa;

  public Optional<CustomUser> findById(Long id) {
    return jpa.findById(id);
  }

  public Optional<CustomUser> findFirstByEmail(String email) {
    return jpa.findFirstByEmail(email);
  }

  public CustomUser save(CustomUser user) {
    return jpa.save(user);
  }
}
