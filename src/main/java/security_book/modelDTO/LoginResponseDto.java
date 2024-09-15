package security_book.modelDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto { // Ответ на запрос логина

    private String email;
    private String jwtToken;
    private String refreshToken;
}
