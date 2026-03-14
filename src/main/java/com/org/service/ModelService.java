package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.entity.Model;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ModelService {
    
    private final Gson gson = new Gson();

    /**
     * Load product models
     */
    public String loadModels(String productId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Model> query = session.createQuery(
                    "from Model where product_id = :productId", Model.class);
            query.setParameter("productId", Integer.parseInt(productId));
            List<Model> models = query.getResultList();
            data = gson.toJsonTree(models);

        } catch (Exception e) {
            state = false;
            message = "model loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    /**
     * Update model
     */
    public String updateModel(String modelId, String model, String price, String quantity) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Model modelEntity = session.get(Model.class, Integer.parseInt(modelId));
            
            if (modelEntity == null) {
                state = false;
                message = "model not found";
                return jsonResponse(state, message, data);
            }
            
            transaction = session.beginTransaction();
            
            if (model != null && !model.isEmpty()) {
                modelEntity.setModel(model);
            }
            if (price != null && !price.isEmpty()) {
                modelEntity.setPrice(Double.parseDouble(price));
            }
            if (quantity != null && !quantity.isEmpty()) {
                modelEntity.setQty(Integer.parseInt(quantity));
            }
            
            session.merge(modelEntity);
            transaction.commit();
            
            data = gson.toJsonTree(modelEntity);
            message = "model updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "model update failed: " + e.getMessage();
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
