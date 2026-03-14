package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.entity.Message;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MessageService {
    
    private final Gson gson = new Gson();

    /**
     * Get message senders
     */
    public String getMessageSenders() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                    "SELECT DISTINCT sender FROM Message", String.class);
            List<String> senders = query.getResultList();
            data = gson.toJsonTree(senders);

        } catch (Exception e) {
            state = false;
            message = "message senders loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    /**
     * Get user messages
     */
    public String getUserMessages(String senderEmail) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Message> query = session.createQuery(
                    "from Message where sender = :sender", Message.class);
            query.setParameter("sender", senderEmail);
            List<Message> messages = query.getResultList();
            data = gson.toJsonTree(messages);

        } catch (Exception e) {
            state = false;
            message = "user messages loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    /**
     * Change message state (mark as read)
     */
    public String changeMessageState(String messageId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Message messageEntity = session.get(Message.class, Integer.parseInt(messageId));
            
            if (messageEntity == null) {
                state = false;
                message = "message not found";
                return jsonResponse(state, message, data);
            }
            
            transaction = session.beginTransaction();
            messageEntity.setStatus(1); // Mark as read
            session.merge(messageEntity);
            transaction.commit();
            
            data = gson.toJsonTree(messageEntity);
            message = "message state updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "message state update failed: " + e.getMessage();
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
