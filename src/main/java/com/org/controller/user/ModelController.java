package com.org.controller.user;

import com.google.gson.Gson;
import com.org.dto.FilterDTO;
import com.org.dto.ModelDTO;
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

    private final Gson gson = new Gson();
    private final ModelService modelService = new ModelService();

    @POST
    @Path("/load")
    public Response loadModels(String requestBody) {
        try {
            FilterDTO filterDTO = gson.fromJson(requestBody, FilterDTO.class);
            System.out.println(filterDTO.getProductId());
            String result = modelService.loadModels(filterDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
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
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addModel(String requestBody) {
        try {
            ModelDTO modelDTO = gson.fromJson(requestBody, ModelDTO.class);
            String result = modelService.addModel(modelDTO);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }


    @GET
    @Path("/img/{id}")
    @Consumes(MediaType.WILDCARD)
    @Produces({"image/jpeg", "image/png", "image/webp", "image/gif", MediaType.APPLICATION_OCTET_STREAM})
    public Response getImage(@PathParam("id") Integer id) {
        return modelService.getImg(id);
    }

    @DELETE
    @Path("/{modelId}")
    public Response deleteModel(@PathParam("modelId") Integer modelId) {
        try {
            if (modelId == null || modelId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"state\": false, \"message\": \"Invalid model id\"}").build();
            }
            System.out.println(modelId);
            String result = modelService.deleteModel(modelId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
