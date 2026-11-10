# Changelog

## Step 1: Data classes
- Added Item, ItemStatus (AVAILABLE, BORROWED) and Loan.

## Step 2: Repository
- Added LoanRepository interface (findItem, tryMarkBorrowed, saveLoan).
- Added InMemoryLoanRepository. tryMarkBorrowed checks and changes the status in one step.

## Step 3: Service
- Added BorrowStatus and BorrowResult.
- Added LoanService.borrow with input check, 404 and 409 cases.

## Step 4: Rollback
- Added releaseItem to the repository.
- LoanService now calls releaseItem if saveLoan fails.

## Step 5: Tests
- Added 3 tests: success, conflict, failure with rollback.
- All tests passed (3 successful, 0 failed).

## Step 6: Documentation
- Added README.md with API, data, transaction, cache decision and diagram.