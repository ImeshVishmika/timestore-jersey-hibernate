package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.org.dto.FilterDTO;
import com.org.dto.ModelDTO;
import com.org.entity.Model;
import com.org.entity.Product;
import com.org.entity.ProductImage;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelService {

    private final Gson gson = new Gson();

    public String loadModels(FilterDTO filterDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            FilterDTO safeFilter = filterDTO != null ? filterDTO : new FilterDTO();
            List<Integer> productId = safeFilter.getProductId();
            List<Integer> modelId = safeFilter.getModelId();
            List<Integer> brandId = safeFilter.getBrandId();
            List<String> brandName = safeFilter.getBrandName();
            String productName = safeFilter.getProductName();
            String modelName = safeFilter.getModelName();

            StringBuilder queryText = new StringBuilder("from Model m where 1=1");

            if (productId != null && !productId.isEmpty()) {
                queryText.append(" and m.productId in (:productIds)");
            }
            if (modelId != null && !modelId.isEmpty()) {
                queryText.append(" and m.modelId in (:modelIds)");
            }

            if (productName != null && !productName.isBlank()) {
                queryText.append(" and lower(m.product.productName) like :productName");
            }
            if (modelName != null && !modelName.isBlank()) {
                queryText.append(" and lower(m.model) like :modelName");
            }

            List<Integer> brandIdsFromNames = new ArrayList<>();
            if (brandName != null && !brandName.isEmpty()) {
                brandIdsFromNames = brandName.stream()
                        .map(value -> value == null ? "" : value)
                        .flatMap(value -> Arrays.stream(value.split(",")))
                        .map(String::trim)
                        .filter(value -> !value.isEmpty())
                        .map(value -> {
                            try {
                                return Integer.parseInt(value);
                            } catch (NumberFormatException ex) {
                                return null;
                            }
                        })
                        .filter(value -> value != null)
                        .collect(Collectors.toList());
                if (!brandIdsFromNames.isEmpty()) {
                    queryText.append(" and m.product.brandId in (:brandIdsFromNames)");
                }
            }

            if (brandId != null && !brandId.isEmpty()) {
                queryText.append(" and m.product.brandId in (:brandIds)");
            }

            Query<Model> query = session.createQuery(queryText.toString(), Model.class);

            if (productId != null && !productId.isEmpty()) {
                query.setParameterList("productIds", productId);
            }
            if (modelId != null && !modelId.isEmpty()) {
                query.setParameterList("modelIds", modelId);
            }
            if (brandId != null && !brandId.isEmpty()) {
                query.setParameterList("brandIds", brandId);
            }
            if (!brandIdsFromNames.isEmpty()) {
                query.setParameterList("brandIdsFromNames", brandIdsFromNames);
            }
            if (productName != null && !productName.isBlank()) {
                query.setParameter("productName", "%" + productName.toLowerCase() + "%");
            }
            if (modelName != null && !modelName.isBlank()) {
                query.setParameter("modelName", "%" + modelName.toLowerCase() + "%");
            }

            List<Model> models = query.getResultList();
            List<ModelDTO> modelDTOs = new ArrayList<>();
            for (Model model : models) {
                modelDTOs.add(new ModelDTO(model));
            }
            data = gson.toJsonTree(modelDTOs);

        } catch (Exception e) {
            state = false;
            message = "model loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
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
                return JsonResponse.response(state, message, data);
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

            data = gson.toJsonTree(new ModelDTO(modelEntity));
            message = "model updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "model update failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String deleteModel(Integer modelId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        if (modelId == null || modelId <= 0) {
            state = false;
            message = "invalid model id";
            return JsonResponse.response(state, message, data);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Model model = session.get(Model.class, modelId);

            if (model == null) {
                state = false;
                message = "model not found";
                transaction.rollback();
                return JsonResponse.response(state, message, data);
            }

            // Delete InvoiceItems first (cascade dependency)
//            String deleteInvoiceItemsHql = "DELETE FROM InvoiceItem il WHERE il.model.modelId = :modelId";
//            Query<?> deleteInvoiceItemsQuery = session.createQuery(deleteInvoiceItemsHql);
//            deleteInvoiceItemsQuery.setParameter("modelId", modelId);
//            int deletedInvoiceItems = deleteInvoiceItemsQuery.executeUpdate();

            // Delete ProductImages (cascade dependency)
            String deleteProductImagesHql = "DELETE FROM ProductImage pi WHERE pi.modelId = :modelId";
            Query<?> deleteProductImagesQuery = session.createQuery(deleteProductImagesHql);
            deleteProductImagesQuery.setParameter("modelId", modelId);
            deleteProductImagesQuery.executeUpdate();

            // Delete Model
            session.remove(model);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            System.out.println( e.getMessage());
            message = "model deletion failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String addModel(ModelDTO modelDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Integer productId = modelDTO.getProductId();

        if (productId == null || productId <= 0) {
            state = false;
            message = "invalid product id";
            return JsonResponse.response(state, message, data);
        }

        // Validate model name
        if (modelDTO.getModel() == null || modelDTO.getModel().isEmpty()) {
            state = false;
            message = "model name is required";
            return JsonResponse.response(state, message, data);
        }

        // Validate price
        if (modelDTO.getPrice() == null || modelDTO.getPrice() <= 0) {
            state = false;
            message = "model price must be greater than 0";
            return JsonResponse.response(state, message, data);
        }

        // Validate quantity
        if (modelDTO.getQty() == null || modelDTO.getQty() <= 0) {
            state = false;
            message = "model quantity must be greater than 0";
            return JsonResponse.response(state, message, data);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Verify product exists
            Product product = session.get(Product.class, productId);
            if (product == null) {
                state = false;
                message = "product not found";
                transaction.rollback();
                return JsonResponse.response(state, message, data);
            }

            // Create new Model
            Model newModel = new Model();
            newModel.setProductId(productId);
            newModel.setModel(modelDTO.getModel());
            newModel.setPrice(modelDTO.getPrice());
            newModel.setQty(modelDTO.getQty());
            newModel.setAddedTime(LocalDateTime.now());

            session.persist(newModel);
            transaction.commit();

            message = "model added successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "model addition failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public Response getImg(Integer mid) {
        if (mid == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ProductImage productImage = session.createQuery(
                            "FROM ProductImage p WHERE p.modelId = :mid OR p.model.productId =:mid", ProductImage.class)
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
}

