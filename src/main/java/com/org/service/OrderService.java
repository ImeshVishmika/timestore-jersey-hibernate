package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.entity.Order;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {
    
    private final Gson gson = new Gson();

    /**
     * Load all orders (admin)
     */
    public String loadAllOrders() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery("from Order", Order.class);
            List<Order> orders = query.getResultList();
            data = gson.toJsonTree(orders);

        } catch (Exception e) {
            state = false;
            message = "order loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    /**
     * Get order details
     */
    public String getOrderDetails(String orderId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Order order = session.get(Order.class, Integer.parseInt(orderId));
            
            if (order == null) {
                state = false;
                message = "order not found";
            } else {
                data = gson.toJsonTree(order);
            }

        } catch (Exception e) {
            state = false;
            message = "order details loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
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
            // Set order properties based on model and delivery method
            if (deliveryMethodId != null && !deliveryMethodId.isEmpty()) {
                order.setDelivery_method(Integer.parseInt(deliveryMethodId));
            }
            order.setOrdered_date(LocalDateTime.now());
            order.setOrder_status(1); // Default to pending status
            
            session.persist(order);
            transaction.commit();
            
            data = gson.toJsonTree(order);
            message = "order created successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "order creation failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
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
                return jsonResponse(state, message, data);
            }
            
            transaction = session.beginTransaction();
            order.setOrder_status(2); // Update to processing status
            session.merge(order);
            transaction.commit();
            
            data = gson.toJsonTree(order);
            message = "order status updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "order status update failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
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
                return jsonResponse(state, message, data);
            }
            
            transaction = session.beginTransaction();
            order.setOrder_status(3); // Set to cancelled status
            session.merge(order);
            transaction.commit();
            
            data = gson.toJsonTree(order);
            message = "order cancelled successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "order cancellation failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
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
            data = gson.toJsonTree(orders);

        } catch (Exception e) {
            state = false;
            message = "user orders loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    private String jsonResponse(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);
        return gson.toJson(responseJson);
    }
}
