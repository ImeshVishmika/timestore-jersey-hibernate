package com.org.controller.user;

import com.org.dto.ProductDTO;
import com.org.service.ProductService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
    
    private final ProductService productService = new ProductService();

    @GET
    public Response getAllProducts() {
        try {
            String products = productService.getAllProducts();
            return Response.ok().entity(products).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching products: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{productId}")
    public Response getProductById(@PathParam("productId") Integer productId) {
        try {
            String product = productService.getProductById(productId);
            return Response.ok().entity(product).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching product: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/img/{id}")
    @Consumes(MediaType.WILDCARD)
    @Produces({"image/jpeg", "image/png", "image/webp", "image/gif", MediaType.APPLICATION_OCTET_STREAM})
    public Response getImage(@PathParam("id") Integer id) {
        return productService.getImg(id);
    }

    @POST
    public Response createProduct(ProductDTO productDTO) {
        try {
            String createdProduct = productService.createProduct(productDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(createdProduct).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating product: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{productId}")
    public Response updateProduct(@PathParam("productId") Integer productId, ProductDTO productDTO) {
        return Response.ok().build();
    }
}
