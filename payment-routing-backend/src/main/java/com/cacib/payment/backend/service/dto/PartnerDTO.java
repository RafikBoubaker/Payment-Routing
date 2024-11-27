package com.cacib.payment.backend.service.dto;

import com.cacib.payment.backend.model.enumeration.Direction;
import com.cacib.payment.backend.model.enumeration.ProcessedFlowType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Data
@Setter
@Getter
public class PartnerDTO implements Serializable {

    @NotBlank(message = "Alias is required")
    private String alias;

    @NotBlank(message = "Type is required")
    private String type;

    private Direction direction;
    private String application;
    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description is required")
    private String description;
}
