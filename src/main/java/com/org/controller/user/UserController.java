package com.org.controller.user;

import com.google.gson.Gson;
import com.org.dto.UserDTO;
import com.org.service.UserService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();


    @GET
    public Response getAllUsers() {
        try {
            String users = userService.getAllUsers();
            return Response.ok().entity(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error fetching users: " + e.getMessage() + "\"}").build();
        }
    }


    @GET
    @Path("/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        try {
            String user = userService.getUserByEmail(email);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"state\":false,\"data\":null,\"error\":\"User not found with email: " + email + "\"}").build();
            }
            
            return Response.ok().entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error fetching user: " + e.getMessage() + "\"}").build();
        }
    }
    

    @POST
    public Response createUser(String jsonData) {
        try {
            UserDTO userDTO = gson.fromJson(jsonData,UserDTO.class);
            String createdUser = userService.createUser(userDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(createdUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error creating user: " + e.getMessage() + "\"}").build();
        }
    }
    

    @PUT
    @Path("/{email}")
    public Response updateUser(@PathParam("email") String email, UserDTO userDTO) {
        try {
            String createdUser = userService.createUser(userDTO);
            return Response.status(Response.Status.CREATED)
                    .entity(createdUser).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error creating user: " + e.getMessage() + "\"}").build();
        }
    }
}
    
