package com.org.controller.admin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.org.dto.FilterDTO;
import com.org.service.AdminService;
import com.org.service.MessageService;
import com.org.service.ProductService;
import com.org.util.JsonRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {

    private final AdminService adminService = new AdminService();
    private final ProductService productService = new ProductService();
    private final MessageService messageService = new MessageService();
    private final Gson gson = new Gson();

    @POST
    @Path("/load")
    public Response loadProducts(String requestBody) {
        try {
            FilterDTO filterDTO =gson.fromJson(requestBody, FilterDTO.class);
            String result = productService.getProductStats(filterDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/model/")
    public Response loadModels(String requestBody){
        try {
            FilterDTO filterDTO = gson.fromJson(requestBody, FilterDTO.class);
            String result = productService.getModelStats(filterDTO);
            return Response.ok().entity(result).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/dashboardStats")
    public Response getDashboardStats() {
        try {
            String result = adminService.getDashboardStats();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/senders")
    public Response getMessageSenders() {
        try {
            String result = messageService.sendSenderData();
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
            String result = adminService.loginAdmin(email, password,request.getSession());
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("error").build();
        }
    }


}
