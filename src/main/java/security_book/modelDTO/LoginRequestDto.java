package security_book.modelDTO;

import lombok.Data;

@Data
public class LoginRequestDto { // Логин пользователя.

    private String email;
    private String password;
}

