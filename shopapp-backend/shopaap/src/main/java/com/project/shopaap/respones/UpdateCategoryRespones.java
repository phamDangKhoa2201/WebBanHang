package com.project.shopaap.respones;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCategoryRespones {
    @JsonProperty("message")
    private String message;

}
