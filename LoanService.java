public class LoanService {
    private final LoanRepository repo;

    public LoanService(LoanRepository repo) {
        this.repo = repo;
    }

    public BorrowResult borrow(String itemId, String studentId) {
        if (itemId == null || itemId.isBlank() || studentId == null || studentId.isBlank()) {
            return new BorrowResult(BorrowStatus.INVALID_INPUT, null);
        }
        if (repo.findItem(itemId) == null) {
            return new BorrowResult(BorrowStatus.ITEM_NOT_FOUND, null);
        }
        if (!repo.tryMarkBorrowed(itemId)) {
            return new BorrowResult(BorrowStatus.ITEM_UNAVAILABLE, null);
        }
        try {
            String loanId = repo.saveLoan(studentId, itemId);
            return new BorrowResult(BorrowStatus.CREATED, loanId);
        } catch (RuntimeException e) {
            repo.releaseItem(itemId);
            throw e;
        }
    }
}