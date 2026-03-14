package com.org.controller.admin;

import com.org.service.AdminService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class AdminController {
    
    private final AdminService adminService = new AdminService();

    @POST
    @Path("/dashboardStats")
    public Response getDashboardStats() {
        try {
            String result = adminService.getDashboardStats();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
