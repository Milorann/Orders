package kpo.orders.repos;

import kpo.orders.domain.Order;
import kpo.orders.domain.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderDishRepository extends JpaRepository<OrderDish, Integer> {
    List<OrderDish> findAllByOrder(Order orderEntity);
}
