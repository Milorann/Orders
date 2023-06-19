package kpo.orders.service;

import java.time.OffsetDateTime;
import java.util.List;

import kpo.orders.domain.Dish;
import kpo.orders.domain.Order;
import kpo.orders.model.CreateOrderForm;
import kpo.orders.model.OrderDTO;
import kpo.orders.model.OrderDishDTO;
import kpo.orders.repos.DishRepository;
import kpo.orders.repos.OrderDishRepository;
import kpo.orders.repos.OrderRepository;
import kpo.orders.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final OrderDishRepository orderDishRepository;
    private final OrderDishService orderDishService;
    private final DishService dishService;

    public OrderService(final OrderRepository orderRepository, final DishRepository dishRepository,
                        final OrderDishRepository orderDishRepository, final OrderDishService orderDishService,
                        final DishService dishService) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.orderDishRepository = orderDishRepository;
        this.orderDishService = orderDishService;
        this.dishService = dishService;
    }


    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Integer id) {
        var orderEntity = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        var orderDTO = new OrderDTO();
        mapToDTO(orderEntity, orderDTO);
        orderDTO.setDishes(orderDishService.findByOrder(orderEntity));
        return orderDTO;
    }

    public Integer create(final CreateOrderForm createOrderForm) {
        final Order order = new Order();
        mapToEntity(createOrderForm, order);
        order.setStatus("processing");
        order.setCreatedAt(OffsetDateTime.now());
        order.setUpdatedAt(OffsetDateTime.now());
        var id = orderRepository.save(order).getId();
        creteOrderDishes(createOrderForm, id);
        return id;
    }

    private void creteOrderDishes(CreateOrderForm createOrderForm, Integer id) {
        for (var orderDish : createOrderForm.getDishes()) {
            final Dish dish = dishRepository.findById(orderDish.getDish())
                    .orElseThrow(() -> new NotFoundException("dish not found"));
            if (orderDish.getQuantity() <= dish.getQuantity()) {
                orderDishService.create(orderDish, id);
                dish.setQuantity(dish.getQuantity() - orderDish.getQuantity());
                dishService.updateQuantity(dish);
            } else {
                throw new NotFoundException("Не хватает ингредиентов");
            }
        }
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setSpecialRequests(order.getSpecialRequests());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        return orderDTO;
    }

    private Order mapToEntity(final CreateOrderForm createOrderForm, final Order order) {
        order.setSpecialRequests(createOrderForm.getSpecialRequests());
        return order;
    }

}
