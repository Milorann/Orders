package kpo.orders.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class CreateOrderForm {

    private String specialRequests;

    List<CreateOrderDishForm> dishes;

}
