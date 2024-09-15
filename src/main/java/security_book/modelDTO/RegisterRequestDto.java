package security_book.modelDTO;

import lombok.Data;

@Data
public class RegisterRequestDto { // Регистрация пользователя.

    private String fullName;
    private String email;
    private String password;
}

