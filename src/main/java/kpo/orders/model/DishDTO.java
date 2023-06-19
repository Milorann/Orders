package kpo.orders.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DishDTO {

    private Integer id;

    @NotNull
    @Size(max = 100)
    private String name;

    private String description;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "75.08")
    private BigDecimal price;

    @NotNull
    private Integer quantity;

    @NotNull
    @JsonProperty("isAvailable")
    private Boolean isAvailable;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
