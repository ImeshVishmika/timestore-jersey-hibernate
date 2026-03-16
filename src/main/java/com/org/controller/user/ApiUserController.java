package com.org.controller.user;

import com.org.service.UserService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiUserController {
    
    private final UserService userService = new UserService();

    @POST
    @Path("/details")
    public Response getUserDetails() {
        try {
            String result = userService.getAllUsers();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/load")
    public Response loadUsers(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String status = JsonRequestUtil.getString(body, "status", "1");
            String result = userService.loadUsersByStatus(status != null ? status : "1");
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/userProfile")
    public Response getUserProfile(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String email = JsonRequestUtil.getString(body, "email");
            String result = userService.getUserProfile(email);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/updateProfile")
    public Response updateProfile(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String firstName = JsonRequestUtil.getString(body, "first_name");
            String lastName = JsonRequestUtil.getString(body, "last_name");
            String mobile = JsonRequestUtil.getString(body, "mobile");
            String email = JsonRequestUtil.getString(body, "email");
            String result = userService.updateUserProfile(email, firstName, lastName, mobile);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/updateAddress")
    public Response updateAddress(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String lineOne = JsonRequestUtil.getString(body, "line_one");
            String lineTwo = JsonRequestUtil.getString(body, "line_two");
            String city = JsonRequestUtil.getString(body, "city");
            String district = JsonRequestUtil.getString(body, "district");
            String province = JsonRequestUtil.getString(body, "province");
            String postalCode = JsonRequestUtil.getString(body, "postal_code");
            String email = JsonRequestUtil.getString(body, "email");
            String result = userService.updateUserAddress(email, lineOne, lineTwo, city, district, province, postalCode);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/logIn")
    public Response logIn(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String email = JsonRequestUtil.getString(body, "email");
            String password = JsonRequestUtil.getString(body, "password");
            JsonRequestUtil.getInteger(body, "rememberMe", 0);
            String result = userService.loginUser(email, password);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("error").build();
        }
    }
}
