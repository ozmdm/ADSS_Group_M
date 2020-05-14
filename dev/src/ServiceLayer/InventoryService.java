package ServiceLayer;

import InventoryPackage.Inventory;
import MessageTypes.*;

import java.util.HashMap;
public class InventoryService {

    private Inventory inventory;

    public InventoryService(Inventory inventory) {
        this.inventory = new Inventory();
    }

    public Response addItem(String description, int quantityShelf, int quantityStock, double costPrice,
            double salePrice, String position, int minimumQuantity, double weight, String category, String subCategory,
            String sub2Category, String manufacturer) {
        int itemId = this.inventory.addItem(description, quantityShelf, quantityStock, costPrice, salePrice, position,
                minimumQuantity, weight, category, subCategory, sub2Category, manufacturer);
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

    public Response updateItemShelfQuantity(int itemId, int delta) {
        try {
            this.inventory.editShelfQuantity(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Shelf quantity was edited");
        return response;
    }

    public Response updateItemStockQuantity(int itemId, int delta) {
        try {
            this.inventory.editStockQuantity(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Stock quantity was edited");
        return response;
    }

    public Response cancelCard(int itemId, int quantityToCancel) {
        try {
            this.inventory.cancelCard(itemId, quantityToCancel);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Quantity was updated according to cancel card");
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

    public Response updateDamagedItem(int itemId, int delta) {
        try {
            this.inventory.updateDamagedItem(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Damaged quantity for item " + itemId + "was updated");
        return response;
    }

    /*
     * arguments: string of categories: category, subCategory, sub2Category. to
     * generate report for all of the items, input empty array.
     */
    public ResponseT<StockReport> generateStockReport(String[] categories) {
        StockReport report = this.inventory.generateStockReport(categories);
        return new ResponseT<StockReport>(report);
    }

    public ResponseT<Damaged> generateDamagedReport() {
        Damaged report = new Damaged(new HashMap<>());
        report.setDamagedById(this.inventory.generateDamagedReport());

        return new ResponseT<Damaged>(report);
    }

    public ResponseT<ItemWarning> generateWarningReport() {
        ItemWarning report = new ItemWarning(new HashMap<>());
        report.setWarningById(this.inventory.generateWarningReport());

        return new ResponseT<ItemWarning>(report);
    }

    public ResponseT<ToOrder> generateToOrderReport() {
        ToOrder report = new ToOrder();
        report.setOrderById(this.inventory.generateToOrderReport());
        // DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        // System.out.println(df.format(report.dateProduced));

        return new ResponseT<ToOrder>(report);
    }

}
