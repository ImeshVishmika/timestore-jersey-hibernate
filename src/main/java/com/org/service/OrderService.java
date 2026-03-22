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
import java.util.Locale;

public class OrderService {

    private final Gson gson = new Gson();
    private static final String ORDER_TOTAL_SUBQUERY =
            "(SELECT COALESCE(SUM(ohm.qty * ohm.model_price), 0.0) " +
            "FROM OrderHasModel ohm WHERE ohm.id.orderId = o.orderId)";

    public String loadAllOrders(FilterDTO filterDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryString = new StringBuilder("SELECT new com.org.dto.OrderDTO( " +
                    "o.id, " +
                    "o.email, "+
                    "o.user.firstName, " +
                    "o.user.lastName , " +
                    "coalesce(SUM(ol.qty*ol.modelPrice),0.0d) as total," +
                    "o.orderedDate," +
                    "o.deliveryMethod," +
                    "o.deliveryMethodRef.price," +
                    "o.orderStatus," +
                    "o.orderStatusRef.status) "+
                    "FROM Order o JOIN o.orderItems ol WHERE 1=1");
            
            // Filter by Order Status
            if (filterDTO != null && filterDTO.getOrderStausId() != null && filterDTO.getOrderStausId() > 0) {
                queryString.append(" AND o.orderStatus =:orderStatusId ");
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
            queryString.append(" GROUP BY o.id ");

            queryString.append(
                    " HAVING 1=1 "
            );

            // Filter by Minimum Price
            if (filterDTO != null && filterDTO.getMinPrice() != null && filterDTO.getMinPrice() >= 0) {
                queryString.append(
                        " AND coalesce(SUM(ol.qty * ol.modelPrice), 0.0d) >= :minPrice "
                );
            }

            // Filter by Maximum Price
            if (filterDTO != null && filterDTO.getMaxPrice() != null && filterDTO.getMaxPrice() >= 0) {
                queryString.append(
                        " AND coalesce(SUM(ol.qty * ol.modelPrice), 0.0d) <= :maxPrice "
                );
            }

            // Create query with final WHERE clause
            Query<OrderDTO> query = session.createQuery(queryString.toString(),OrderDTO.class);

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

            List<OrderDTO> orders = query.getResultList();

            data = gson.toJsonTree(orders);

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
                responseObj.add("order", gson.toJsonTree(convertToDTO(order, session)));
                
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

            data = gson.toJsonTree(convertToDTO(order, session));
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

            data = gson.toJsonTree(convertToDTO(order, session));
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

            data = gson.toJsonTree(convertToDTO(order, session));
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
                orderDTOs.add(convertToDTO(order, session));
            }
            data = gson.toJsonTree(orderDTOs);

        } catch (Exception e) {
            state = false;
            message = "user orders loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    private OrderDTO convertToDTO(Order order, Session session) {
        Double orderTotal = getOrderTotal(order.getOrder_id(), session);

        Double deliveryFee = null;
        if (order.getDeliveryMethodRef() != null) {
            deliveryFee = order.getDeliveryMethodRef().getPrice();
        }

        String firstName = null;
        String lastName = null;
        if (order.getUser() != null) {
            firstName = order.getUser().getFirstName();
            lastName = order.getUser().getLastName();
        }
        
        String orderStatusName = null;
        if (order.getOrderStatusRef() != null) {
            orderStatusName = order.getOrderStatusRef().getStatus();
        }
        
        return new OrderDTO(
                order.getOrder_id(),
                order.getEmail(),
                firstName,
                lastName,
                orderTotal,
                order.getOrdered_date(),
                order.getDelivery_method(),
                deliveryFee,
                order.getOrder_status(),
                orderStatusName
        );
    }

            private Double getOrderTotal(Integer orderId, Session session) {
            Query<Double> totalQuery = session.createQuery(
                "SELECT COALESCE(SUM(ohm.qty * COALESCE(ohm.modelPrice, ohm.model.price)), 0.0) " +
                    "FROM OrderHasModel ohm WHERE ohm.id.orderId = :orderId",
                Double.class
            );
            totalQuery.setParameter("orderId", orderId);
            Double total = totalQuery.uniqueResult();
            return total != null ? total : 0.0;
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

    public String loadOrderStatusCounts() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT os.status, COUNT(o.orderId) " +
                            "FROM Order o JOIN o.orderStatusRef os " +
                            "GROUP BY os.status",
                    Object[].class
            );

            long processing = 0;
            long inTransit = 0;
            long delivered = 0;

            List<Object[]> rows = query.getResultList();
            for (Object[] row : rows) {
                String status = row[0] != null
                        ? row[0].toString().toLowerCase(Locale.ROOT)
                        : "";
                long count = row[1] != null ? ((Number) row[1]).longValue() : 0;

                if (status.contains("processing") || status.contains("pending") || status.contains("order places")) {
                    processing += count;
                } else if (status.contains("in-transit") || status.contains("in transit") || status.contains("shipped")) {
                    inTransit += count;
                } else if (status.contains("delivered") || status.contains("completed")) {
                    delivered += count;
                }
            }

            JsonObject counts = new JsonObject();
            counts.addProperty("processing", processing);
            counts.addProperty("inTransit", inTransit);
            counts.addProperty("delivered", delivered);
            data = counts;

        } catch (Exception e) {
            state = false;
            message = "order status counts loading failed: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Search orders by Order ID, Email, First Name, or Last Name
     */
    public String searchOrders(String searchQuery) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                state = false;
                message = "search query is empty";
                return JsonResponse.response(state, message, data);
            }

            String searchPattern = "%" + searchQuery + "%";

            StringBuilder queryString = new StringBuilder("SELECT new com.org.dto.OrderDTO( " +
                    "o.id, " +
                    "o.email, " +
                    "o.user.firstName, " +
                    "o.user.lastName, " +
                    "coalesce(SUM(ol.qty*ol.modelPrice),0.0d) as total," +
                    "o.orderedDate," +
                    "o.deliveryMethod," +
                    "o.deliveryMethodRef.price," +
                    "o.orderStatus," +
                    "o.orderStatusRef.status) " +
                    "FROM Order o JOIN o.orderItems ol WHERE (LOWER(CAST(o.id AS string)) LIKE LOWER(:searchPattern) " +
                    "OR LOWER(o.email) LIKE LOWER(:searchPattern) " +
                    "OR LOWER(o.user.firstName) LIKE LOWER(:searchPattern) " +
                    "OR LOWER(o.user.lastName) LIKE LOWER(:searchPattern)) " +
                    "GROUP BY o.id");

            Query<OrderDTO> query = session.createQuery(queryString.toString(), OrderDTO.class);
            query.setParameter("searchPattern", searchPattern);

            List<OrderDTO> orders = query.getResultList();
            data = gson.toJsonTree(orders);

        } catch (Exception e) {
            state = false;
            message = "order search failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

}

