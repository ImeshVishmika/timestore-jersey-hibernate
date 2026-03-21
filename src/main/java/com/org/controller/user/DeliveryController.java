package com.org.controller.user;

import com.org.service.DeliveryService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryController {
    
    private final DeliveryService deliveryService = new DeliveryService();

    @POST
    @Path("/load")
    public Response loadDeliveryMethods() {
        try {
            String result = deliveryService.loadDeliveryMethods();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/update")
    public Response updateDeliveryMethod(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String deliveryMethodId = JsonRequestUtil.getString(body, "delivery_method_id");
            if (deliveryMethodId == null) {
                deliveryMethodId = JsonRequestUtil.getString(body, "id");
            }
            String price = JsonRequestUtil.getString(body, "price");
            if (price == null) {
                price = JsonRequestUtil.getString(body, "new_price");
            }
            String result = deliveryService.updateDeliveryMethod(deliveryMethodId, price);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/delete")
    public Response deleteDeliveryMethod(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String deliveryMethodId = JsonRequestUtil.getString(body, "delivery_method_id");
            if (deliveryMethodId == null) {
                deliveryMethodId = JsonRequestUtil.getString(body, "id");
            }
            String result = deliveryService.deleteDeliveryMethod(deliveryMethodId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
