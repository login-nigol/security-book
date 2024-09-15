package security_book.modelDTO;

import lombok.Data;

@Data
public class RefreshTokenResponseDto { // Ответ на сброс токена.

    private String jwtToken;
    private String refreshToken;
}
