package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.DeliveryMethodDTO;
import com.org.entity.DeliveryMethod;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DeliveryService {

    private final Gson gson = new Gson();

    /**
     * Load delivery methods
     */
    public String loadDeliveryMethods() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DeliveryMethod> query = session.createQuery("from DeliveryMethod", DeliveryMethod.class);
            List<DeliveryMethod> methods = query.getResultList();
            List<DeliveryMethodDTO> methodDTOs = new ArrayList<>();
            for (DeliveryMethod method : methods) {
                methodDTOs.add(convertToDTO(method));
            }
            data = gson.toJsonTree(methodDTOs);

        } catch (Exception e) {
            state = false;
            message = "delivery method loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    /**
     * Update delivery method
     */
    public String updateDeliveryMethod(String deliveryMethodId, String price) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            DeliveryMethod method = session.get(DeliveryMethod.class, Integer.parseInt(deliveryMethodId));

            if (method == null) {
                state = false;
                message = "delivery method not found";
                return jsonResponse(state, message, data);
            }

            transaction = session.beginTransaction();

            if (price != null && !price.isEmpty()) {
                method.setPrice(Double.parseDouble(price));
            }

            session.merge(method);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(method));
            message = "delivery method updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "delivery method update failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    /**
     * Delete delivery method
     */
    public String deleteDeliveryMethod(String deliveryMethodId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            DeliveryMethod method = session.get(DeliveryMethod.class, Integer.parseInt(deliveryMethodId));

            if (method == null) {
                state = false;
                message = "delivery method not found";
                return jsonResponse(state, message, data);
            }

            transaction = session.beginTransaction();
            session.remove(method);
            transaction.commit();

            message = "delivery method deleted successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "delivery method deletion failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    private DeliveryMethodDTO convertToDTO(DeliveryMethod method) {
        return new DeliveryMethodDTO(
                method.getId(),
                method.getDelivery_method(),
                method.getPrice(),
                method.getDelivery_days()
        );
    }

    private String jsonResponse(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);
        return gson.toJson(responseJson);
    }
}
