package com.org.controller.user;

import com.org.service.DeliveryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ApiDeliveryController {
    
    private final DeliveryService deliveryService = new DeliveryService();

    @POST
    @Path("/load")
    public Response loadDeliveryMethods() {
        try {
            String result = deliveryService.loadDeliveryMethods();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/update")
    public Response updateDeliveryMethod(
            @FormParam("delivery_method_id") String deliveryMethodId,
            @FormParam("price") String price) {
        try {
            String result = deliveryService.updateDeliveryMethod(deliveryMethodId, price);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/delete")
    public Response deleteDeliveryMethod(@FormParam("delivery_method_id") String deliveryMethodId) {
        try {
            String result = deliveryService.deleteDeliveryMethod(deliveryMethodId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
