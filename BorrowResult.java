public class BorrowResult {
    private final BorrowStatus status;
    private final String loanId;

    public BorrowResult(BorrowStatus status, String loanId) {
        this.status = status;
        this.loanId = loanId;
    }

    public BorrowStatus getStatus(){
        return status;
    }
    
    public String getLoanId(){
        return loanId;
    }
}