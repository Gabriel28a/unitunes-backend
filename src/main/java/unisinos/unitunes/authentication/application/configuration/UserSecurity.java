package unisinos.unitunes.authentication.application.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import unisinos.unitunes.authentication.domain.enums.UserType;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserSecurity implements UserDetails {

  private static final long serialVersionUID = 1L;

  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSecurity(String email, String password, Set<UserType> roles) {
    super();
    this.email = email;
    this.password = password;
    this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.toString()))
        .collect(Collectors.toList());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
