package security_book.modelDTO;

import lombok.Data;
import security_book.models.AppUser;

@Data
public class RegisterResponseDto {

//    private Integer id;
    private String fullName;
    private String email;
    private String jwtToken;

    // Статический метод для создания DTO из AppUser
//    public static RegisterResponseDto fromAppUser(AppUser appUser) {
//        RegisterResponseDto dto = new RegisterResponseDto();
//
//        dto.setId(appUser.getId());
//        dto.setFullName(appUser.getFullName());
//        dto.setEmail(appUser.getEmail());
//        dto.setRole(appUser.getRole().name());
//
//        return dto;
//    }
}
