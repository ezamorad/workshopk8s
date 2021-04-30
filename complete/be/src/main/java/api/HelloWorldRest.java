package api;

import javax.ws.rs.Consumes; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType; 
import javax.ws.rs.core.Response; 

@Consumes(MediaType.APPLICATION_JSON) 

@Path("helloworld") 

public class HelloWorldRest {
    @GET  
    @Produces(MediaType.APPLICATION_JSON) 
    public Response sayHello() {     
        return Response.ok("Hello World desde el API REST",MediaType.APPLICATION_JSON).build();   
    } 
}
