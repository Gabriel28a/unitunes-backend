package unisinos.unitunes.authentication.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import unisinos.unitunes.authentication.domain.enums.UserType;

import java.math.BigDecimal;

@Entity
@Table(name = "en_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
public class CustomUser {

  private static final long serialVersionUID = -8340367861960919985L;

  @Id
  @Column(name = "user_id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_user_id")
  @SequenceGenerator(name = "se_user_id", sequenceName = "se_user_id", allocationSize = 1)
  @EqualsAndHashCode.Include
  Long id;

  @Size(min = 0, max = 50)
  String firstName;

  @Size(min = 0, max = 50)
  String lastName;

  @Email
  @Size(min = 0, max = 255)
  String email;

  @JsonIgnore
  @Size(min = 0, max = 255)
  String password;

  BigDecimal credits;

  @Enumerated(EnumType.STRING)
  UserType type;
}
