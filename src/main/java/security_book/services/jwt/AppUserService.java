package security_book.services.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import security_book.models.AppUser;
import security_book.repositories.AppUserRepository;

@Service
@RequiredArgsConstructor
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public UserDetailsService getUserDetailsService() {

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
