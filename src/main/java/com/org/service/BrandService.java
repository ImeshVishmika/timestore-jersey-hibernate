package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.org.cache.cacheBrand;
import com.org.dto.BrandDTO;
import com.org.entity.Brand;
import com.org.util.GsonUtil;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BrandService {

    private final Gson gson = GsonUtil.getGson();

    public String loadBrands() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSQLiteSessionFactory().openSession()) {
            session.beginTransaction();

            Query<cacheBrand> query = session.createQuery("from cacheBrand ", cacheBrand.class);
            List<cacheBrand> brands = query.getResultList();
            List<BrandDTO> brandDTOs = new ArrayList<>();
            for (cacheBrand cacheBrand : brands) {
                brandDTOs.add(new BrandDTO(cacheBrand));
            }
            data = gson.toJsonTree(brandDTOs);

        } catch (Exception e) {
            state = false;
            message = "brand loading failed";
        }
        return JsonResponse.response(state, message, data);
    }

}

