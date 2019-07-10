package hr.fer.zemris.java.hw16.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.model.ImageDB;
import hr.fer.zemris.java.hw16.model.ImageDB.Image;

/**
 * REST application used for gallery rendering
 * @author Matija
 *
 */
@Path("/restgaler")
public class GalerijaJSON {

	/**
	 * Returns a response containing all the available picture tags.
	 * @return a response containing all the available picture tags.
	 */
	@Path("/tag")
	@GET
	@Produces("application/json")
	public Response getTags() {
		Set<String> tags = ImageDB.getImageDB().getTags();

		String result = new Gson().toJson(tags);
		
		return Response.status(Status.OK).entity(result).build();
	}


	/**
	 * Returns a response containing thumbnail details of all the images containing the given tag
	 * @param tag tag of the images whose thumbnail links needs to be returned
	 * @return a response containing thumbnail details of all the images containing the given tag
	 */
	@Path("/tag/{tag}")
	@GET
	@Produces("application/json")
	public Response getThumbnails(@PathParam("tag") String tag) {
		List<Image> thumbs = ImageDB.getImageDB().getImages(tag);
		Set<String> images = new HashSet<String>();
		for(Image img : thumbs) {
			images.add(img.getName());
		}
		
		String result = new Gson().toJson(images);
		
		return Response.status(Status.OK).entity(result).build();
	}
	
	/**
	 * Returns a response containing picture details of the image
	 * @param pic name of the image
	 * @return a response containing picture details of the image
	 */
	@Path("/picture/{pic}")
	@GET
	@Produces("application/json")
	public Response getPicture(@PathParam("pic") String pic) {
		Image img = ImageDB.getImageDB().getImage(pic);
		
		String response = "{" +
						  "\"name\":\"" + img.getName() + "\"," +
						  "\"desc\":\"" + img.getDesc() + "\"," + 
						  "\"tags\":\"";
		
		StringBuilder sb = new StringBuilder();
		sb.append(response);
		
		boolean first = true;
		for(String tag : img.getTags()) {
			if(first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(tag);
		}
		
		sb.append("\"}");
		
		return Response.status(Status.OK).entity(sb.toString()).build();
	}
	
}
