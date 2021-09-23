package unisinos.unitunes.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unisinos.unitunes.authentication.application.configuration.UserSecurity;
import unisinos.unitunes.authentication.domain.entity.CustomUser;
import unisinos.unitunes.authentication.domain.enums.UserType;
import unisinos.unitunes.authentication.infrastructure.repository.CustomUserRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private CustomUserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    CustomUser user = repository.findFirstByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

    Set<UserType> roles;

    if (user.getType().equals(UserType.ROLE_ADMIN)) {
      roles = Stream.of(UserType.ROLE_ADMIN, UserType.ROLE_ACADEMIC).collect(Collectors.toSet());
    } else {
      roles = Stream.of(UserType.ROLE_ADMIN).collect(Collectors.toSet());
    }

    return new UserSecurity(user.getEmail(), user.getPassword(), roles);
  }
}
