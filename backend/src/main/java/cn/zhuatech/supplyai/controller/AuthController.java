/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.supplyai.controller;
import cn.zhuatech.supplyai.common.ApiResponse; import cn.zhuatech.supplyai.dto.AuthDto.*; import cn.zhuatech.supplyai.repository.UserRepository; import cn.zhuatech.supplyai.security.JwtService; import cn.zhuatech.supplyai.service.CurrentUserService; import jakarta.validation.Valid; import org.springframework.security.authentication.*; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") public class AuthController {
    private final AuthenticationManager auth;private final JwtService jwt;private final UserRepository users;private final CurrentUserService current;
    public AuthController(AuthenticationManager auth,JwtService jwt,UserRepository users,CurrentUserService current){this.auth=auth;this.jwt=jwt;this.users=users;this.current=current;}
    @PostMapping("/login") public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request){auth.authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));var user=users.findByUsername(request.username()).orElseThrow();return ApiResponse.ok("登录成功",new LoginResponse(jwt.generate(user.getUsername()),UserView.from(user)));}
    @GetMapping("/me") public ApiResponse<UserView> me(){return ApiResponse.ok(UserView.from(current.get()));}
}
