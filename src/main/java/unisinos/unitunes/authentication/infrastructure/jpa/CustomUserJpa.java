package unisinos.unitunes.authentication.infrastructure.jpa;

import org.springframework.data.repository.CrudRepository;
import unisinos.unitunes.authentication.domain.entity.CustomUser;

import java.util.Optional;

public interface CustomUserJpa extends CrudRepository<CustomUser, Long> {

  Optional<CustomUser> findFirstByEmail(String email);
}
