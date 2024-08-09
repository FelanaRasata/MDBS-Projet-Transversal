package itu.mbds.transversal.utils.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String username;
    private String token;

}
