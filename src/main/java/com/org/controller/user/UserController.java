package com.org.controller.user;

import com.google.gson.Gson;
import com.org.dto.FilterDTO;
import com.org.dto.UserDTO;
import com.org.service.UserService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    @POST
    public Response loadUsers(String filterData) {
        try {

            FilterDTO filterDTO = gson.fromJson(filterData, FilterDTO.class);
            System.out.println(filterData);
            String users = userService.loadUsers(filterDTO);
            return Response.ok().entity(users).build();

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false}").build();
        }
    }


    @POST
    @Path("/userProfile")
    public Response getUserProfile(@Context HttpServletRequest request) {
        try {
            UserDTO userDTO=(UserDTO) request.getSession().getAttribute("user");
            String result = userService.getUserProfile(userDTO);
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
    public Response logIn(String requestBody, @Context HttpServletRequest request) {
        try {

            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String email = JsonRequestUtil.getString(body, "email");
            String password = JsonRequestUtil.getString(body, "password");
            JsonRequestUtil.getInteger(body, "rememberMe", 0);
            String result = userService.loginUser(email, password,request.getSession());
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("error").build();
        }
    }

    @GET
    @Path("/signinstatus")
    public Response getSignInStatus(@Context HttpServletRequest request) {
        try {
            JsonObject response = new JsonObject();
            Object user = request.getSession().getAttribute("user");
            response.addProperty("status", user != null);
            return Response.ok().entity(response.toString()).build();
        } catch (Exception e) {
            JsonObject response = new JsonObject();
            response.addProperty("status", false);
            return Response.ok().entity(response.toString()).build();
        }
    }

    @GET
    @Path("/countSummary")
    public Response getUserCountSummary() {
        try {
            String result = userService.getUserCountSummary();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
