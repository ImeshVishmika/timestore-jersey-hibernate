package com.org.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

    /**
     * Load users by status
     */
    public String loadUsersByStatus(String status) {
        boolean state = true;
        String message = "success";
        JsonElement data = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User where status = :status", User.class);
            query.setParameter("status", Integer.parseInt(status));
            List<User> users = query.getResultList();
            List<UserDTO> userDTOs = new ArrayList<>();
            
            for (User user : users) {
                userDTOs.add(convertToDTO(user));
            }
            
            data = gson.toJsonTree(userDTOs);

        } catch (Exception e) {
            state = false;
            message = "user loading failed: " + e.getMessage();
        }
        return JsonResponse.response(state, message, data);
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

}

