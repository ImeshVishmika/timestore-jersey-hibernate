package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.entity.Order;
import com.org.entity.User;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class AdminService {
    
    private final Gson gson = new Gson();


    public String getDashboardStats() {
        boolean state = true;
        String message = "success";
        JsonObject statsObject = new JsonObject();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Get total users count
            Query<Long> userCountQuery = session.createQuery(
                    "SELECT COUNT(*) FROM User", Long.class);
            Long totalUsers = userCountQuery.getSingleResult();
            statsObject.addProperty("totalUsers", totalUsers);
            
            // Get total orders count
            Query<Long> orderCountQuery = session.createQuery(
                    "SELECT COUNT(*) FROM Order", Long.class);
            Long totalOrders = orderCountQuery.getSingleResult();
            statsObject.addProperty("totalOrders", totalOrders);
            
            // Get pending orders count
            Query<Long> pendingOrdersQuery = session.createQuery(
                    "SELECT COUNT(*) FROM Order WHERE order_status = 1", Long.class);
            Long pendingOrders = pendingOrdersQuery.getSingleResult();
            statsObject.addProperty("pendingOrders", pendingOrders);
            
            // Get total revenue (placeholder - adjust based on your requirements)
            statsObject.addProperty("totalRevenue", 0.0);
            
            JsonElement data = statsObject;
            return jsonResponse(state, message, data);

        } catch (Exception e) {
            state = false;
            message = "dashboard stats loading failed: " + e.getMessage();
            return jsonResponse(state, message, null);
        }
    }

    private String jsonResponse(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);
        return gson.toJson(responseJson);
    }
}
