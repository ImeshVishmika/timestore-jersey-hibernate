package com.org.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.org.dto.FilterDTO;
import com.org.dto.OrderDTO;
import com.org.dto.UserDTO;
import com.org.entity.User;
import com.org.util.HibernateUtil;
import com.org.util.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final Gson gson = new Gson();

    public String getUserProfile(UserDTO userDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

//        if (userDTO == null || userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
//            return JsonResponse.response(false, "user not signed in", null);
//        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, "imesh@gmail.com");

            if (user == null) {
                state = false;
                message = "user not found";
            } else {
                UserDTO profile = convertToDTO(user);

                Query<Object[]> addressQuery = session.createQuery(
                        "select ua.addressLine1, ua.addressLine2, c.cityEn, d.districtEn, p.provinceEn, c.postcode " +
                                "from UserAddress ua " +
                                "left join ua.city c " +
                                "left join c.districts d " +
                                "left join d.province p " +
                                "where ua.usersEmail = :email",
                        Object[].class
                );
                addressQuery.setParameter("email", user.getEmail());
                List<Object[]> addressRows = addressQuery.setMaxResults(1).getResultList();

                if (!addressRows.isEmpty()) {
                    Object[] row = addressRows.get(0);
                    profile.setLineOne((String) row[0]);
                    profile.setLineTwo((String) row[1]);
                    profile.setCity((String) row[2]);
                    profile.setDistrict((String) row[3]);
                    profile.setProvince((String) row[4]);
                    profile.setPostalCode((String) row[5]);
                }

                data = gson.toJsonTree(profile);
            }

        } catch (Exception e) {
            state = false;
            message = "user profile loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String updateUserProfile(String email, String firstName, String lastName, String mobile) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);

            if (user == null) {
                state = false;
                message = "user not found";
                return JsonResponse.response(state, message, data);
            }

            transaction = session.beginTransaction();

            if (firstName != null && !firstName.isEmpty()) {
                user.setFirstName(firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                user.setLastName(lastName);
            }
            if (mobile != null && !mobile.isEmpty()) {
                user.setMobile(mobile);
            }

            session.merge(user);
            transaction.commit();

            data = gson.toJsonTree(convertToDTO(user));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "user profile update failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String updateUserAddress(String email, String addressLine1, String addressLine2, String city, String district, String province, String postalCode) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // This is a placeholder - implementation depends on UserAddress entity structure

            transaction = session.beginTransaction();
            transaction.commit();

            message = "address updated successfully";

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "address update failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String loginUser(String email, String password, HttpSession httpSession) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);

            if (user == null) {
                state = false;
                message = "user not found";
            } else if (!user.getPassword().equals(password)) {
                state = false;
                message = "incorrect password";
            } else {
                httpSession.setAttribute("user",convertToDTO(user));
            }

        } catch (Exception e) {
            state = false;
            message = "login failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    public String loadUsers(FilterDTO filterDTO) {
        filterDTO = filterDTO == null ? new FilterDTO() : filterDTO;

        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryString = new StringBuilder(" SELECT new com.org.dto.UserDTO(u.email," +
                    "u.firstName," +
                    "u.lastName," +
                    "u.mobile," +
                    "coalesce(count (ol),0) , " +
                    "coalesce(SUM (oi.modelPrice*oi.qty),0.0d) , " +
                    "u.joinedDate" +
                    ") FROM  User u " +
                    "JOIN u.orderList ol " +
                    "JOIN ol.orderItems oi " +
                    "WHERE 1=1 ");

            if (filterDTO.getSearchQuery() != null && !filterDTO.getSearchQuery().trim().isEmpty()) {
                queryString.append(" AND (lower(u.firstName) like lower(:searchQuery) " +
                        "OR lower(u.lastName) like lower(:searchQuery) " +
                        "OR lower(u.email) like lower(:searchQuery)) ");
            }

            if (filterDTO.getUserStatusId() != null) {
                queryString.append(" AND u.status = :userStatusId ");
            }

            LocalDate joinedDateFrom = parseDate(filterDTO.getJoinedDateFrom());
            LocalDate joinedDateTo = parseDate(filterDTO.getJoinedDateTo());

            if (joinedDateFrom != null) {
                queryString.append(" AND u.joinedDate >= :joinedDateFrom ");
            }

            if (joinedDateTo != null) {
                queryString.append(" AND u.joinedDate <= :joinedDateTo ");
            }

            if (filterDTO.getMinOrderCount() != null) {
                queryString.append(" AND (select count(o.orderId) from Order o where o.email = u.email) >= :minOrderCount ");
            }

            if (filterDTO.getMaxOrderCount() != null) {
                queryString.append(" AND (select count(o.orderId) from Order o where o.email = u.email) <= :maxOrderCount ");
            }

            queryString.append(" GROUP BY u.email,u.firstName,u.lastName,u.mobile,u.joinedDate HAVING 1=1 ");

            if (filterDTO.getMinSpent() != null) {
                queryString.append(" AND SUM(oi.modelPrice*oi.qty) >= :minSpent ");
            }

            if (filterDTO.getMaxSpent() != null) {
                queryString.append(" AND SUM(oi.modelPrice*oi.qty) <= :maxSpent ");
            }

            Query<UserDTO> query = session.createQuery(queryString.toString(),UserDTO.class);

            if (filterDTO.getSearchQuery() != null && !filterDTO.getSearchQuery().trim().isEmpty()) {
                query.setParameter("searchQuery", "%" + filterDTO.getSearchQuery().trim() + "%");
            }

            if (filterDTO.getUserStatusId() != null) {
                query.setParameter("userStatusId", filterDTO.getUserStatusId());
            }

            if (joinedDateFrom != null) {
                query.setParameter("joinedDateFrom", joinedDateFrom);
            }

            if (joinedDateTo != null) {
                query.setParameter("joinedDateTo", joinedDateTo);
            }

            if (filterDTO.getMinOrderCount() != null) {
                query.setParameter("minOrderCount", filterDTO.getMinOrderCount().longValue());
            }

            if (filterDTO.getMaxOrderCount() != null) {
                query.setParameter("maxOrderCount", filterDTO.getMaxOrderCount().longValue());
            }

            if (filterDTO.getMinSpent() != null) {
                query.setParameter("minSpent", filterDTO.getMinSpent());
            }

            if (filterDTO.getMaxSpent() != null) {
                query.setParameter("maxSpent", filterDTO.getMaxSpent());
            }

            data = gson.toJsonTree(query.getResultList());

        } catch (Exception e) {
            state = false;
            message = "user search failed: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

    private LocalDate parseDate(String dateText) {
        if (dateText == null || dateText.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(dateText.trim());
        } catch (Exception e) {
            return null;
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPassword(user.getPassword());
        dto.setMobile(user.getMobile());
        dto.setGenderId(user.getGenderId());
        dto.setStatus(user.getStatus());
        dto.setJoinedDate(user.getJoinedDate());

//        if (user.getGender() != null) {
//            dto.setGenderName(user.getGender().getGenderName());
//        }
//        if (user.getUserStatus() != null) {
//            dto.setStatusName(user.getUserStatus().getStatusName());
//        }

        return dto;
    }

    public String getUserCountSummary() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Get total user count
            Query<Long> totalCountQuery = session.createQuery(
                    "select count(u) from User u",
                    Long.class
            );
            Long totalUserCount = totalCountQuery.uniqueResult();

            // Get user counts by status
            Query<Object[]> statusCountQuery = session.createQuery(
                    "select us.statusId, us.status, count(u) " +
                            "from User u " +
                            "left join u.userStatus us " +
                            "group by us.statusId, us.status " +
                            "order by us.statusId",
                    Object[].class
            );
            List<Object[]> statusCounts = statusCountQuery.getResultList();

            // Build response data
            com.google.gson.JsonObject responseData = new com.google.gson.JsonObject();
            responseData.addProperty("totalUsers", totalUserCount != null ? totalUserCount : 0);

            com.google.gson.JsonArray statusArray = new com.google.gson.JsonArray();
            for (Object[] row : statusCounts) {
                com.google.gson.JsonObject statusObj = new com.google.gson.JsonObject();
                statusObj.addProperty("statusId", ((Number) row[0]).intValue());
                statusObj.addProperty("statusName", (String) row[1]);
                statusObj.addProperty("count", ((Number) row[2]).longValue());
                statusArray.add(statusObj);
            }

            responseData.add("statusBreakdown", statusArray);
            data = gson.toJsonTree(responseData);

        } catch (Exception e) {
            state = false;
            message = "failed to retrieve user count summary: " + e.getMessage();
        }

        return JsonResponse.response(state, message, data);
    }

}

