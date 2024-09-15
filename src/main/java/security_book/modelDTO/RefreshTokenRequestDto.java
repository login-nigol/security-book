package security_book.modelDTO;

import lombok.Data;

@Data
public class RefreshTokenRequestDto { // Сброс токена.

    private String refreshToken;
}

