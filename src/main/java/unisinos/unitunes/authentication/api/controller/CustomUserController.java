package unisinos.unitunes.authentication.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import unisinos.unitunes.authentication.domain.entity.CustomUser;
import unisinos.unitunes.authentication.service.CustomUserService;

import javax.validation.Valid;
import java.math.BigDecimal;

@RequestScope
@RestController
@RequestMapping("/user")
public class CustomUserController {

  @Autowired
  private CustomUserService service;

  @GetMapping("/{id}")
  @ResponseBody
  public CustomUser findById(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Long save(@Valid @RequestBody CustomUser user) {
    return service.save(user).getId();
  }

  @PostMapping("/credits/{id}")
  @ResponseBody
  public void addCredits(@PathVariable("id") Long id, @RequestBody BigDecimal value) {
    service.addCredits(id, value);
  }
}
