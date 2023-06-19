package kpo.orders.service;

import java.util.List;

import kpo.orders.domain.Dish;
import kpo.orders.domain.Order;
import kpo.orders.domain.OrderDish;
import kpo.orders.model.CreateOrderDishForm;
import kpo.orders.model.OrderDishDTO;
import kpo.orders.repos.DishRepository;
import kpo.orders.repos.OrderDishRepository;
import kpo.orders.repos.OrderRepository;
import kpo.orders.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderDishService {

    private final OrderDishRepository orderDishRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;

    public OrderDishService(final OrderDishRepository orderDishRepository,
                            final DishRepository dishRepository, final OrderRepository orderRepository) {
        this.orderDishRepository = orderDishRepository;
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderDishDTO> findAll() {
        final List<OrderDish> orderDishs = orderDishRepository.findAll(Sort.by("id"));
        return orderDishs.stream()
                .map(orderDish -> mapToDTO(orderDish, new OrderDishDTO()))
                .toList();
    }

    public OrderDishDTO get(final Integer id) {
        return orderDishRepository.findById(id)
                .map(orderDish -> mapToDTO(orderDish, new OrderDishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CreateOrderDishForm createOrderDishForm, Integer orderId) {
        final OrderDish orderDish = new OrderDish();
        mapToEntity(createOrderDishForm, orderDish, orderId);
        return orderDishRepository.save(orderDish).getId();
    }

    private OrderDishDTO mapToDTO(final OrderDish orderDish, final OrderDishDTO orderDishDTO) {
        orderDishDTO.setId(orderDish.getId());
        orderDishDTO.setQuantity(orderDish.getQuantity());
        orderDishDTO.setPrice(orderDish.getPrice());
        orderDishDTO.setDish(orderDish.getDish() == null ? null : orderDish.getDish().getId());
        orderDishDTO.setOrder(orderDish.getOrder() == null ? null : orderDish.getOrder().getId());
        return orderDishDTO;
    }

    private OrderDish mapToEntity(final CreateOrderDishForm createOrderDishForm, final OrderDish orderDish, Integer orderId) {
        orderDish.setQuantity(createOrderDishForm.getQuantity());
        final Dish dish = dishRepository.findById(createOrderDishForm.getDish())
                .orElseThrow(() -> new NotFoundException("dish not found"));
        orderDish.setPrice(dish.getPrice());
        orderDish.setDish(dish);
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderDish.setOrder(order);
        return orderDish;
    }

    public List<OrderDishDTO> findByOrder(Order orderEntity) {
        final List<OrderDish> orderDishs = orderDishRepository.findAllByOrder(orderEntity);
        return orderDishs.stream()
                .map(orderDish -> mapToDTO(orderDish, new OrderDishDTO()))
                .toList();
    }
}
