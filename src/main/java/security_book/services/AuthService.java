package security_book.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import security_book.enums.AppRole;
import security_book.modelDTO.*;
import security_book.models.AppUser;
import security_book.repositories.AppUserRepository;
import security_book.services.jwt.JwtSecurityService;

import java.util.HashMap;

//AuthService, предоставляет основные функции аутентификации и управления пользователями в приложении. Он включает
// в себя методы для регистрации, входа и обновления токенов..
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtSecurityService jwtSecurityService;
    private final AuthenticationManager authenticationManager;

    public AppUser register(RegisterRequestDto registerRequestDto) { // регистрация пользователя
        AppUser appUser = new AppUser();
        appUser.setEmail(registerRequestDto.getEmail());
        appUser.setFullName(registerRequestDto.getFullName());
        appUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        appUser.setRole(AppRole.USER);

        return appUserRepository.save(appUser);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()));

        AppUser user = appUserRepository
                .findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        String token = jwtSecurityService.generateToken(user);
        String refreshToken = jwtSecurityService.generateRefreshToken(new HashMap<>(), user);

        return LoginResponseDto
                .builder()
                .email(loginRequestDto.getEmail())
                .jwtToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        String jwt = refreshTokenRequestDto.getRefreshToken();
        String email = jwtSecurityService.extractUsername(jwt);
        AppUser user = appUserRepository
                .findByEmail(email)
                .orElseThrow();

        if (jwtSecurityService.validateToken(jwt, user)) {
            RefreshTokenResponseDto refreshTokenResponseDto = new RefreshTokenResponseDto();

            refreshTokenResponseDto
                    .setJwtToken(
                            jwtSecurityService.generateToken(user));

            refreshTokenResponseDto
                    .setRefreshToken(
                            jwtSecurityService.generateRefreshToken(new HashMap<>(), user));

            return refreshTokenResponseDto;
        }
        return null;
    }
}

//Регистрация пользователя:
//Создает нового пользователя с зашифрованным паролем
//Назначает роль USER по умолчанию
//Сохраняет пользователя в базе данных

//Аутентификация пользователя:
//Проверяет учетные данные пользователя
//Генерирует JWT токен и refresh токен
//Возвращает данные для аутентифицированного пользователя

//Обновление токена:
//Проверяет валидность refresh токена
//Генерирует новую пару JWT и refresh токенов

//Сервис использует:

//PasswordEncoder для шифрования паролей
//JwtSecurityService для работы с JWT токенами
//AuthenticationManager для проверки учетных данных
//AppUserRepository для взаимодействия с базой данных пользователей

//Ключевые моменты безопасности:

//Пароли хранятся в зашифрованном виде
//Используется механизм JWT для безопасной аутентификации
//Реализована система refresh токенов для продления сессий