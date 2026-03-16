package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.BrandDTO;
import com.org.entity.Brand;
import com.org.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BrandService {

    private final Gson gson = new Gson();

    public String loadBrands() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Brand> query = session.createQuery("from Brand", Brand.class);
            List<Brand> brands = query.getResultList();
            List<BrandDTO> brandDTOs = new ArrayList<>();
            for (Brand brand : brands) {
                brandDTOs.add(convertToDTO(brand));
            }
            data = gson.toJsonTree(brandDTOs);

        } catch (Exception e) {
            state = false;
            message = "brand loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    private BrandDTO convertToDTO(Brand brand) {
        return new BrandDTO(brand.getBrandId(), brand.getBrandName());
    }

    private String jsonResponse(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);
        return gson.toJson(responseJson);
    }
}
