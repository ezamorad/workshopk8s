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

import api.dao.AssetDao;
import api.dao.Dao;
import api.domain.Asset;

@Path("/assets")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)

public class AssetService {
    private static final List<Asset> assets = new ArrayList<>();
	
//	static {
//		assets.add(new Asset(1L, "thinkpad", "1234", "Portatil Lenovo", "OscarZ", "active"));
//		assets.add(new Asset(2L, "macbook", "5678", "Portatil Apple", "JuanR", "inactive"));
//		assets.add(new Asset(3L, "ipad", "9123", "Tableta Apple", "MariaC", "active"));
//	}
	
	@GET
	@Path("/{serial}")
	public Response getAsset(@PathParam("serial") String serial) {
		System.out.println("serial ==> " + serial);

		System.out.println("getAsset1");
		Dao<Asset> assetDao = new AssetDao();
		System.out.println("getAsset2");
		Asset asset = assetDao.get(serial);

		//Throws error in case of the asset not found
		if(asset == null) 
			return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();

		return Response.ok(asset).build();
	}

	@GET
	public Response findAllAssets() {

		System.out.println("getAsset1");
		Dao<Asset> assetDao = new AssetDao();
		System.out.println("getAsset2");
		List assetList = assetDao.getAll();

		//Throws error in case of the asset not found
		if(assetList.isEmpty()) 
			return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();

		return Response.ok(assetList).build();
	}

	@POST
	public Response createAsset(Asset asset) {
		
		System.out.println("createAsset1");
		Dao<Asset> assetDao = new AssetDao();
		System.out.println("createAsset2");
		assetDao.save(asset);
		System.out.println("createAsset3");
		return Response.ok().build();
	}
	
//	@PUT
//	public Response updateAsset(Asset assetRequest) {
//		List<Asset> found = this.assets.stream().filter(x -> assetRequest.getId() == x.getId()).collect(Collectors.toList());
//
//		//Throws error in case of the asset not found
//		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();
//
//		Asset updateAsset = found.get(0);
//		updateAsset.setName(assetRequest.getName());
//		updateAsset.setSerial(assetRequest.getSerial());
//		updateAsset.setDetails(assetRequest.getDetails());
//		updateAsset.setOwner(assetRequest.getOwner());
//		updateAsset.setStatus(assetRequest.getStatus());
//		return Response.ok(updateAsset).build();
//	}
//
//	@DELETE
//	@Path("{assetId}")
//	public Response deleteAsset( @PathParam("assetId") long assetId) {
//		System.out.println("assetId ==> " + assetId);
//		List<Asset> found = this.assets.stream().filter(x -> assetId == x.getId().longValue()).collect(Collectors.toList());
//
//		//Throws error in case of the asset not found
//		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Asset not found").build();
//
//		Asset updateAsset = found.get(0);
//		this.assets.remove(updateAsset);
//		return Response.noContent().build();
//	}
	
	
	@HEAD
	public Response pingAssetsService() {
		return Response.noContent().header("running", true).build();
	}
	
}
