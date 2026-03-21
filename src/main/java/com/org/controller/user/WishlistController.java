package com.org.controller.user;

import com.org.service.WishlistService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/wishlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WishlistController {
    
    private final WishlistService wishlistService = new WishlistService();

    @POST
    @Path("/load")
    public Response loadUserWishlist(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String email = JsonRequestUtil.getString(body, "email");
            String result = wishlistService.loadUserWishlist(email);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
