package com.org.controller.user;

import com.google.gson.Gson;
import com.org.dto.FilterDTO;
import com.org.dto.ProductDTO;
import com.org.service.ProductService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiProductController {
    
    private final ProductService productService = new ProductService();
    private final Gson gson = new Gson();

    @POST
    @Path("/load")
    public Response loadProducts(String requestBody) {
        try {
            FilterDTO filterDTO = gson.fromJson(requestBody, FilterDTO.class);
            String result = productService.loadProducts(filterDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/add")
    public Response addProduct(String requestBody) {
        try {
            ProductDTO productDTO = gson.fromJson(requestBody,ProductDTO.class);
            String result = productService.addProduct(productDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/revenue")
    public Response getProductRevenue(String requestBody) {
        try {
            FilterDTO filterDTO = gson.fromJson(requestBody, FilterDTO.class);
            String result = productService.getProductRevenue(filterDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{productId}")
    public Response deleteProduct(@PathParam("productId") Integer productId) {
        try {
            if (productId == null || productId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\": false, \"message\": \"Invalid product id\"}").build();
            }
            System.out.println(productId);
            String result = productService.deleteProduct(productId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
