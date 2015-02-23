/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.services;

import com.crowninteractive.handlers.NullHandler;
import java.io.ByteArrayInputStream;
import java.text.ParseException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import johnson4j.dto.User;
import johnson4j.ejb.LafEJB;
import johnson4j.dto.Error;
import johnson4j.dto.UpdateUser;
import johnson4j.entity.LafUser;
import johnson4j.exception.LafException;

/**
 * REST Web Service
 *
 * @author johnson3yo
 */
@Path("/lafresource")
@Stateless
public class LAFResource {

    @Context
    private UriInfo context;
    @EJB
    LafEJB lafEJB;

    @GET
    @Path("/facebookDetail")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFaceBookDetails(@QueryParam("access_token") String token) {
        try {
            String faceBookDetail = lafEJB.getFaceBookDetail(token);

            return Response.ok(faceBookDetail, MediaType.APPLICATION_JSON).build();
        } catch (LafException le) {
            return Response.status(Response.Status.FORBIDDEN).entity(new Error(le.getMessage(),403)).build();
        }
    }

    @GET
    @Path("/faceBookPhoto/{id}")
    @Produces({"image/jpeg", "image/png"})
    public Response getFacebookPhoto(@QueryParam("height") int height, @QueryParam("width") int width, @PathParam("id") String id) {

        byte[] img = lafEJB.getFacebookPhoto(id, height, width);

        return Response.ok(new ByteArrayInputStream(img)).build();
    }

    @POST
    @Path("/createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User usr) {
        try {
            NullHandler nh = new NullHandler(usr);

            johnson4j.entity.LafUser u = lafEJB.createUser(usr);

            return Response.ok(u, MediaType.APPLICATION_JSON).build();
        } catch (LafException | IllegalAccessException | IllegalArgumentException | ParseException no) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new Error(no.getMessage(),400)).build();
        }

    }

    @PUT
    @Path("/updateUser")
    public Response updateUser(UpdateUser usr) {

        try {
            LafUser u = lafEJB.updateUser(usr);
            return Response.status(Response.Status.OK).entity(u).build();
        } catch (ParseException | LafException lf) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Error(lf.getMessage(),404)).build();
        }

    }
    
    

    @DELETE
    @Path("/removeUser/{id}")
    public Response removeUser(@PathParam("id") String id) {
        try {
            lafEJB.removeUser(id);
            return Response.status(Response.Status.OK).entity("user has been removed").build();
        } catch (LafException lf) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Error(lf.getMessage(),404)).build();
        }

    }

    @POST
    @Path("/addFacebook/{laf_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addFacebook(@QueryParam("id") String id, @PathParam("laf_id") String laf_id) {

        johnson4j.entity.LafUser u = lafEJB.addFaceBook(id, laf_id);

        return Response.ok(String.format("Facebook account : %s added", id), MediaType.TEXT_PLAIN).build();
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    public Response login(User usr) {
        try {
            LafUser lu = lafEJB.login(usr);
            return Response.status(Response.Status.ACCEPTED).
                    entity(lu)
                    .build();

        } catch (LafException le) {
            return Response.status(Response.Status.NOT_FOUND).entity(new Error(le.getMessage(),404)).build();
        }

    }

    @GET
    @Path("/lafVideos/{maxResults}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLafVideos(@PathParam("maxResults")String maxResults) {

        return Response.ok(lafEJB.getLafVideos(maxResults), MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Path("/publishFacebookStory/{facebook_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishFacebookStory(@PathParam("facebook_id") String facebook_id,
            @QueryParam("message") String message,
            @QueryParam("link") String link,
            @QueryParam("place") String place,
            @QueryParam("access_token") String access_token) {

        this.lafEJB.publishPost(link, message, access_token, link, place);

        return Response.status(Response.Status.ACCEPTED).entity("").build();

    }
    
    
    
    
    
}
