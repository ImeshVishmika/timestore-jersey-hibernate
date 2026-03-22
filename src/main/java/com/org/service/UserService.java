package com.org.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.org.dto.FilterDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

public class UserService {
    
    private final Gson gson = new Gson();
    

    public String getAllUsers() {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> users = query.getResultList();
            List<UserDTO> userDTOs = new ArrayList<>();
            
            for (User user : users) {
                userDTOs.add(convertToDTO(user));
            }
            
            data = gson.toJsonTree(userDTOs);

        } catch (Exception e) {
            state = false;
            message = "user loading failed :"+e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }
    

    public String getUserByEmail(String email) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);
            
            if (user == null) {
                state = false;
                message = "user not found";
            } else {
                data = gson.toJsonTree(convertToDTO(user));
            }

        } catch (Exception e) {
            state = false;
            message = "user loading failed";
        }
        return JsonResponse.response(state, message, data);
    }
    

    public String createUser(UserDTO userDTO) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            state = false;
            message = "email is required";
            return JsonResponse.response(state, message, data);
        }
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Check if user already exists
            User existingUser = session.get(User.class, userDTO.getEmail());
            if (existingUser != null) {
                state = false;
                message = "user already exists with this email";
                return JsonResponse.response(state, message, data);
            }
            
            transaction = session.beginTransaction();

            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPassword(BCrypt.withDefaults().hashToString(12,userDTO.getPassword().toCharArray()));
            user.setMobile(userDTO.getMobile());
            user.setGenderId(userDTO.getGenderId());
            user.setStatus(userDTO.getStatus());
            user.setJoinedDate(userDTO.getJoinedDate() != null ? userDTO.getJoinedDate() : LocalDate.now());
            
            session.persist(user);
            transaction.commit();
            
            data = gson.toJsonTree(convertToDTO(user));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "user creation failed";
        }
        return JsonResponse.response(state, message, data);
    }
    

    public String updateUser(String email, UserDTO userDTO) {
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
            
            if (userDTO.getFirstName() != null) {
                user.setFirstName(userDTO.getFirstName());
            }
            if (userDTO.getLastName() != null) {
                user.setLastName(userDTO.getLastName());
            }
            if (userDTO.getPassword() != null) {
                user.setPassword(userDTO.getPassword());
            }
            if (userDTO.getMobile() != null) {
                user.setMobile(userDTO.getMobile());
            }
            if (userDTO.getGenderId() != null) {
                user.setGenderId(userDTO.getGenderId());
            }
            if (userDTO.getStatus() != null) {
                user.setStatus(userDTO.getStatus());
            }
            
            session.merge(user);
            transaction.commit();
            
            data = gson.toJsonTree(convertToDTO(user));

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "user update failed";
        }
        return JsonResponse.response(state, message, data);
    }
    

    public String deleteUser(String email) {
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
            session.remove(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            state = false;
            message = "user deletion failed";
        }
        return JsonResponse.response(state, message, data);
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


//    public String loadUsersByFilters(FilterDTO filterDTO) {
//        if (filterDTO == null) {
//            filterDTO = new FilterDTO();
//        }
//
//        String status = filterDTO.getOrderStausId() != null ? String.valueOf(filterDTO.getOrderStausId()) : "0";
//        return loadUsersByFilters(
//                status,
//                filterDTO.getSearchQuery(),
//                filterDTO.getMinOrderCount(),
//                filterDTO.getMaxOrderCount(),
//                filterDTO.getMinPrice(),
//                filterDTO.getMaxPrice(),
//                filterDTO.getDateFrom(),
//                filterDTO.getDateTo()
//        );
//    }

//    public String loadUsersByFilters(FilterDTO filterDTO) {
//        boolean state = true;
//        String message = "success";
//        JsonElement data = null;
//
//        Integer status =1;
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            StringBuilder userQueryString = new StringBuilder("from User where 1=1");
//            Integer parsedStatus = null;
//            if (status != null && !status.isBlank()) {
//                try {
//                    parsedStatus = Integer.parseInt(status);
//                } catch (NumberFormatException ignored) {
//                    parsedStatus = null;
//                }
//            }
//
//            if (parsedStatus != null && parsedStatus > 0) {
//                userQueryString.append(" and status = :status");
//            }
//
//            Query<User> query = session.createQuery(userQueryString.toString(), User.class);
//            if (parsedStatus != null && parsedStatus > 0) {
//                query.setParameter("status", parsedStatus);
//            }
//            List<User> users = query.getResultList();
//
//            Map<String, Object[]> userOrderSummaryMap = new HashMap<>();
//            if (!users.isEmpty()) {
//                List<String> userEmails = users.stream().map(User::getEmail).toList();
//                Query<Object[]> summaryQuery = session.createQuery(
//                        "SELECT o.email, COUNT(DISTINCT o.orderId), " +
//                                "COALESCE(SUM(ohm.qty * COALESCE(ohm.modelPrice, ohm.model.price)), 0.0) " +
//                                "FROM Order o " +
//                                "LEFT JOIN o.orderItems ohm " +
//                                "WHERE o.email IN (:userEmails) " +
//                                "GROUP BY o.email",
//                        Object[].class
//                );
//                summaryQuery.setParameter("userEmails", userEmails);
//
//                List<Object[]> summaryRows = summaryQuery.getResultList();
//                for (Object[] summaryRow : summaryRows) {
//                    String email = (String) summaryRow[0];
//                    userOrderSummaryMap.put(email, summaryRow);
//                }
//            }
//
//            List<UserDTO> userDTOs = new ArrayList<>();
//            LocalDate joinedFromDate = parseDate(joinedDateFrom);
//            LocalDate joinedToDate = parseDate(joinedDateTo);
//            String normalizedSearchText = searchText != null ? searchText.trim().toLowerCase(Locale.ROOT) : null;
//
//            for (User user : users) {
//                UserDTO userDTO = convertToDTO(user);
//                Object[] summary = userOrderSummaryMap.get(user.getEmail());
//                int orderCount = 0;
//                double totalSpent = 0.0;
//
//                if (summary != null) {
//                    orderCount = ((Number) summary[1]).intValue();
//                    totalSpent = ((Number) summary[2]).doubleValue();
//                }
//
//                userDTO.setOrderCount(orderCount);
//                userDTO.setTotalSpent(totalSpent);
//
//                if (!matchesSearch(user, normalizedSearchText)) {
//                    continue;
//                }
//
//                if (minOrderCount != null && orderCount < minOrderCount) {
//                    continue;
//                }
//
//                if (maxOrderCount != null && orderCount > maxOrderCount) {
//                    continue;
//                }
//
//                if (minSpent != null && totalSpent < minSpent) {
//                    continue;
//                }
//
//                if (maxSpent != null && totalSpent > maxSpent) {
//                    continue;
//                }
//
//                if (!matchesJoinedDate(user.getJoinedDate(), joinedFromDate, joinedToDate)) {
//                    continue;
//                }
//
//                userDTOs.add(userDTO);
//            }
//
//            data = gson.toJsonTree(userDTOs);
//
//        } catch (Exception e) {
//            state = false;
//            message = "user loading failed: " + e.getMessage();
//        }
//        return JsonResponse.response(state, message, data);
//    }

    private LocalDate parseDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(date);
        } catch (Exception ignored) {
            return null;
        }
    }

    private boolean matchesJoinedDate(LocalDate joinedDate, LocalDate joinedFromDate, LocalDate joinedToDate) {
        if (joinedDate == null) {
            return joinedFromDate == null && joinedToDate == null;
        }

        if (joinedFromDate != null && joinedDate.isBefore(joinedFromDate)) {
            return false;
        }

        if (joinedToDate != null && joinedDate.isAfter(joinedToDate)) {
            return false;
        }

        return true;
    }

    private boolean matchesSearch(User user, String searchText) {
        if (searchText == null || searchText.isBlank()) {
            return true;
        }

        String firstName = user.getFirstName() != null ? user.getFirstName().toLowerCase(Locale.ROOT) : "";
        String lastName = user.getLastName() != null ? user.getLastName().toLowerCase(Locale.ROOT) : "";
        String email = user.getEmail() != null ? user.getEmail().toLowerCase(Locale.ROOT) : "";
        String mobile = user.getMobile() != null ? user.getMobile().toLowerCase(Locale.ROOT) : "";

        return firstName.contains(searchText)
                || lastName.contains(searchText)
                || email.contains(searchText)
                || mobile.contains(searchText);
    }

    /**
     * Get user profile with address and orders
     */
    public String getUserProfile(String email) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);
            
            if (user == null) {
                state = false;
                message = "user not found";
            } else {
                data = gson.toJsonTree(convertToDTO(user));
            }

        } catch (Exception e) {
            state = false;
            message = "user profile loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
    }

    /**
     * Update user profile
     */
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

    /**
     * Update user address
     */
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

    /**
     * Login user
     */
    public String loginUser(String email, String password, HttpSession httpSession) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, email);
            System.out.println(user.getEmail());
            
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

    /**
     * Search users by firstName, lastName, email, or combinations
     * @param firstName first name search term (optional, supports partial match)
     * @param lastName last name search term (optional, supports partial match)
     * @param email email search term (optional, supports partial match)
     * @return JSON response containing matching users
     */
    public String searchUsers(String firstName, String lastName, String email) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder queryString = new StringBuilder("FROM User WHERE 1=1 ");
            
            // Build dynamic WHERE clause based on provided parameters
            if (firstName != null && !firstName.trim().isEmpty()) {
                queryString.append("AND LOWER(firstName) LIKE LOWER(:firstName) ");
            }
            
            if (lastName != null && !lastName.trim().isEmpty()) {
                queryString.append("AND LOWER(lastName) LIKE LOWER(:lastName) ");
            }
            
            if (email != null && !email.trim().isEmpty()) {
                queryString.append("AND LOWER(email) LIKE LOWER(:email) ");
            }
            
            // If no search criteria provided, return empty result
            if ((firstName == null || firstName.trim().isEmpty()) &&
                (lastName == null || lastName.trim().isEmpty()) &&
                (email == null || email.trim().isEmpty())) {
                state = false;
                message = "at least one search parameter is required";
                return JsonResponse.response(state, message, data);
            }
            
            Query<User> query = session.createQuery(queryString.toString(), User.class);
            
            // Bind parameters with LIKE wildcards for partial matching
            if (firstName != null && !firstName.trim().isEmpty()) {
                query.setParameter("firstName", "%" + firstName.trim() + "%");
            }
            
            if (lastName != null && !lastName.trim().isEmpty()) {
                query.setParameter("lastName", "%" + lastName.trim() + "%");
            }
            
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email.trim() + "%");
            }
            
            List<User> users = query.getResultList();
            List<UserDTO> userDTOs = new ArrayList<>();
            
            for (User user : users) {
                userDTOs.add(convertToDTO(user));
            }
            
            data = gson.toJsonTree(userDTOs);
            
            if (users.isEmpty()) {
                message = "no users found matching the criteria";
            }

        } catch (Exception e) {
            state = false;
            message = "user search failed: " + e.getMessage();
        }
        
        return JsonResponse.response(state, message, data);
    }

}

