package com.org.controller.user;

import com.org.service.MessageService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ApiMessageController {
    
    private final MessageService messageService = new MessageService();

    @POST
    @Path("/senders")
    public Response getMessageSenders() {
        try {
            String result = messageService.getMessageSenders();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/userMessages")
    public Response getUserMessages(@FormParam("sender_email") String senderEmail) {
        try {
            String result = messageService.getUserMessages(senderEmail);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/changeState")
    public Response changeMessageState(@FormParam("message_id") String messageId) {
        try {
            String result = messageService.changeMessageState(messageId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\": false, \"message\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
