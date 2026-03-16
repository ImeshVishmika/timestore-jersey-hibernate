package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.WishlistDTO;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class WishlistService {

    private final Gson gson = new Gson();

    /**
     * Load user wishlist
     */
    public String loadUserWishlist(String email) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<WishlistDTO> query = session.createQuery(
                    "select new com.org.dto.WishlistDTO(w.watchlist_id, w.product_id, w.users_email) " +
                            "from Watchlist w where w.users_email = :email",
                    WishlistDTO.class);
            query.setParameter("email", email);
            List<WishlistDTO> wishlist = query.getResultList();
            data = gson.toJsonTree(wishlist);

        } catch (Exception e) {
            state = false;
            message = "wishlist loading failed: " + e.getMessage();
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
