package security_book.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security_book.modelDTO.*;
import security_book.models.AppUser;
import security_book.services.AuthService;
import security_book.services.jwt.AppUserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    // отвечает за логику регистрации и возвращает объект AppUser
    private final AuthService authService;
    // преобразование объекта из одного типа в другой
//    private final ModelMapper modelMapper;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        // Вызываем сервис для регистрации и маппинга в DTO
        return ResponseEntity.ok(authService.register(registerRequestDto));
//        RegisterResponseDto responseDto = authService.register(registerRequestDto);
//        return ResponseEntity.ok(responseDto);
    }

//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
//        AppUser appUser = authService.register(registerRequestDto);//
//        // маппим объект AppUser в RegisterResponseDto
//        RegisterResponseDto registerResponseDto = modelMapper.map(appUser, RegisterResponseDto.class);//
//        return ResponseEntity.ok(registerResponseDto);
//    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refresh(refreshTokenRequestDto));
    }
}
