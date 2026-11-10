public interface LoanRepository {
    Item findItem(String itemId);
    boolean tryMarkBorrowed(String itemId);
    String saveLoan(String studentId, String itemId);
    void releaseItem(String itemId);    
}