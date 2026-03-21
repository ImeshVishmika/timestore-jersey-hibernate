package com.org.controller.admin;

import com.google.gson.Gson;
import com.org.dto.DiscountDTO;
import com.org.service.DiscountService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/discount")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscountController {

    private final Gson gson = new Gson();
    private final DiscountService discountService = new DiscountService();

    /**
     * Create a new discount
     */
    @POST
    @Path("/create")
    public Response createDiscount(String requestBody) {
        try {
            DiscountDTO discountDTO = gson.fromJson(requestBody, DiscountDTO.class);
            String result = discountService.createDiscount(discountDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Get discount by ID
     */
    @GET
    @Path("/{discountId}")
    public Response getDiscount(@PathParam("discountId") Integer discountId) {
        try {
            if (discountId == null || discountId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid discount ID\"}").build();
            }
            String result = discountService.getDiscountById(discountId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Get all active ORDER type discounts
     */
    @GET
    @Path("/order/active")
    public Response getActiveOrderDiscounts() {
        try {
            String result = discountService.getActiveOrderDiscounts();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Get all active PRODUCT type discounts for a product
     */
    @GET
    @Path("/product/{productId}/active")
    public Response getActiveProductDiscounts(@PathParam("productId") Integer productId) {
        try {
            if (productId == null || productId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid product ID\"}").build();
            }
            String result = discountService.getActiveProductDiscounts(productId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Apply discount to a product
     */
    @POST
    @Path("/apply/{productId}/{discountId}")
    public Response applyDiscountToProduct(
            @PathParam("productId") Integer productId,
            @PathParam("discountId") Integer discountId) {
        try {
            if (productId == null || productId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid product ID\"}").build();
            }
            if (discountId == null || discountId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid discount ID\"}").build();
            }
            String result = discountService.applyDiscountToProduct(productId, discountId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Remove discount from a product
     */
    @DELETE
    @Path("/remove/{productId}/{discountId}")
    public Response removeDiscountFromProduct(
            @PathParam("productId") Integer productId,
            @PathParam("discountId") Integer discountId) {
        try {
            if (productId == null || productId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid product ID\"}").build();
            }
            if (discountId == null || discountId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid discount ID\"}").build();
            }
            String result = discountService.removeDiscountFromProduct(productId, discountId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Record discount usage (increment usage count)
     */
    @POST
    @Path("/use/{discountId}")
    public Response useDiscount(@PathParam("discountId") Integer discountId) {
        try {
            if (discountId == null || discountId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid discount ID\"}").build();
            }
            String result = discountService.useDiscount(discountId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Update discount status
     */
    @PUT
    @Path("/{discountId}/status/{status}")
    public Response updateDiscountStatus(
            @PathParam("discountId") Integer discountId,
            @PathParam("status") String status) {
        try {
            if (discountId == null || discountId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid discount ID\"}").build();
            }
            String result = discountService.updateDiscountStatus(discountId, status);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Delete discount
     */
    @DELETE
    @Path("/{discountId}")
    public Response deleteDiscount(@PathParam("discountId") Integer discountId) {
        try {
            if (discountId == null || discountId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\":false,\"message\":\"Invalid discount ID\"}").build();
            }
            String result = discountService.deleteDiscount(discountId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Validate if discount can be applied to an order
     */
    @POST
    @Path("/validate/order")
    public Response validateOrderDiscount(String requestBody) {
        try {
            com.google.gson.JsonObject body = gson.fromJson(requestBody, com.google.gson.JsonObject.class);
            Integer discountId = body.get("discountId").getAsInt();
            Double orderTotal = body.get("orderTotal").getAsDouble();

            String validationError = discountService.validateOrderDiscount(discountId, orderTotal);
            
            if (validationError == null) {
                return Response.ok().entity("{\"state\":true,\"message\":\"Discount is valid\"}").build();
            } else {
                return Response.ok().entity("{\"state\":false,\"message\":\"" + validationError + "\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"message\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
