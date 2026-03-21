package com.org.controller.user;

import com.org.service.MessageService;
import com.org.util.JsonRequestUtil;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageController {
    
    private final MessageService messageService = new MessageService();

    @POST
    @Path("/senders")
    public Response getMessageSenders() {
        try {
            String result = messageService.getMessageSenders();
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/userMessages")
    public Response getUserMessages(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String senderEmail = JsonRequestUtil.getString(body, "sender_email");
            if (senderEmail == null) {
                senderEmail = JsonRequestUtil.getString(body, "sender");
            }
            String result = messageService.getUserMessages(senderEmail);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @POST
    @Path("/changeState")
    public Response changeMessageState(String requestBody) {
        try {
            JsonObject body = JsonRequestUtil.parseBody(requestBody);
            String messageId = JsonRequestUtil.getString(body, "message_id");
            String result = messageService.changeMessageState(messageId);
            return Response.ok().entity(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"state\":false,\"data\":null,\"error\":\"Error: " + e.getMessage() + "\"}").build();
        }
    }
}
