package unisinos.unitunes.authentication.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unisinos.unitunes.authentication.domain.entity.CustomUser;
import unisinos.unitunes.authentication.domain.enums.UserType;
import unisinos.unitunes.authentication.infrastructure.repository.CustomUserRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CustomUserService {

  @Autowired
  CustomUserRepository repository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public CustomUser findById(Long id) {
    return repository.findById(id).orElseThrow(this::getExceptionUserNotFound);
  }

  public CustomUser save(CustomUser bean)  {
    Optional<CustomUser> user = repository.findFirstByEmail(bean.getEmail());
    if (user.isPresent()) {
      throw new RuntimeException();
    }
    bean.setCredits(new BigDecimal(0));
    bean.setType(UserType.ROLE_ACADEMIC);
    bean.setPassword(passwordEncoder.encode(bean.getPassword()));
    return repository.save(bean);
  }

  public BigDecimal addCredits(Long id, BigDecimal value)  {
    val user = this.findById(id);
    val currentCredits = user.getCredits().add(value);
    repository.save(user);
    return currentCredits;
  }

  protected RuntimeException getExceptionUserNotFound() {
    return new RuntimeException("USER NOT FOUND");
  }
}
