package cqrs_architecture.query.service;

import cqrs_architecture.query.dto.OrderDTO;
import cqrs_architecture.query.dto.OrderStatisticsDTO;
import cqrs_architecture.query.model.OrderItemView;
import cqrs_architecture.query.model.OrderView;
import cqrs_architecture.query.repository.OrderViewRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderQueryService {
    private final OrderViewRepository orderViewRepository;
    public OrderQueryService(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }
    public OrderDTO getOrderById(String orderId) {
        OrderView orderView = orderViewRepository.findById(orderId);
        List<String> itemDescriptions = orderView.getItems().stream()
                .map(OrderItemView::toString)
                .collect(Collectors.toList());
        return new OrderDTO(
                orderView.getId(),
                orderView.getCustomerName(),
                orderView.getStatus(),
                orderView.getCreatedAt(),
                itemDescriptions
        );
    }
    public List<OrderDTO> getAllOrders() {
        return orderViewRepository.findAll().stream()
                .map(orderView -> getOrderById(orderView.getId()))
                .collect(Collectors.toList());
    }

    public OrderStatisticsDTO getOrderStatistics(String orderId) {
        OrderView orderView = orderViewRepository.findById(orderId);
        int totalDishes = orderView.getItems().stream()
                .mapToInt(OrderItemView::getQuantity)
                .sum();
        double totalPrice = orderView.getItems().stream()
                .mapToDouble(OrderItemView::getTotalPrice)
                .sum();
        return new OrderStatisticsDTO(orderView.getId(), totalDishes, totalPrice);
    }
}
