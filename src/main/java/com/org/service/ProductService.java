package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.ModelDTO;
import com.org.dto.ProductDTO;
import com.org.entity.Model;
import com.org.entity.Product;
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

public class ProductService {

    private final Gson gson = new Gson();

    public String getAllProducts() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Product> query = session.createQuery("from Product", Product.class);
            List<Product> products = query.getResultList();
            List<ProductDTO> productDTOs = new ArrayList<>();

            for (Product product : products) {
                productDTOs.add(convertToDTO(product));
            }

            data = gson.toJsonTree(productDTOs);

        } catch (Exception e) {
            state = false;
            message = "product loading failed";

        }
        return jsonResponse(state, message, data);
    }

    public String getProductById(Integer productId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, productId);
            ProductDTO productDTO = convertToDTO(product);

            List<ModelDTO> modelDTOList = new ArrayList<>();

            for (Model m : product.getModel()) {
                ModelDTO modelDTO = new ModelDTO(m);
                modelDTOList.add(modelDTO);
            }

            productDTO.setModels(modelDTOList);

            data = gson.toJsonTree(productDTO);

        } catch (Exception e) {
            state = false;
            message = "product loading failed";
        }
        return jsonResponse(state, message, data);
    }

    public String createProduct(ProductDTO productDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        if (productDTO.getProductName() == null || productDTO.getProductName().isEmpty()) {
            state = false;
            message = "product name is required";
            return jsonResponse(state, message, data);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product product = new Product();
            product.setProductName(productDTO.getProductName());
            product.setBrandId(productDTO.getBrandId());

            session.persist(product);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(product));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "product creation failed";
        }
        return jsonResponse(state, message, data);
    }

    public String updateProduct(Integer productId, ProductDTO productDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, productId);

            if (product == null) {
                state = false;
                message = "product not found";
                return jsonResponse(state, message, data);
            }

            transaction = session.beginTransaction();

            if (productDTO.getProductName() != null) {
                product.setProductName(productDTO.getProductName());
            }
            if (productDTO.getBrandId() != null) {
                product.setBrandId(productDTO.getBrandId());
            }

            session.merge(product);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(product));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "product update failed";
        }
        return jsonResponse(state, message, data);
    }

    public String deleteProduct(Integer productId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Product product = session.get(Product.class, productId);

            if (product == null) {
                state = false;
                message = "product not found";
                return jsonResponse(state, message, data);
            }

            transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "product deletion failed";
        }
        return jsonResponse(state, message, data);
    }

    public String getProductsByBrand(Integer brandId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Product> query = session.createQuery(
                    "from Product where brandId = :brandId", Product.class);
            query.setParameter("brandId", brandId);
            List<Product> products = query.getResultList();
            List<ProductDTO> productDTOs = new ArrayList<>();

            for (Product product : products) {
                productDTOs.add(convertToDTO(product));
            }

            data = gson.toJsonTree(productDTOs);

        } catch (Exception e) {
            state = false;
            message = "product loading failed";
        }
        return jsonResponse(state, message, data);
    }


    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setBrandId(product.getBrandId());

        if (product.getBrand() != null) {
            dto.setBrandName(product.getBrand().getBrandName());
        }

        return dto;
    }


    public String loadProducts(String filter, String sort) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Product> query = session.createQuery("from Product", Product.class);
            List<Product> products = query.getResultList();
            List<ProductDTO> productDTOs = new ArrayList<>();

            for (Product product : products) {
                productDTOs.add(convertToDTO(product));
            }

            data = gson.toJsonTree(productDTOs);

        } catch (Exception e) {
            state = false;
            message = "product loading failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    public String addProduct(String productName, String brandId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product product = new Product();
            product.setProductName(productName);
            if (brandId != null && !brandId.isEmpty()) {
                product.setBrandId(Integer.parseInt(brandId));
            }

            session.persist(product);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(product));
            message = "product added successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "product addition failed: " + e.getMessage();
        }
        return jsonResponse(state, message, data);
    }

    public String getProductRevenue() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // This is a placeholder - adjust based on your revenue calculation logic
            Query<Product> query = session.createQuery("from Product", Product.class);
            List<Product> products = query.getResultList();
            List<ProductDTO> productDTOs = new ArrayList<>();

            for (Product product : products) {
                productDTOs.add(convertToDTO(product));
            }

            data = gson.toJsonTree(productDTOs);

        } catch (Exception e) {
            state = false;
            message = "revenue data loading failed: " + e.getMessage();
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

            // Map database path to actual file location
            // DB stores: "Image/product/filename.ext"
            // Actual path: "src/main/webapp/assets/Image/product/filename.ext"
            String dbPath = productImage.getImg_path();
            Path path = Paths.get("src/main/webapp/assets/" + dbPath);

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


    private String jsonResponse(boolean state, String message, JsonElement jsonElement) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("state", state);
        responseJson.addProperty("message", message);
        responseJson.add("data", jsonElement);

        return gson.toJson(responseJson);
    }
}
