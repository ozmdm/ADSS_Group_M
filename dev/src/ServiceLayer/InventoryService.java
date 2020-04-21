package ServiceLayer;

import InventoryPackage.Inventory;
import MessageTypes.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InventoryService {

    private Inventory inventory;

    public InventoryService(Inventory inventory) {
        this.inventory = new Inventory();
    }

    public Response<String> addItem(String description, int quantityShelf,
                                    int quantityStock, double costPrice, double salePrice, String position,
                                    int minimumQuantity,
                                    double weight, String category, String subCategory, String sub2Category, String manufacturer) {
        int itemId=this.inventory.addItem( description,  quantityShelf,
         quantityStock,  costPrice,  salePrice,  position,
         minimumQuantity,
         weight,  category,  subCategory,  sub2Category,  manufacturer);
        return new Response<>(null, "New item was added, with id: "+itemId);
    }


    public Response<String> editMinimumQuantity(int itemId, int quantity) {
        try {
            this.inventory.editMinimumQuantity(itemId,quantity);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Minimum quantity was edited");
    }

    public Response<String> updateItemShelfQuantity(int itemId, int delta) {
        try {
            this.inventory.editShelfQuantity(itemId,delta);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Shelf quantity was edited");
    }

    public Response<String> updateItemStockQuantity(int itemId, int delta) {
        try {
            this.inventory.editStockQuantity(itemId,delta);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Stock quantity was edited");
    }

    public Response<String> cancelCard(int itemId, int quantityToCancel) {
        try {
            this.inventory.cancelCard(itemId,quantityToCancel);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Quantity was updated according to cancel card");
    }

    public Response<String> updateItemCostPrice(int itemId, int newPrice) {
        try {
            this.inventory.updateItemCostPrice(itemId,newPrice);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Cost price was updated");
    }

    public Response<String> updateItemSalePrice(int itemId, int newPrice) {
        try {
            this.inventory.updateItemSalePrice(itemId,newPrice);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Sale price was updated");
    }


    public Response<String> updateDamagedItem(int itemId, int delta) {
        try {
            this.inventory.updateDamagedItem(itemId,delta);
        } catch (Exception e) {
            return new Response<>(e,e.getMessage());
        }
        return new Response<>(null, "Damaged quantity for item "+itemId+ "was updated");
    }

    /*
    arguments: string of categories: category, subCategory, sub2Category.
    to generate report for all of the items, input empty array.
     */
    public Response<StockReport> generateStockReport(String[] categories) {
        StockReport report = this.inventory.generateStockReport(categories);
        return new Response<StockReport>(null, report);
    }

    public Response<Damaged> generateDamagedReport() {
        Damaged report = new Damaged(new HashMap<>());
        report.setDamagedById(this.inventory.generateDamagedReport());

        return new Response<Damaged>(null, report);
    }

    public Response<ItemWarning> generateWarningReport() {
        ItemWarning report = new ItemWarning(new HashMap<>());
        report.setWarningById(this.inventory.generateWarningReport());

        return new Response<ItemWarning>(null, report);
    }

    public Response<ToOrder> generateToOrderReport() {
        ToOrder report = new ToOrder();
        report.setOrderById(this.inventory.generateToOrderReport());
//        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//        System.out.println(df.format(report.dateProduced));

        return new Response<ToOrder>(null, report);
    }


}
