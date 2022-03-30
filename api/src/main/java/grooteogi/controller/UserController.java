package grooteogi.controller;

import grooteogi.domain.EmailCodeRequest;
import grooteogi.domain.EmailRequest;
import grooteogi.domain.User;
import grooteogi.domain.UserDto;
import grooteogi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user")
  public List<User> getAllUser() {
    return userService.getAllUser();
  }

  // 일반 회원가입 중 이메일 인증 버튼 누를 경우 ( 유효성 검사, 이메일 중복 검사 )
  @PostMapping("/user/email/generate")
  public ResponseEntity genarateEmailVerify(@Valid @RequestBody EmailRequest email, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
      }
    else{
      if(userService.genarateEmailVerify(email)) return ResponseEntity.status(HttpStatus.OK).body("success!!");
      else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this email already exists");
    }
  }
   // 일반 회원가입 중 이메일 인증 버튼을 누를 경우 ( 인증 코드 확인 )
  @PostMapping("user/email/confirm")
  public ResponseEntity confirmEmailVerify(@RequestBody EmailCodeRequest emailCodeRequest){
    if(userService.confirmEmailVerify(emailCodeRequest)){
      return ResponseEntity.status(HttpStatus.OK).body("confirm success!!!");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("time out @@@");
  }
  
  
  // 일반 회원가입 중 가입 버튼을 누를 경우 ( 비밀번호 유효성 검사 )
  @PostMapping("user/register")
  public ResponseEntity register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
    }
    return ResponseEntity.status(HttpStatus.OK).body("register success@@");
  }

}
