package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.org.dto.FilterDTO;
import com.org.dto.ModelDTO;
import com.org.dto.ProductDTO;
import com.org.entity.Brand;
import com.org.entity.Model;
import com.org.entity.Product;
import com.org.entity.ProductImage;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.time.LocalDate;

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
                                    "from ProductImage i where i.modelId = :mid", ProductImage.class)
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
        System.out.println("test");
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

        System.out.println(productId);
        if (productId == null || productId <= 0) {
            state = false;
            message = "invalid product id";
            return JsonResponse.response(state, message, data);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Product product = session.get(Product.class, productId);

            if (product == null) {
                state = false;
                message = "product not found";
                transaction.rollback();
                return JsonResponse.response(state, message, data);
            }

            session.createQuery("DELETE FROM Model m WHERE m.productId =:productId")
                    .setParameter("productId", productId)
                    .executeUpdate();

            // Delete Product
            session.remove(product);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "product deletion failed: " + e.getMessage();
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

    public String addProduct(ProductDTO productDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        if (productDTO == null) {
            state = false;
            message = "product data is required";
            return JsonResponse.response(state, message, data);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Validate and handle Brand
            Integer brandId = productDTO.getBrandId();
            if (brandId == null && productDTO.getBrandName() != null && !productDTO.getBrandName().isEmpty()) {
                Brand newBrand = new Brand();
                newBrand.setBrandName(productDTO.getBrandName());
                session.persist(newBrand);
                session.flush();
                brandId = newBrand.getBrandId();
            }

            if (brandId == null) {
                state = false;
                message = "brand id or brand name is required";
                transaction.rollback();
                return JsonResponse.response(state, message, data);
            }

            // Validate Product Name
            if (productDTO.getProductName() == null || productDTO.getProductName().isEmpty()) {
                state = false;
                message = "product name is required";
                transaction.rollback();
                return JsonResponse.response(state, message, data);
            }

            // Create new Product
            Product product = new Product();
            product.setProductName(productDTO.getProductName());
            product.setBrandId(brandId);
            session.persist(product);
            session.flush();

            // Validate and create Model
            if (productDTO.getModels() == null || productDTO.getModels().isEmpty()) {
                state = false;
                message = "at least one model is required";
                transaction.rollback();
                return JsonResponse.response(state, message, data);
            }

            for (ModelDTO modelDTO : productDTO.getModels()) {
                if (modelDTO.getModel() == null || modelDTO.getModel().isEmpty()) {
                    state = false;
                    message = "model name is required";
                    transaction.rollback();
                    return JsonResponse.response(state, message, data);
                }

                if (modelDTO.getPrice() == null || modelDTO.getPrice() <= 0) {
                    state = false;
                    message = "model price must be greater than 0";
                    transaction.rollback();
                    return JsonResponse.response(state, message, data);
                }

                if (modelDTO.getQty() == null || modelDTO.getQty() <= 0) {
                    state = false;
                    message = "model quantity must be greater than 0";
                    transaction.rollback();
                    return JsonResponse.response(state, message, data);
                }

                Model model = new Model();
                model.setProductId(product.getProductId());
                model.setModel(modelDTO.getModel());
                model.setPrice(modelDTO.getPrice());
                model.setQty(modelDTO.getQty());
                model.setAddedTime(LocalDateTime.now());
                session.persist(model);
            }

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

    public String getProductRevenue(FilterDTO filterDTO) {

        filterDTO = filterDTO == null ? new FilterDTO() : filterDTO;

        boolean state = true;
        String message = "success";
        JsonElement data = null;

        int periodDays = filterDTO.getPeriodDays();
        if (periodDays <= 0) {
            periodDays = 7;
        }
        if (periodDays > 365) {
            periodDays = 365;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(periodDays - 1L);

            String queryString = "SELECT new com.org.dto.ProductDTO(SUM(il.qty*il.modelPrice)," +
                    filterDTO.getViewBy() + "(il.dateTime)) " +
                    "FROM InvoiceItem il " +
                    "LEFT JOIN il.model m " +
                    "LEFT JOIN m.product p " +
                    "WHERE 1=1 ";

            if (filterDTO.getProductId() != null) {
                queryString += " AND p.productId IN (:productId) ";
            }

            if (filterDTO.getStartDate() != null) {
                queryString += " AND il.dateTime >=:startDate ";
            }

            if (filterDTO.getEndDate() != null) {
                queryString += " AND il.dateTime <=:endDate ";
            }

            queryString += " GROUP BY " + filterDTO.getViewBy() + "(il.dateTime) " +
                    " HAVING 1=1 ";

            if (filterDTO.getMinRevenue() > 0) {
                queryString += " AND SUM(il.qty*il.modelPrice) >=:minRevenue ";
            }

            if (filterDTO.getMaxRevenue() > 0 && filterDTO.getMaxRevenue() > filterDTO.getMinRevenue()) {
                queryString += " AND  SUM(il.qty*il.modelPrice) <=:maxRevenue ";
            }

            queryString += " ORDER BY " + filterDTO.getViewBy() + "(il.dateTime) ";

            Query<ProductDTO> query = session.createQuery(queryString, ProductDTO.class);

            if (filterDTO.getProductId() != null) {
                query.setParameter("productId", filterDTO.getProductId());
            }

            if (filterDTO.getMinRevenue() > 0) {
                query.setParameter("minRevenue", filterDTO.getMinRevenue());
            }

            if (filterDTO.getMaxRevenue() > 0 && filterDTO.getMaxRevenue() >= filterDTO.getMinRevenue()) {
                query.setParameter("maxRevenue", filterDTO.getMaxRevenue());
            }

            if (filterDTO.getEndDate() != null) {
                query.setParameter("endDate", LocalDate.parse(filterDTO.getEndDate()).plusDays(1).atStartOfDay());
            }

            if (filterDTO.getStartDate() != null) {
                query.setParameter("startDate", LocalDate.parse(filterDTO.getStartDate()).atStartOfDay());
            }

            List<ProductDTO> productList = query.getResultList();

            ProductDTO productDTO = new ProductDTO();

            for (ProductDTO p : productList) {
                productDTO.addToRevenueList(p.getRevenue());
                productDTO.addToDateList(p.getDate());
            }

            data = gson.toJsonTree(productDTO);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            state = false;
            message = "revenue data loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String getProductStats(FilterDTO filterDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        filterDTO = filterDTO == null ? new FilterDTO() : filterDTO;

        List<Integer> productIds = filterDTO.getProductId();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder(
                    "SELECT new com.org.dto.ProductDTO(" +
                            "p.productId, " +
                            "p.productName, " +
                            "p.brandId, " +
                            "b.brandName, " +
                            "COALESCE((SELECT MAX(m.price) FROM Model m WHERE m.product = p),0,0), " +
                            "COALESCE((SELECT SUM(m.qty) FROM Model m WHERE m.product = p), 0), " +
                            "COALESCE((SELECT SUM(il.qty) FROM InvoiceItem il WHERE il.model.product = p), 0), " +
                            "COALESCE((SELECT SUM(il.qty * il.modelPrice) FROM InvoiceItem il WHERE il.model.product = p), 0.0) " +
                            ") " +
                            "FROM Product p " +
                            "LEFT JOIN p.brand b " +
                            "WHERE 1=1 "
            );

            if (productIds != null && !productIds.isEmpty()) {
                hql.append("AND p.productId IN (:productIds) ");
            }

            if (filterDTO.getBrandId() != null && !filterDTO.getBrandId().isEmpty()) {
                hql.append(" AND p.brand.brandId IN (:brandIds) ");
            }

            Query<ProductDTO> query = session.createQuery(hql.toString(), ProductDTO.class);

            if (productIds != null && !productIds.isEmpty()) {
                query.setParameterList("productIds", productIds);
            }

            if (filterDTO.getBrandId() != null && !filterDTO.getBrandId().isEmpty()) {
                query.setParameter("brandIds", filterDTO.getBrandId());
            }

            List<ProductDTO> productStatsList = query.getResultList();

            data = gson.toJsonTree(productStatsList);

        } catch (Exception e) {
            state = false;
            message = "product stats loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String getModelStats(FilterDTO filterDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        filterDTO = filterDTO == null ? new FilterDTO() : filterDTO;

        List<Integer> modelIds = filterDTO.getModelId();
        List<Integer> productIds = filterDTO.getProductId();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("""
                    SELECT new com.org.dto.ModelDTO(
                        m.modelId,
                        m.model,
                        m.product.brand.brandId,
                        m.product.brand.brandName,
                        m.price,
                        m.qty,
                        coalesce((SELECT SUM(il.qty) FROM InvoiceItem il WHERE il.model = m), 0L),
                        coalesce((SELECT SUM(il.qty * il.modelPrice) FROM InvoiceItem il WHERE il.model = m), 0.0d)
                    )
                    FROM Model m WHERE 1=1
                    """);

            if (productIds != null && !productIds.isEmpty()) {
                hql.append(" AND m.product.productId IN (:productIds) ");
            }

            if (modelIds != null && !modelIds.isEmpty()) {
                hql.append(" AND m.modelId IN (:modelIds) ");
            }

            Query<ModelDTO> query = session.createQuery(hql.toString(), ModelDTO.class);

            if (productIds != null && !productIds.isEmpty()) {
                query.setParameter("productIds", productIds);
            }

            if (modelIds != null && !modelIds.isEmpty()) {
                query.setParameterList("modelIds", modelIds);
            }

            List<ModelDTO> productStatsList = query.getResultList();

            data = gson.toJsonTree(productStatsList);

        } catch (Exception e) {
            state = false;
            message = "product stats loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

}

