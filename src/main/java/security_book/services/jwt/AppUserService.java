package security_book.services.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import security_book.models.AppUser;
import security_book.repositories.AppUserRepository;

// Класс AppUserService предоставляет реализацию UserDetailsService для аутентификации пользователей в Spring Security.
// Он загружает данные пользователя из базы данных по email и возвращает объект UserDetails, необходимый для проверки
// учетных данных и авторизации.
@Service
@RequiredArgsConstructor
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public UserDetailsService getDetailsService() {

        UserDetailsService detailsService = new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser user = appUserRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
                return user;
            }
        };
        return detailsService;
    }
}

// Класс использует AppUserRepository для поиска пользователя в базе данных и преобразует найденного AppUser в UserDetails.

// Метод getDetailsService() возвращает анонимную реализацию UserDetailsService, которую можно использовать в
// конфигурации Spring Security.