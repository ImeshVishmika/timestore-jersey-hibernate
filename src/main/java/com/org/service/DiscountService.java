package com.org.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.org.dto.DiscountDTO;
import com.org.entity.Discount;
import com.org.entity.Product;
import com.org.entity.ProductDiscount;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountService {

    private final Gson gson = new Gson();

    /**
     * Create a new discount
     */
    public String createDiscount(DiscountDTO discountDTO) {
        boolean state = true;
        String message = "Discount created successfully";
        JsonElement data = null;

        // Validation
        if (discountDTO.getDiscountCode() == null || discountDTO.getDiscountCode().trim().isEmpty()) {
            return JsonResponse.response(false, "Discount code is required", null);
        }

        if (discountDTO.getDiscountValue() == null || discountDTO.getDiscountValue() <= 0) {
            return JsonResponse.response(false, "Discount value must be greater than 0", null);
        }

        if (discountDTO.getUsageLimit() == null || discountDTO.getUsageLimit() <= 0) {
            return JsonResponse.response(false, "Usage limit must be greater than 0", null);
        }

        if (discountDTO.getExpiryDate() == null) {
            return JsonResponse.response(false, "Expiry date is required", null);
        }

        if (discountDTO.getExpiryDate().isBefore(LocalDateTime.now())) {
            return JsonResponse.response(false, "Expiry date must be in the future", null);
        }

        if (discountDTO.getValueType() == null || discountDTO.getValueType().trim().isEmpty()) {
            return JsonResponse.response(false, "Value type is required (PERCENTAGE or FIXED_AMOUNT)", null);
        }

        if (discountDTO.getDiscountType() == null || discountDTO.getDiscountType().trim().isEmpty()) {
            return JsonResponse.response(false, "Discount type is required (PRODUCT or ORDER)", null);
        }

        // For PERCENTAGE type, value should be 0-100
        if ("PERCENTAGE".equals(discountDTO.getValueType()) && discountDTO.getDiscountValue() > 100) {
            return JsonResponse.response(false, "Percentage discount cannot exceed 100%", null);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Check if discount code already exists
            Query<Discount> existingQuery = session.createQuery(
                    "FROM Discount WHERE LOWER(discountCode) = LOWER(:code)", Discount.class);
            existingQuery.setParameter("code", discountDTO.getDiscountCode());
            if (existingQuery.uniqueResult() != null) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount code already exists", null);
            }

            // Create new discount
            Discount discount = new Discount();
            discount.setDiscountCode(discountDTO.getDiscountCode().toUpperCase());
            discount.setDescription(discountDTO.getDescription());
            discount.setDiscountType(Discount.DiscountType.valueOf(discountDTO.getDiscountType()));
            discount.setValueType(Discount.ValueType.valueOf(discountDTO.getValueType()));
            discount.setDiscountValue(discountDTO.getDiscountValue());
            discount.setUsageLimit(discountDTO.getUsageLimit());
            discount.setExpiryDate(discountDTO.getExpiryDate());
            discount.setMinimumOrderValue(discountDTO.getMinimumOrderValue());
            discount.setMinimumProductQuantity(discountDTO.getMinimumProductQuantity());
            discount.setStatus(Discount.DiscountStatus.ACTIVE);

            session.persist(discount);
            transaction.commit();

            data = gson.toJsonTree(new DiscountDTO(discount));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Error creating discount: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Get discount by ID
     */
    public String getDiscountById(Integer discountId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        if (discountId == null || discountId <= 0) {
            return JsonResponse.response(false, "Invalid discount ID", null);
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Discount discount = session.get(Discount.class, discountId);

            if (discount == null) {
                return JsonResponse.response(false, "Discount not found", null);
            }

            data = gson.toJsonTree(new DiscountDTO(discount));

        } catch (Exception e) {
            state = false;
            message = "Error fetching discount: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Get active discounts for ORDER type
     */
    public String getActiveOrderDiscounts() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;
        List<DiscountDTO> discountList = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Discount> query = session.createQuery(
                    "FROM Discount WHERE discountType = :type AND status = :status " +
                    "AND expiryDate > :now AND timesUsed < usageLimit " +
                    "ORDER BY createdDate DESC", Discount.class);
            query.setParameter("type", Discount.DiscountType.ORDER);
            query.setParameter("status", Discount.DiscountStatus.ACTIVE);
            query.setParameter("now", LocalDateTime.now());

            List<Discount> discounts = query.list();
            for (Discount discount : discounts) {
                discountList.add(new DiscountDTO(discount));
            }

            data = gson.toJsonTree(discountList);

        } catch (Exception e) {
            state = false;
            message = "Error fetching discounts: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Get active discounts for a specific PRODUCT
     */
    public String getActiveProductDiscounts(Integer productId) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;
        List<DiscountDTO> discountList = new ArrayList<>();

        if (productId == null || productId <= 0) {
            return JsonResponse.response(false, "Invalid product ID", null);
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Discount> query = session.createQuery(
                    "SELECT d FROM Discount d " +
                    "JOIN ProductDiscount pd ON d.discountId = pd.discountId " +
                    "WHERE pd.productId = :productId AND d.status = :status " +
                    "AND d.expiryDate > :now AND d.timesUsed < d.usageLimit " +
                    "ORDER BY d.createdDate DESC", Discount.class);
            query.setParameter("productId", productId);
            query.setParameter("status", Discount.DiscountStatus.ACTIVE);
            query.setParameter("now", LocalDateTime.now());

            List<Discount> discounts = query.list();
            for (Discount discount : discounts) {
                discountList.add(new DiscountDTO(discount));
            }

            data = gson.toJsonTree(discountList);

        } catch (Exception e) {
            state = false;
            message = "Error fetching product discounts: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Apply discount to a product (create ProductDiscount mapping)
     */
    public String applyDiscountToProduct(Integer productId, Integer discountId) {
        boolean state = true;
        String message = "Discount applied to product successfully";
        JsonElement data = null;

        if (productId == null || productId <= 0) {
            return JsonResponse.response(false, "Invalid product ID", null);
        }

        if (discountId == null || discountId <= 0) {
            return JsonResponse.response(false, "Invalid discount ID", null);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Verify product exists
            Product product = session.get(Product.class, productId);
            if (product == null) {
                transaction.rollback();
                return JsonResponse.response(false, "Product not found", null);
            }

            // Verify discount exists
            Discount discount = session.get(Discount.class, discountId);
            if (discount == null) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount not found", null);
            }

            // Check if already applied
            Query<ProductDiscount> existingQuery = session.createQuery(
                    "FROM ProductDiscount WHERE productId = :productId AND discountId = :discountId", 
                    ProductDiscount.class);
            existingQuery.setParameter("productId", productId);
            existingQuery.setParameter("discountId", discountId);
            
            if (existingQuery.uniqueResult() != null) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount already applied to this product", null);
            }

            // Create mapping
            ProductDiscount productDiscount = new ProductDiscount(productId, discountId);
            session.persist(productDiscount);
            transaction.commit();

            data = gson.toJsonTree(productDiscount);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Error applying discount: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Remove discount from a product
     */
    public String removeDiscountFromProduct(Integer productId, Integer discountId) {
        boolean state = true;
        String message = "Discount removed from product successfully";
        JsonElement data = null;

        if (productId == null || productId <= 0) {
            return JsonResponse.response(false, "Invalid product ID", null);
        }

        if (discountId == null || discountId <= 0) {
            return JsonResponse.response(false, "Invalid discount ID", null);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            int deletedCount = session.createQuery(
                    "DELETE FROM ProductDiscount WHERE productId = :productId AND discountId = :discountId")
                    .setParameter("productId", productId)
                    .setParameter("discountId", discountId)
                    .executeUpdate();

            if (deletedCount == 0) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount mapping not found", null);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Error removing discount: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Use discount (increment usage count)
     */
    public String useDiscount(Integer discountId) {
        boolean state = true;
        String message = "Discount usage recorded";
        JsonElement data = null;

        if (discountId == null || discountId <= 0) {
            return JsonResponse.response(false, "Invalid discount ID", null);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Discount discount = session.get(Discount.class, discountId);
            if (discount == null) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount not found", null);
            }

            if (discount.isUsageLimitReached()) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount usage limit reached", null);
            }

            if (discount.isExpired()) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount has expired", null);
            }

            discount.incrementUsageCount();
            session.merge(discount);
            transaction.commit();

            data = gson.toJsonTree(new DiscountDTO(discount));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Error recording discount usage: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Update discount status
     */
    public String updateDiscountStatus(Integer discountId, String newStatus) {
        boolean state = true;
        String message = "Discount status updated successfully";
        JsonElement data = null;

        if (discountId == null || discountId <= 0) {
            return JsonResponse.response(false, "Invalid discount ID", null);
        }

        if (newStatus == null || newStatus.trim().isEmpty()) {
            return JsonResponse.response(false, "Status is required", null);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Discount discount = session.get(Discount.class, discountId);
            if (discount == null) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount not found", null);
            }

            discount.setStatus(Discount.DiscountStatus.valueOf(newStatus.toUpperCase()));
            discount.setUpdatedDate(LocalDateTime.now());
            session.merge(discount);
            transaction.commit();

            data = gson.toJsonTree(new DiscountDTO(discount));

        } catch (IllegalArgumentException e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Invalid status value: " + e.getMessage();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Error updating discount status: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Delete discount
     */
    public String deleteDiscount(Integer discountId) {
        boolean state = true;
        String message = "Discount deleted successfully";
        JsonElement data = null;

        if (discountId == null || discountId <= 0) {
            return JsonResponse.response(false, "Invalid discount ID", null);
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Delete from ProductDiscount first (FK constraint)
            int productDiscountDeleted = session.createQuery(
                    "DELETE FROM ProductDiscount WHERE discountId = :discountId")
                    .setParameter("discountId", discountId)
                    .executeUpdate();

            // Delete from Discount
            Discount discount = session.get(Discount.class, discountId);
            if (discount == null) {
                transaction.rollback();
                return JsonResponse.response(false, "Discount not found", null);
            }

            session.remove(discount);
            transaction.commit();

            data = gson.toJsonTree(new DiscountDTO(discount));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "Error deleting discount: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    /**
     * Calculate discount amount based on value and value type
     */
    public Double calculateDiscountAmount(Discount discount, Double baseAmount) {
        if (discount.getValueType() == Discount.ValueType.PERCENTAGE) {
            return baseAmount * (discount.getDiscountValue() / 100);
        } else {
            return discount.getDiscountValue();
        }
    }

    /**
     * Validate if discount can be applied to an order
     */
    public String validateOrderDiscount(Integer discountId, Double orderTotal) {
        if (discountId == null || discountId <= 0) {
            return "Invalid discount ID";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Discount discount = session.get(Discount.class, discountId);

            if (discount == null) {
                return "Discount not found";
            }

            if (!discount.isActive()) {
                return "Discount is not active";
            }

            if (discount.getDiscountType() != Discount.DiscountType.ORDER) {
                return "This discount can only be applied to products";
            }

            if (discount.getMinimumOrderValue() != null && orderTotal < discount.getMinimumOrderValue()) {
                return "Order total must be at least Rs. " + discount.getMinimumOrderValue();
            }

            return null; // null means valid
        } catch (Exception e) {
            return "Error validating discount: " + e.getMessage();
        }
    }
}
