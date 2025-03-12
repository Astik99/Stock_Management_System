import java.util.ArrayList;

public class StockManager {
    private ArrayList<Stock> stocks = new ArrayList<>();

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public void removeStock(String name) {
        stocks.removeIf(stock -> stock.getName().equalsIgnoreCase(name));
    }

    public ArrayList<Stock> searchStock(String query) {
        ArrayList<Stock> result = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(stock);
            }
        }
        return result;
    }

    public ArrayList<Stock> getAllStocks() {
        return stocks;
    }
}
