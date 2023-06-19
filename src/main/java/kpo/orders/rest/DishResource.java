package kpo.orders.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;

import kpo.orders.model.CreateDishForm;
import kpo.orders.model.DishDTO;
import kpo.orders.service.DishService;
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
@RequestMapping(value = "/api/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = "Authorization")
public class DishResource {

    private final DishService dishService;

    public DishResource(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishs() {
        return ResponseEntity.ok(dishService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDish(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(dishService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDish(@RequestBody @Valid final CreateDishForm createDishForm) {
        final Integer createdId = dishService.create(createDishForm);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDish(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final CreateDishForm createDishForm) {
        dishService.update(id, createDishForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDish(@PathVariable(name = "id") final Integer id) {
        dishService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
