package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.ModelDTO;
import com.org.entity.Model;
import com.org.entity.ProductImage;
import com.org.util.HibernateUtil;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModelService {

    private final Gson gson = new Gson();

    public String loadModels(String productId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Model> query = session.createQuery(
                    "from Model where product_id = :productId", Model.class);
            query.setParameter("productId", Integer.parseInt(productId));
            List<Model> models = query.getResultList();
            List<ModelDTO> modelDTOs = new ArrayList<>();
            for (Model model : models) {
                modelDTOs.add(convertToDTO(model, session));
            }
            data = gson.toJsonTree(modelDTOs);

        } catch (Exception e) {
            state = false;
            message = "model loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

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

            data = gson.toJsonTree(convertToDTO(modelEntity, session));
            message = "model updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "model update failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    public Response getImg(Integer mid) {
        if (mid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProductImage productImage = session.createQuery(
                            "FROM ProductImage p WHERE p.model_id = :mid OR p.model.product_id =:mid", ProductImage.class)
                    .setParameter("mid", mid)
                    .setMaxResults(1)
                    .uniqueResult();

            if (productImage == null || productImage.getImg_path() == null || productImage.getImg_path().isBlank()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }


            String dbPath = productImage.getImg_path();
            Path path = Paths.get("webapp/" + dbPath);

            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            String mimeType = Files.probeContentType(path);
            if (mimeType == null || mimeType.isBlank()) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM;
            }

            byte[] imageBytes = Files.readAllBytes(path);
            return Response.ok(imageBytes, mimeType)
                    .header("Content-Length", imageBytes.length)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ModelDTO convertToDTO(Model model, Session session) {
        ModelDTO dto = new ModelDTO(model);
        try {
            List<ProductImage> imgs = session.createQuery(
                    "from ProductImage where model_id = :mid", ProductImage.class)
                    .setParameter("mid", model.getModel_id())
                    .setMaxResults(1)
                    .getResultList();
            if (imgs != null && !imgs.isEmpty()) {
                dto.setImgPath(imgs.get(0).getImg_path());
            }
        } catch (Exception ignored) {
        }
        return dto;
    }

    private String jsonResponse(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);
        return gson.toJson(responseJson);
    }
}
