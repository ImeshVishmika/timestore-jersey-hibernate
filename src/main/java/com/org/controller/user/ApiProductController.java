package com.org.controller.user;

import com.org.service.ProductService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ApiProductController {
    
    private final ProductService productService = new ProductService();

    @POST
    @Path("/load")
    public Response loadProducts(
            @FormParam("sort") String sort,
            @FormParam("brand") String brand,
            @FormParam("page") String page,
            @FormParam("page_size") String pageSize) {
        try {
            String result = productService.loadProducts(sort, brand);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/add")
    public Response addProduct(
            @FormParam("brand_id") String brandId,
            @FormParam("brand_name") String brandName,
            @FormParam("product_id") String productId,
            @FormParam("product_name") String productName,
            @FormParam("model_name") String modelName,
            @FormParam("price") String price,
            @FormParam("qty") String qty) {
        try {
            String result = productService.addProduct(productName, brandId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/revenue")
    public Response getProductRevenue(
            @FormParam("product_id") String productId,
            @FormParam("revenuePeriod") String revenuePeriod) {
        try {
            String result = productService.getProductRevenue();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/img/{id}")
    @Consumes(MediaType.WILDCARD)
    @Produces({"image/jpeg", "image/png", "image/webp", "image/gif", MediaType.APPLICATION_OCTET_STREAM})
    public Response getImage(@PathParam("id") Integer id) {
        return productService.getImg(id);
    }
}
