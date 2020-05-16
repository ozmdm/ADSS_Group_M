package ServiceLayer;

import DataAccessLaye.*;

import MessageTypes.Damaged;
import MessageTypes.ItemWarning;
import MessageTypes.StockReport;
import MessageTypes.ToOrder;
import ServiceLayer.ServiceObjects.OrderDTO;
import bussinessLayer.BranchPackage.BranchController;
import bussinessLayer.OrderPackage.Order;
import bussinessLayer.SupplierPackage.Supplier;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BranchService {
    private BranchController branchController;

    public BranchService() {
        this.branchController = BranchController.getInstance();
    }

    public Response updateItemShelfQuantity(int branchId, int itemId, int delta) {
        try {
            this.branchController.getBranches().get(branchId).editShelfQuantity(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Shelf quantity was edited, for branch id: " + branchId);
        return response;
    }

    public Response createBranch(String description) {
        int id = 0;
        try {
            id = this.branchController.createBranch(description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Branch was created successfully, with id: " + id);
        return response;
    }

    public Response updateItemStockQuantity(int branchId, int itemId, int delta) {
        try {
            this.branchController.getBranches().get(branchId).editStockQuantity(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Stock quantity was edited, for branch id: " + branchId);
        return response;
    }

    public Response cancelCard(int branchId, int itemId, int quantityToCancel) {
        try {
            this.branchController.getBranches().get(branchId).cancelCard(itemId, quantityToCancel);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Quantity was updated according to cancel card");
        return response;
    }

    public Response updateBranchDescription(int branchId, String description) {
        try {
            this.branchController.getBranches().get(branchId).setDescription(description);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Description was edited, for branch id: " + branchId);
        return response;
    }

    public Response updateDamagedItem(int branchId, int itemId, int delta) {
        try {
            this.branchController.getBranches().get(branchId).updateDamagedItem(itemId, delta);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        Response response = new Response();
        response.setMessage("Damaged quantity for item " + itemId + "was updated, at branchId " + branchId);
        return response;
    }

    /*
     * arguments: string of categories: category, subCategory, sub2Category. to
     * generate report for all of the items, input empty array.
     */
    public ResponseT<StockReport> generateStockReport(int branchId, String[] categories) {
        StockReport report = this.branchController.getBranches().get(branchId).generateStockReport(categories);
        return new ResponseT<StockReport>(report);
    }

    public ResponseT<Damaged> generateDamagedReport(int branchId) {
        Damaged report = new Damaged(new HashMap<>());
        report.setDamagedById(this.branchController.getBranches().get(branchId).generateDamagedReport());

        return new ResponseT<Damaged>(report);
    }

    public ResponseT<ItemWarning> generateWarningReport(int branchId) {
        ItemWarning report = new ItemWarning(new HashMap<>());
        report.setWarningById(this.branchController.getBranches().get(branchId).generateWarningReport());

        return new ResponseT<ItemWarning>(report);
    }

    public ResponseT<ToOrder> generateToOrderReport(int branchId) {
        ToOrder report = new ToOrder();
        report.setOrderById(this.branchController.getBranches().get(branchId).generateToOrderReport());
        // DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        // System.out.println(df.format(report.dateProduced));

        return new ResponseT<ToOrder>(report);
    }

    public ResponseT<ToOrder> generateAndSendOrder(int branchId, List<Supplier> suppliersForBranchId) throws Exception {
        int chosenForAnItem = -1; //represents supplier id that is cheapest
        double priceAfterDiscount = -1;
        boolean foundExistOrderBySup = false;
        ResponseT<ToOrder> report = this.generateToOrderReport(branchId);
        double cheapestPriceForItem = -1;
        List<Order> orderList = new LinkedList<>();
        Supplier chosenSup = suppliersForBranchId.get(0);
        for (Integer itemId : report.getObj().getOrderById().keySet()) {
            for (Supplier sup : suppliersForBranchId) {
                try {
                    priceAfterDiscount = sup.getPriceForItemWithAmountAfterDiscount(itemId, report.getObj().getOrderById().get(itemId)); //arg. 2 returns amount to order
                } catch (Exception e) {
                    continue;
                }
                if (cheapestPriceForItem == -1 || priceAfterDiscount < cheapestPriceForItem) {
                    cheapestPriceForItem = priceAfterDiscount;
                    chosenForAnItem = sup.getSupplierId();
                }
                chosenSup = sup;
            }
            if (orderList.size() > 0) {
                for (Order order : orderList) {
                    if (order.getSupplierId() == chosenForAnItem) {
                        order.addItemToCart(chosenSup.getCatalogItemIdByItem(itemId), report.getObj().getOrderById().get(itemId));
                        foundExistOrderBySup = true;
                        break;
                    }
                    if (foundExistOrderBySup) break;
                    orderList.add(new Order(chosenForAnItem, branchId));
                    foundExistOrderBySup = false;
                }
            } else {
                orderList.add(new Order(chosenForAnItem, branchId));
                orderList.get(0).addItemToCart(chosenSup.getCatalogItemIdByItem(itemId), report.getObj().getOrderById().get(itemId));
                foundExistOrderBySup = false;
            }

        }
        for (Order order : orderList) {
            order.sendOrder();
            OrderDTO orderDTO = order.converToDTO();
            Repo.getInstance().insertOrder(orderDTO);
        }
        return report;
    }


}
