package unisinos.unitunes.authentication.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CredentialDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private String email;
  private String password;
  private String accessToken;
}
