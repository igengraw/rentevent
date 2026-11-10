public class Loan{
    String id;
    String itemId;
    String studentId;
    String status;

    public Loan(String id, String itemId, String studentId, String status) {
        this.id = id;
        this.itemId = itemId;
        this.studentId = studentId;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public String getItemId() {
        return itemId;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
