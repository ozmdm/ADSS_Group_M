package ServiceLayer;

import java.util.List;

import DataAccessLaye.Repo;
import ServiceLayer.ServiceObjects.ItemDTO;
import bussinessLayer.InventoryPackage.Inventory;
public class InventoryService {

    private Inventory inventory;

    public InventoryService() {
        this.inventory = Inventory.getInstance();
    }

    public Response addItem(String description, int quantityShelf, int quantityStock, double costPrice,
            double salePrice, String position, int minimumQuantity, double weight, String category, String subCategory,
            String sub2Category, String manufacturer) {
        int itemId = this.inventory.addItem(description, costPrice,  salePrice, position, minimumQuantity, weight, category, subCategory, sub2Category, manufacturer);
        Response response = new Response();
        response.setMessage("New item was added, with id: " + itemId);
        return response;
    }

    public Response editMinimumQuantity(int itemId, int quantity) {
        try {
            this.inventory.editMinimumQuantity(itemId, quantity);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Minimum quantity was edited");
        return response;
    }

    public Response updateItemDescription(int itemId, String description) {
        try {
            this.inventory.editItemDescription(itemId, description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Item description was edited");
        return response;
    }


    public Response updateItemCostPrice(int itemId, int newPrice) {
        try {
            this.inventory.updateItemCostPrice(itemId, newPrice);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Cost price was updated");
        return response;
    }

    public Response updateItemSalePrice(int itemId, int newPrice) {
        try {
            this.inventory.updateItemSalePrice(itemId, newPrice);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Sale price was updated");
        return response;
    }

	public ResponseT<List<ItemDTO>> getItemsList() {
		try{
            return new ResponseT<List<ItemDTO>>(Repo.getInstance().getAllItems());
        } catch(Exception e){
            return new ResponseT<List<ItemDTO>>(e.getMessage());
        }
	}



}
