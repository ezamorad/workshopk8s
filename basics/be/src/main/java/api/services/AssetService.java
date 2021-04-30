package api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import api.domain.Asset;

@Path("/assets")
@Consumes(value= MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)

public class AssetService {
    private static final List<Asset> assets = new ArrayList<>();
	
	static {
		assets.add(new Asset(1L, "thinkpad", "1234", "Portatil Lenovo", "OscarZ", "active"));
		assets.add(new Asset(2L, "macbook", "5678", "Portatil Apple", "JuanR", "inactive"));
		assets.add(new Asset(3L, "ipad", "9123", "Tableta Apple", "MariaC", "active"));
	}
	
	@GET
	@Path("/{assetId}")
	public Response getAsset(@PathParam("assetId") long assetId) {
		System.out.println("assetId ==> " + assetId);
		List<Asset> found = this.assets.stream().filter(x -> assetId == x.getId().longValue()).collect(Collectors.toList());

		//Throws error in case of the asset not found
		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();
		
		Asset updateAsset = found.get(0);
		return Response.ok(updateAsset).build();
	}

	@GET
	public Response findAllAssets() {
		return Response.ok(this.assets).build();
	}

	@POST
	public Response createAsset(Asset assetRequest) {
		assetRequest.setId(assets.size()+1l);
		this.assets.add(assetRequest);
		return Response.ok(assetRequest).build();
	}
	
	@PUT
	public Response updateAsset(Asset assetRequest) {
		List<Asset> found = this.assets.stream().filter(x -> assetRequest.getId() == x.getId()).collect(Collectors.toList());
		
		//Throws error in case of the asset not found
		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();
		
		Asset updateAsset = found.get(0);
		updateAsset.setName(assetRequest.getName());
		updateAsset.setSerial(assetRequest.getSerial());
		updateAsset.setDetails(assetRequest.getDetails());
		updateAsset.setOwner(assetRequest.getOwner());
		updateAsset.setStatus(assetRequest.getStatus());
		return Response.ok(updateAsset).build();
	}
	
	@DELETE
	@Path("{assetId}")
	public Response deleteAsset( @PathParam("assetId") long assetId) {
		System.out.println("assetId ==> " + assetId);
		List<Asset> found = this.assets.stream().filter(x -> assetId == x.getId().longValue()).collect(Collectors.toList());
		
		//Throws error in case of the asset not found
		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();
		
		Asset updateAsset = found.get(0);
		this.assets.remove(updateAsset);
		return Response.noContent().build();
	}
	
	
	@HEAD
	public Response pingAssetsService() {
		return Response.noContent().header("running", true).build();
	}
	
}
