import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    static class FailingSaveRepository extends InMemoryLoanRepository {
        @Override
        public String saveLoan(String studentId, String itemId) {
            throw new RuntimeException("DB failure");
        }
    }

    @Test
    void borrowSuccess_createsLoanAndMarksItemBorrowed() {
        InMemoryLoanRepository repo = new InMemoryLoanRepository();
        LoanService service = new LoanService(repo);

        BorrowResult result = service.borrow("camera-1", "230101001");

        assertEquals(BorrowStatus.CREATED, result.getStatus());
        assertNotNull(result.getLoanId());
        assertEquals(ItemStatus.BORROWED, repo.findItem("camera-1").getStatus());
    }

    @Test
    void borrowConflict_whenItemAlreadyBorrowed() {
        InMemoryLoanRepository repo = new InMemoryLoanRepository();
        LoanService service = new LoanService(repo);

        service.borrow("camera-1", "230101001");                 // первый берёт
        BorrowResult second = service.borrow("camera-1", "230101002"); // второй опоздал

        assertEquals(BorrowStatus.ITEM_UNAVAILABLE, second.getStatus());
        assertNull(second.getLoanId());
    }

    @Test
    void secondWriteFails_rollsBackItemStatus() {
        FailingSaveRepository repo = new FailingSaveRepository();
        LoanService service = new LoanService(repo);

        assertThrows(RuntimeException.class,
                () -> service.borrow("camera-1", "230101001"));
        assertEquals(ItemStatus.AVAILABLE, repo.findItem("camera-1").getStatus());
    }
}