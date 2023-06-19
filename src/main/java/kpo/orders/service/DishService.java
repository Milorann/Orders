package kpo.orders.service;

import java.time.OffsetDateTime;
import java.util.List;
import kpo.orders.domain.Dish;
import kpo.orders.model.CreateDishForm;
import kpo.orders.model.DishDTO;
import kpo.orders.repos.DishRepository;
import kpo.orders.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(final DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<DishDTO> findAll() {
        final List<Dish> dishs = dishRepository.findAll(Sort.by("id"));
        return dishs.stream()
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .toList();
    }

    public DishDTO get(final Integer id) {
        return dishRepository.findById(id)
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CreateDishForm createDishForm) {
        final Dish dish = new Dish();
        mapToEntity(createDishForm, dish);
        dish.setDateCreated(OffsetDateTime.now());
        dish.setLastUpdated(OffsetDateTime.now());
        return dishRepository.save(dish).getId();
    }

    public void update(final Integer id, final CreateDishForm createDishForm) {
        final Dish dish = dishRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(createDishForm, dish);
        dish.setLastUpdated(OffsetDateTime.now());
        dishRepository.save(dish);
    }

    public void delete(final Integer id) {
        dishRepository.deleteById(id);
    }

    private DishDTO mapToDTO(final Dish dish, final DishDTO dishDTO) {
        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setQuantity(dish.getQuantity());
        dishDTO.setIsAvailable(dish.getIsAvailable());
        dishDTO.setCreatedAt(dish.getDateCreated());
        dishDTO.setUpdatedAt(dish.getLastUpdated());
        return dishDTO;
    }

    private Dish mapToEntity(final CreateDishForm createDishForm, final Dish dish) {
        dish.setName(createDishForm.getName());
        dish.setDescription(createDishForm.getDescription());
        dish.setPrice(createDishForm.getPrice());
        dish.setQuantity(createDishForm.getQuantity());
        dish.setIsAvailable(dish.getQuantity() != 0);
        return dish;
    }

    public List<DishDTO> findAvailable() {
        final List<Dish> dishs = dishRepository.findAllByIsAvailable(true);
        return dishs.stream()
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .toList();
    }

    public void updateQuantity(Dish dish) {
        dish.setLastUpdated(OffsetDateTime.now());
        dishRepository.save(dish);
    }
}
