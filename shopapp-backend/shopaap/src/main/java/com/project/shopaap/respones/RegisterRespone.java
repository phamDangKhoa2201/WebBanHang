package com.project.shopaap.respones;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopaap.models.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRespone {
    @JsonProperty("message")
    private String message;
    @JsonProperty("user")
    private User user;
}
