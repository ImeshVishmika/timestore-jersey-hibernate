package com.org.controller.user;

import com.google.gson.Gson;
import com.org.dto.FilterDTO;
import com.org.dto.OrderDTO;
import com.org.service.OrderService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {
    
    private final OrderService orderService = new OrderService();
    private final Gson gson = new Gson();

    @POST
    @Path("/load")
    public Response loadOrders(String requestBody) {
        try {
            System.out.println(requestBody);
            FilterDTO filterDTO = gson.fromJson(requestBody, FilterDTO.class);
            String result = orderService.loadAllOrders(filterDTO);
            System.out.println(result);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/search")
    public Response searchOrders(String requestBody) {
        try {
            System.out.println(requestBody);
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String searchQuery = JsonRequestUtil.getString(body, "searchQuery");
            String result = orderService.searchOrders(searchQuery);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/details")
    public Response getOrderDetails(String requestBody) {
        try {
            OrderDTO orderDTO = gson.fromJson(requestBody, OrderDTO.class);
            String result = orderService.getOrderDetails(orderDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/new")
    public Response createNewOrder(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String modelId = JsonRequestUtil.getString(body, "id");
            String quantity = JsonRequestUtil.getString(body, "qty");
            String deliveryMethodId = JsonRequestUtil.getString(body, "delivery_method_id");
            String result = orderService.createNewOrder(modelId, quantity, deliveryMethodId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/updateStatusAfterPayment")
    public Response updateOrderStatusAfterPayment(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String orderId = JsonRequestUtil.getString(body, "order_id");
            String result = orderService.updateOrderStatusAfterPayment(orderId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/cancel")
    public Response cancelOrder(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String orderId = JsonRequestUtil.getString(body, "orderId");
            String result = orderService.cancelOrder(orderId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/userOrders")
    public Response getUserOrders(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String email = JsonRequestUtil.getString(body, "email");
            String result = orderService.getUserOrders(email);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/statuses")
    public Response loadOrderStatuses() {
        try {
            String result = orderService.loadAllOrderStatuses();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/statusCounts")
    public Response loadOrderStatusCounts() {
        try {
            String result = orderService.loadOrderStatusCounts();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
