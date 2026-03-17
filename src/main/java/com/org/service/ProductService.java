package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.org.dto.FilterDTO;
import com.org.dto.ModelDTO;
import com.org.dto.ProductDTO;
import com.org.entity.Model;
import com.org.entity.Product;
import com.org.entity.ProductImage;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductService {

    private final Gson gson = new Gson();
    private static final int CANCELLED_ORDER_STATUS = 6;

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
        return JsonResponse.response(state, message, data);
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
                try {
                    ProductImage productImage = session.createQuery(
                                    "from ProductImage where model_id = :mid", ProductImage.class)
                            .setParameter("mid", m.getModel_id())
                            .setMaxResults(1)
                            .uniqueResult();
                    if (productImage != null && productImage.getImg_path() != null && !productImage.getImg_path().isBlank()) {
                        modelDTO.setImgPath(productImage.getImg_path());
                    }
                } catch (Exception ignored) {
                }
                modelDTOList.add(modelDTO);
            }

            productDTO.setModels(modelDTOList);

            data = gson.toJsonTree(productDTO);

        } catch (Exception e) {
            state = false;
            message = "product loading failed";
        }
        return JsonResponse.response(state, message, data);
    }

    public String createProduct(ProductDTO productDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        if (productDTO.getProductName() == null || productDTO.getProductName().isEmpty()) {
            state = false;
            message = "product name is required";
            return JsonResponse.response(state, message, data);
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
        return JsonResponse.response(state, message, data);
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
                return JsonResponse.response(state, message, data);
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
        return JsonResponse.response(state, message, data);
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
                return JsonResponse.response(state, message, data);
            }

            transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "product deletion failed";
        }
        return JsonResponse.response(state, message, data);
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
        return JsonResponse.response(state, message, data);
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


    public String loadProducts(FilterDTO filterDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            FilterDTO safeFilter = filterDTO != null ? filterDTO : new FilterDTO();
            List<Integer> brandIds = safeFilter.getBrandId();
            String sort = safeFilter.getSort();

            StringBuilder hql = new StringBuilder("from Product p where 1=1");
            if (brandIds != null && !brandIds.isEmpty()) {
                hql.append(" and p.brandId in (:brandIds)");
            }

            Query<Product> query = session.createQuery(hql.toString(), Product.class);
            if (brandIds != null && !brandIds.isEmpty()) {
                query.setParameterList("brandIds", brandIds);
            }

            List<Product> products = query.getResultList();
            List<ProductDTO> productDTOs = new ArrayList<>();

            for (Product product : products) {
                productDTOs.add(convertToDTO(product));
            }

            if (sort != null) {
                if ("asc".equalsIgnoreCase(sort) || "low_to_high".equalsIgnoreCase(sort)) {
                    productDTOs.sort(Comparator.comparing(ProductDTO::getProductName, Comparator.nullsLast(String::compareToIgnoreCase)));
                } else if ("desc".equalsIgnoreCase(sort) || "high_to_low".equalsIgnoreCase(sort)) {
                    productDTOs.sort(Comparator.comparing(ProductDTO::getProductName, Comparator.nullsLast(String::compareToIgnoreCase)).reversed());
                }
            }

            data = gson.toJsonTree(productDTOs);

        } catch (Exception e) {
            state = false;
            message = "product loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
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
        return JsonResponse.response(state, message, data);
    }

    public String getProductRevenue() {
        return getProductRevenue(7);
    }

    public String getProductRevenue(Integer requestedDays) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        int periodDays = requestedDays == null ? 7 : requestedDays;
        if (periodDays <= 0) {
            periodDays = 7;
        }
        if (periodDays > 365) {
            periodDays = 365;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(periodDays - 1L);

            List<Object[]> rows = session.createNamedQuery(
                            "SELECT DATE(o.ordered_date) AS revenue_date, COALESCE(SUM(ohm.qty * m.price), 0) AS revenue " +
                                    "FROM order_has_model ohm " +
                                    "JOIN `order` o ON o.order_id = ohm.order_id " +
                                    "JOIN model m ON m.model_id = ohm.model_id " +
                                    "WHERE o.order_status <> :cancelledStatus " +
                                    "AND o.ordered_date >= :startDate " +
                                    "GROUP BY DATE(o.ordered_date) " +
                                    "ORDER BY DATE(o.ordered_date)")
                    .setParameter("cancelledStatus", CANCELLED_ORDER_STATUS)
                    .setParameter("startDate", startDate.atStartOfDay())
                    .getResultList();

            Map<String, Double> dailyRevenueMap = new HashMap<>();
            for (Object[] row : rows) {
                if (row == null || row.length < 2 || row[0] == null) {
                    continue;
                }

                String dateKey = row[0].toString();
                double revenueValue = row[1] == null ? 0.0 : ((Number) row[1]).doubleValue();
                dailyRevenueMap.put(dateKey, revenueValue);
            }

            JsonArray dates = new JsonArray();
            JsonArray revenues = new JsonArray();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

            double total = 0.0;
            LocalDate cursor = startDate;
            while (!cursor.isAfter(endDate)) {
                String dateKey = cursor.format(dateFormatter);
                double revenue = dailyRevenueMap.getOrDefault(dateKey, 0.0);

                dates.add(dateKey);
                revenues.add(revenue);
                total += revenue;

                cursor = cursor.plusDays(1);
            }

            JsonObject revenuePayload = new JsonObject();
            revenuePayload.add("dates", dates);
            revenuePayload.add("revenues", revenues);
            revenuePayload.addProperty("total", total);
            revenuePayload.addProperty("periodDays", periodDays);

            data = revenuePayload;

        } catch (Exception e) {
            state = false;
            message = "revenue data loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

}

