package com.org.controller.user;

import com.org.service.ModelService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/model")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ApiModelController {
    
    private final ModelService modelService = new ModelService();

    @POST
    @Path("/load")
    public Response loadModels(
            @FormParam("product_id") String productId) {
        try {
            String result = modelService.loadModels(productId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/update")
    public Response updateModel(
            @FormParam("model_id") String modelId,
            @FormParam("model_name") String modelName,
            @FormParam("price") String price,
            @FormParam("qty") String qty) {
        try {
            String result = modelService.updateModel(modelId, modelName, price, qty);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
