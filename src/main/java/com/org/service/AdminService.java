package com.org.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.entity.Admin;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;

public class AdminService {

    private static final int CANCELLED_ORDER_STATUS = 6;
    private static final int GROWTH_PERIOD_DAYS = 30;
    
    public String getDashboardStats() {
        boolean state = true;
        String message = "success";
        JsonObject statsObject = new JsonObject();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> userCountQuery = session.createQuery(
                    "SELECT COUNT(*) FROM User", Long.class);
            Long totalUsers = userCountQuery.getSingleResult();
            statsObject.addProperty("totalUsers", totalUsers);
            
            Query<Long> orderCountQuery = session.createQuery(
                    "SELECT COUNT(*) FROM Order", Long.class);
            Long totalOrders = orderCountQuery.getSingleResult();
            statsObject.addProperty("totalOrders", totalOrders);
            
            Query<Long> pendingOrdersQuery = session.createQuery(
                    "SELECT COUNT(*) FROM Order WHERE order_status = 1", Long.class);
            Long pendingOrders = pendingOrdersQuery.getSingleResult();
            statsObject.addProperty("pendingOrders", pendingOrders);
            
            Double totalRevenue = ((Number) session.createNativeQuery(
                "SELECT COALESCE(SUM(ohm.qty * m.price), 0) " +
                    "FROM order_has_model ohm " +
                    "JOIN `order` o ON o.order_id = ohm.order_id " +
                    "JOIN model m ON m.model_id = ohm.model_id " +
                    "WHERE o.order_status <> :cancelledStatus")
                .setParameter("cancelledStatus", CANCELLED_ORDER_STATUS)
                .getSingleResult()).doubleValue();

            LocalDateTime now = LocalDateTime.now();
            double currentPeriodRevenue = getRevenueBetween(session, now.minusDays(GROWTH_PERIOD_DAYS), now);
            double previousPeriodRevenue = getRevenueBetween(session, now.minusDays(GROWTH_PERIOD_DAYS * 2L), now.minusDays(GROWTH_PERIOD_DAYS));

            double revenueGrowth;
            if (previousPeriodRevenue <= 0) {
            revenueGrowth = currentPeriodRevenue > 0 ? 100.0 : 0.0;
            } else {
            revenueGrowth = ((currentPeriodRevenue - previousPeriodRevenue) / previousPeriodRevenue) * 100.0;
            }

            statsObject.addProperty("totalRevenue", totalRevenue);
            statsObject.addProperty("total_revenue", totalRevenue);
            statsObject.addProperty("revenueGrowth", revenueGrowth);
            statsObject.addProperty("revenue_growth", revenueGrowth);
            statsObject.addProperty("total_orders", totalOrders);
            statsObject.addProperty("total_users", totalUsers);
            statsObject.addProperty("pending_orders", pendingOrders);
            
            JsonElement data = statsObject;
            return JsonResponse.response(state, message, data);

        } catch (Exception e) {
            state = false;
            message = "dashboard stats loading failed: " + e.getMessage();
            return JsonResponse.response(state, message, null);
        }
    }

    private double getRevenueBetween(Session session, LocalDateTime startInclusive, LocalDateTime endExclusive) {
        Number result = (Number) session.createNativeQuery(
                        "SELECT COALESCE(SUM(ohm.qty * m.price), 0) " +
                                "FROM order_has_model ohm " +
                                "JOIN `order` o ON o.order_id = ohm.order_id " +
                                "JOIN model m ON m.model_id = ohm.model_id " +
                                "WHERE o.order_status <> :cancelledStatus " +
                                "AND o.ordered_date >= :startDate " +
                                "AND o.ordered_date < :endDate")
                .setParameter("cancelledStatus", CANCELLED_ORDER_STATUS)
                .setParameter("startDate", startInclusive)
                .setParameter("endDate", endExclusive)
                .getSingleResult();

        return result != null ? result.doubleValue() : 0.0;
    }

    public String loginAdmin(String email, String password, HttpSession httpSession) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Admin admin = session.get(Admin.class, email);
            
            if (admin == null) {
                state = false;
                message = "admin not found";
            } else if (!admin.getPassword().equals(password)) {
                state = false;
                message = "incorrect password";
            } else {
                httpSession.setAttribute("admin", admin);
            }

        } catch (Exception e) {
            state = false;
            message = "login failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }
}

