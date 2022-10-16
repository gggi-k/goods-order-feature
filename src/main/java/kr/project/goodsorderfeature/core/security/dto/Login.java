package kr.project.goodsorderfeature.core.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@NoArgsConstructor(staticName = "create")
@FieldNameConstants
@Getter
@Setter
@ToString
public class Login {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;
}