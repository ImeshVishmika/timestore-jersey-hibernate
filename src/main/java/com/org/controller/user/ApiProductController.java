package com.org.controller.user;

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

    @POST
    @Path("/load")
    public Response loadProducts(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String sort = JsonRequestUtil.getString(body, "sort");
            String brand = JsonRequestUtil.getString(body, "brand");
            String result = productService.loadProducts(sort, brand);
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
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String brandId = JsonRequestUtil.getString(body, "brand_id");
            String productName = JsonRequestUtil.getString(body, "product_name");
            String result = productService.addProduct(productName, brandId);
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
            JsonRequestUtil.parseBody(requestBody);
            String result = productService.getProductRevenue();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }


}
