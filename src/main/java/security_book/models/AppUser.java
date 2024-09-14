package security_book.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import security_book.enums.AppRole;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String email;
    private String password;
    private AppRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // Этот метод возвращает коллекцию прав (ролей) пользователя.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() { // Возвращает уникальный идентификатор пользователя.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { // Показывает, не истек ли срок действия аккаунта пользователя.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // Определяет, не заблокирован ли аккаунт пользователя.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // Показывает, не истек ли срок действия учетных данных (обычно пароля) пользователя.
        return true;
    }

    @Override
    public boolean isEnabled() { // Определяет, активен ли аккаунт пользователя.
        return true;
    }
}

// Метод getAuthorities() с аннотацией @Override реализует интерфейс UserDetails в Spring Security и отвечает за
// предоставление полномочий (authorities) пользователя для системы безопасности. Этот метод возвращает коллекцию
// объектов, реализующих интерфейс GrantedAuthority, которые описывают права доступа пользователя (например, его роли).
// Collection<? extends GrantedAuthority>: Метод возвращает коллекцию объектов, которые реализуют интерфейс
// GrantedAuthority. Этот интерфейс используется Spring Security для представления полномочий, которые связаны с
// пользователем (например, роли или привилегии).

// List.of(...): Это метод который создает неизменяемый список (в данном случае из одного элемента).
// Здесь создается список, содержащий один элемент — объект SimpleGrantedAuthority.

// new SimpleGrantedAuthority(role.name()):
//
//SimpleGrantedAuthority — это класс, предоставляемый Spring Security, который реализует интерфейс GrantedAuthority.
// Он используется для представления одной полномочия или роли (например, "ROLE_ADMIN" или "ROLE_USER").
//role.name() — это вызов метода name() у перечисления (enum) role. Метод возвращает строковое представление имени
// текущей роли. Если role является перечислением, как в вашем примере с AppRole, то это будет либо "ADMIN", либо "USER".
//Таким образом, если у пользователя роль ADMIN, то этот метод вернет объект SimpleGrantedAuthority("ADMIN"), который
// Spring Security интерпретирует как полномочие/роль пользователя.


















