package com.cacib.payment.backend.model;


import com.cacib.payment.backend.model.enumeration.Direction;
import com.cacib.payment.backend.model.enumeration.ProcessedFlowType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Alias is required")
    @Column(name = "alias", nullable = false)
    private String alias;

    @NotBlank(message = "Type is required")
    @Column(name = "type", nullable = false)
    private String type;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    private String application;

    @Enumerated(EnumType.STRING)
    private ProcessedFlowType processedFlowType;

    @NotBlank(message = "Description is required")
    @Column(name = "description", nullable = false)
    private String description;

}
