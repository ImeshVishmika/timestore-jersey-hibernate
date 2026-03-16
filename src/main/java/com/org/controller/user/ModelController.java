package com.org.controller.user;

import com.org.service.ModelService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/model")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModelController {
    
    private final ModelService modelService = new ModelService();

    @POST
    @Path("/load")
    public Response loadModels(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String productId = JsonRequestUtil.getString(body, "product_id");
            if (productId == null) {
                productId = JsonRequestUtil.getString(body, "model_id");
            }
            String result = modelService.loadModels(productId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/update")
    public Response updateModel(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String modelId = JsonRequestUtil.getString(body, "model_id");
            String modelName = JsonRequestUtil.getString(body, "model_name");
            String price = JsonRequestUtil.getString(body, "price");
            String qty = JsonRequestUtil.getString(body, "qty");
            String result = modelService.updateModel(modelId, modelName, price, qty);
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
        System.out.println(id);
        return modelService.getImg(id);
    }
}
