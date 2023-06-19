package kpo.orders.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import kpo.orders.model.DishDTO;
import kpo.orders.model.OrderDishDTO;
import kpo.orders.service.DishService;
import kpo.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class Menu {

    private final DishService dishService;

    public Menu(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishs() {
        return ResponseEntity.ok(dishService.findAvailable());
    }
}
