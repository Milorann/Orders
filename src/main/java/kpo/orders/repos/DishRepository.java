package kpo.orders.repos;

import kpo.orders.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DishRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByIsAvailable(boolean b);
}
