package com.org.controller.user;

import com.org.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
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
    public Response loadUsers(@FormParam("status") String status) {
        try {
            String result = userService.loadUsersByStatus(status != null ? status : "1");
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/userProfile")
    public Response getUserProfile(@FormParam("email") String email) {
        try {
            String result = userService.getUserProfile(email);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/updateProfile")
    public Response updateProfile(
            @FormParam("first_name") String firstName,
            @FormParam("last_name") String lastName,
            @FormParam("mobile") String mobile,
            @FormParam("email") String email) {
        try {
            String result = userService.updateUserProfile(email, firstName, lastName, mobile);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/updateAddress")
    public Response updateAddress(
            @FormParam("line_one") String lineOne,
            @FormParam("line_two") String lineTwo,
            @FormParam("city") String city,
            @FormParam("district") String district,
            @FormParam("province") String province,
            @FormParam("postal_code") String postalCode,
            @FormParam("email") String email) {
        try {
            String result = userService.updateUserAddress(email, lineOne, lineTwo, city, district, province, postalCode);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/logIn")
    public Response logIn(
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("rememberMe") Integer rememberMe) {
        try {
            String result = userService.loginUser(email, password);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("error").build();
        }
    }
}
