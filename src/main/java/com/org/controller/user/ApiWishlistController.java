package com.org.controller.user;

import com.org.service.WishlistService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/wishlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ApiWishlistController {
    
    private final WishlistService wishlistService = new WishlistService();

    @POST
    @Path("/load")
    public Response loadUserWishlist(@FormParam("email") String email) {
        try {
            String result = wishlistService.loadUserWishlist(email);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
