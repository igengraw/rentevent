import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryLoanRepository implements LoanRepository{
    private final Map<String, Item> items = new HashMap<>();
    private final List<Loan> loans = new ArrayList<>();
    private final int nextLoan = 1;

     public InMemoryLoanRepository() {
        items.put("camera-1", new Item("camera-1", "Camera", ItemStatus.AVAILABLE));
    }

     @Override
     public Item findItem(String itemId) {
        return items.get(itemId);
     }

     @Override
     public synchronized boolean tryMarkBorrowed(String itemId){
        Item item = items.get(itemId);
        if (item != null && item.getStatus() == ItemStatus.AVAILABLE){
            item.setStatus(ItemStatus.BORROWED);
            return true;
        }
        return false;
     }


     @Override
     public String saveLoan(String studentId, String itemId){
        String loanId = "L-" + nextLoan;
        loans.add(new Loan(loanId, itemId, studentId,"Borrowed"));
        return loanId;
     }

     @Override
    public synchronized void releaseItem(String itemId) {
        Item item = items.get(itemId);
        if (item != null) {
            item.setStatus(ItemStatus.AVAILABLE);
        }
    }
}