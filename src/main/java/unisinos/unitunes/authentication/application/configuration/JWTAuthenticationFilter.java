package unisinos.unitunes.authentication.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import unisinos.unitunes.authentication.domain.dto.CredentialDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager manager;
  private JWTUtil jwt;

  public JWTAuthenticationFilter(AuthenticationManager manager, JWTUtil jwt) {
    setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
    this.manager = manager;
    this.jwt = jwt;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {

    try {
      val credentials = new ObjectMapper().readValue(req.getInputStream(), CredentialDTO.class);

      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>());

      Authentication auth = manager.authenticate(authToken);
      return auth;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException {

    String username = ((UserSecurity) auth.getPrincipal()).getUsername();
    String token = jwt.generateToken(username);
    res.addHeader("Authorization", "Bearer " + token);
    res.addHeader("access-control-expose-headers", "Authorization");
    Map<String, Object> body = new HashMap<>();
    body.put("access_token", "Bearer "+token);
    body.put("logged_with", ((UserSecurity) auth.getPrincipal()).getUsername());
    body.put("message", "Sessão iniciada com sucesso");
    res.getWriter().write(new ObjectMapper().writeValueAsString(body));
    res.setStatus(200);
    res.setContentType("application/json");
  }

  private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                          AuthenticationException exception) throws IOException, ServletException {
      response.setStatus(401);
      response.setContentType("application/json");
      response.getWriter().append(json());
    }

    private String json() {
      long date = new Date().getTime();
      return "{\"timestamp\": " + date + ", "
        + "\"status\": 401, "
        + "\"error\": \"Não autorizado\", "
        + "\"message\": \"Email ou senha inválidos\", "
        + "\"path\": \"/login\"}";
    }
  }
}
