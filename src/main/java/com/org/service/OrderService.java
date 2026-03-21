package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.FilterDTO;
import com.org.dto.OrderDTO;
import com.org.dto.OrderItemsDTO;
import com.org.entity.Order;
import com.org.entity.OrderHasModel;
import com.org.entity.OrderStatus;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final Gson gson = new Gson();

    /**
     * Load all orders with advanced filtering (admin)
     */
    public String loadAllOrders(FilterDTO filterDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryString = new StringBuilder("FROM Order o WHERE 1=1");

            // Build WHERE clause based on valid parameters
            
            // Filter by Order Status
            if (filterDTO != null && filterDTO.getOrderStausId() != null && filterDTO.getOrderStausId() > 0) {
                queryString.append(" AND o.orderStatus = :orderStatusId");
            }

            // Filter by Minimum Price
            if (filterDTO != null && filterDTO.getMinPrice() != null && filterDTO.getMinPrice() >= 0) {
                queryString.append(" AND o.total >= :minPrice");
            }

            // Filter by Maximum Price
            if (filterDTO != null && filterDTO.getMaxPrice() != null && filterDTO.getMaxPrice() >= 0) {
                queryString.append(" AND o.total <= :maxPrice");
            }

            // Filter by Date From
            if (filterDTO != null && filterDTO.getDateFrom() != null && !filterDTO.getDateFrom().trim().isEmpty()) {
                try {
                    LocalDateTime.parse(filterDTO.getDateFrom() + "T00:00:00");
                    queryString.append(" AND CAST(o.orderedDate AS DATE) >= CAST(:dateFrom AS DATE)");
                } catch (Exception e) {
                    // Invalid date format, skip this filter
                }
            }

            // Filter by Date To
            if (filterDTO != null && filterDTO.getDateTo() != null && !filterDTO.getDateTo().trim().isEmpty()) {
                try {
                    LocalDateTime.parse(filterDTO.getDateTo() + "T00:00:00");
                    queryString.append(" AND CAST(o.orderedDate AS DATE) <= CAST(:dateTo AS DATE)");
                } catch (Exception e) {
                    // Invalid date format, skip this filter
                }
            }

            // Create query with final WHERE clause
            Query<Order> query = session.createQuery(queryString.toString(), Order.class);

            // Bind parameters
            if (filterDTO != null) {
                if (filterDTO.getOrderStausId() != null && filterDTO.getOrderStausId() > 0) {
                    query.setParameter("orderStatusId", filterDTO.getOrderStausId());
                }

                if (filterDTO.getMinPrice() != null && filterDTO.getMinPrice() >= 0) {
                    query.setParameter("minPrice", filterDTO.getMinPrice());
                }

                if (filterDTO.getMaxPrice() != null && filterDTO.getMaxPrice() >= 0) {
                    query.setParameter("maxPrice", filterDTO.getMaxPrice());
                }

                if (filterDTO.getDateFrom() != null && !filterDTO.getDateFrom().trim().isEmpty()) {
                    try {
                        LocalDateTime.parse(filterDTO.getDateFrom() + "T00:00:00");
                        query.setParameter("dateFrom", filterDTO.getDateFrom());
                    } catch (Exception e) {
                        // Skip invalid date
                    }
                }

                if (filterDTO.getDateTo() != null && !filterDTO.getDateTo().trim().isEmpty()) {
                    try {
                        LocalDateTime.parse(filterDTO.getDateTo() + "T00:00:00");
                        query.setParameter("dateTo", filterDTO.getDateTo());
                    } catch (Exception e) {
                        // Skip invalid date
                    }
                }
            }

            List<Order> orders = query.getResultList();
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for (Order order : orders) {
                orderDTOs.add(convertToDTO(order));
            }
            data = gson.toJsonTree(orderDTOs);

        } catch (Exception e) {
            state = false;
            message = "order loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    /**
     * Get order details with order items
     */
    public String getOrderDetails(OrderDTO orderDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, Integer.parseInt(orderDTO.getOrderId().toString()));

            if (order == null) {
                state = false;
                message = "order not found";
            } else {
                // Create response object with order and items
                JsonObject responseObj = new JsonObject();
                responseObj.add("order", gson.toJsonTree(convertToDTO(order)));
                
                // Get order items
                Query<OrderHasModel> itemsQuery = session.createQuery(
                        "from OrderHasModel where id.orderId = :orderId", OrderHasModel.class);
                itemsQuery.setParameter("orderId", order.getOrder_id());
                List<OrderHasModel> orderItems = itemsQuery.getResultList();
                
                List<OrderItemsDTO> orderItemsDTOs = new ArrayList<>();
                for (OrderHasModel item : orderItems) {
                    orderItemsDTOs.add(new OrderItemsDTO(item));
                }
                responseObj.add("orderItemsDetails", gson.toJsonTree(orderItemsDTOs));
                
                data = responseObj;
            }

        } catch (Exception e) {
            state = false;
            message = "order details loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    /**
     * Create new order
     */
    public String createNewOrder(String modelId, String quantity, String deliveryMethodId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Order order = new Order();
            if (deliveryMethodId != null && !deliveryMethodId.isEmpty()) {
                order.setDelivery_method(Integer.parseInt(deliveryMethodId));
            }
            order.setOrdered_date(LocalDateTime.now());
            order.setOrder_status(1);

            session.persist(order);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(order));
            message = "order created successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "order creation failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    /**
     * Update order status after payment
     */
    public String updateOrderStatusAfterPayment(String orderId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, Integer.parseInt(orderId));

            if (order == null) {
                state = false;
                message = "order not found";
                return JsonResponse.response(state, message, data);
            }

            transaction = session.beginTransaction();
            order.setOrder_status(2);
            session.merge(order);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(order));
            message = "order status updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "order status update failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    /**
     * Cancel order
     */
    public String cancelOrder(String orderId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, Integer.parseInt(orderId));

            if (order == null) {
                state = false;
                message = "order not found";
                return JsonResponse.response(state, message, data);
            }

            transaction = session.beginTransaction();
            order.setOrder_status(3);
            session.merge(order);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(order));
            message = "order cancelled successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "order cancellation failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    /**
     * Get user orders
     */
    public String getUserOrders(String email) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery(
                    "from Order where email = :email", Order.class);
            query.setParameter("email", email);
            List<Order> orders = query.getResultList();
            List<OrderDTO> orderDTOs = new ArrayList<>();
            for (Order order : orders) {
                orderDTOs.add(convertToDTO(order));
            }
            data = gson.toJsonTree(orderDTOs);

        } catch (Exception e) {
            state = false;
            message = "user orders loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    private OrderDTO convertToDTO(Order order) {
        Double deliveryFee = null;
        if (order.getDeliveryMethodRef() != null) {
            deliveryFee = order.getDeliveryMethodRef().getPrice();
        }
        
        String orderStatusName = null;
        if (order.getOrderStatusRef() != null) {
            orderStatusName = order.getOrderStatusRef().getStatus();
        }
        
        return new OrderDTO(
                order.getOrder_id(),
                order.getEmail(),
                order.getUser().getFirstName(),
                order.getUser().getLastName(),
                1000.00,
                order.getOrdered_date().toString(),
                order.getDelivery_method(),
                deliveryFee,
                order.getOrder_status(),
                orderStatusName
        );
    }

    /**
     * Load all order statuses
     */
    public String loadAllOrderStatuses() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<OrderStatus> query = session.createQuery("from OrderStatus", OrderStatus.class);
            List<OrderStatus> statuses = query.getResultList();
            data = gson.toJsonTree(statuses);
        } catch (Exception e) {
            state = false;
            message = "order statuses loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

}

