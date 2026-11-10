# Campus Equipment Loan

## Variant
BORROW (my student ID ends in 1).

## Rule that must always be true
One item can have only one active loan at a time.

## API
Method and path: POST /items/{itemId}/loans

Example request:
POST /items/camera-1/loans
{ "studentId": "230101001" }

Example response:
201 Created
{ "loanId": "L-1", "status": "ACTIVE" }

## Status codes
- 201: loan created
- 400: studentId or itemId is empty
- 404: item does not exist
- 409: item is already borrowed

## Data we store
- Item: id, name, status (AVAILABLE or BORROWED)
- Loan: id, itemId, studentId, status

## Repository operations
- findItem(itemId): finds the item
- tryMarkBorrowed(itemId): changes AVAILABLE to BORROWED. Returns true only if it worked
- saveLoan(studentId, itemId): saves the loan
- releaseItem(itemId): sets the item back to AVAILABLE (undo)

## Transaction
The transaction is in the service method borrow.
It starts before tryMarkBorrowed and ends after saveLoan.
Both changes (item becomes BORROWED, loan is saved) must happen together.
If both work, we commit and return 201.

## Failure example
The item was marked BORROWED, but saving the loan failed.
Rollback: we call releaseItem, so the item is AVAILABLE again. No loan is saved.

## Caching
What can be cached safely: name, description and photo of the equipment. They rarely change.
What must be read from the database every time: item status (AVAILABLE or BORROWED).
Why: status changes with every loan. An old cached value could show a borrowed item as free, and two students could get the same item.

## Diagram
Client
  -> Controller (receives the request, returns 201 / 400 / 404 / 409)
  -> Service (borrow)
       [TRANSACTION STARTS]
  -> Repository (tryMarkBorrowed, saveLoan)
  -> Database
       [TRANSACTION ENDS: commit if OK, rollback if saveLoan fails]
  <- result goes back: Database -> Repository -> Service -> Controller -> Client

Here the "Database" is the in-memory storage inside InMemoryLoanRepository.

## Tests
3 tests in LoanServiceTest: success, conflict (409), failure with rollback.
Run in PowerShell:
javac -cp "lib\junit-platform-console-standalone-1.10.2.jar" *.java
java -jar "lib\junit-platform-console-standalone-1.10.2.jar" --class-path . --scan-class-path


## AI use
I used AI as a helper for the code explanations, automated tests, the README text and the README formatting.
I ran all the tests myself, checked the results, and I can explain the code.