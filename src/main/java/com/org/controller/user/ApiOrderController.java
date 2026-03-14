package com.org.controller.user;

import com.org.service.OrderService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ApiOrderController {
    
    private final OrderService orderService = new OrderService();

    @POST
    @Path("/load")
    public Response loadOrders() {
        try {
            String result = orderService.loadAllOrders();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/details")
    public Response getOrderDetails(@FormParam("order_id") String orderId) {
        try {
            String result = orderService.getOrderDetails(orderId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/new")
    public Response createNewOrder(
            @FormParam("id") String modelId,
            @FormParam("qty") String quantity,
            @FormParam("delivery_method_id") String deliveryMethodId) {
        try {
            String result = orderService.createNewOrder(modelId, quantity, deliveryMethodId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/updateStatusAfterPayment")
    public Response updateOrderStatusAfterPayment(@FormParam("order_id") String orderId) {
        try {
            String result = orderService.updateOrderStatusAfterPayment(orderId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/cancel")
    public Response cancelOrder(@FormParam("orderId") String orderId) {
        try {
            String result = orderService.cancelOrder(orderId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/userOrders")
    public Response getUserOrders(@FormParam("email") String email) {
        try {
            String result = orderService.getUserOrders(email);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
