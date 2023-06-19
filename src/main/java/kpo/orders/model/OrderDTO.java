package kpo.orders.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Integer id;

    @NotNull
    @Size(max = 50)
    private String status;

    private String specialRequests;

    List<OrderDishDTO> dishes;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
