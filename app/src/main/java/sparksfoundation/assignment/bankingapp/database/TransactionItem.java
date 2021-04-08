package sparksfoundation.assignment.bankingapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Transactions", indices = @Index(value = "transactionId",unique = true))
public class TransactionItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    private int transactionId;

    @ColumnInfo(name = "userId")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @ColumnInfo(name = "senderName")
    private String senderName;

    @ColumnInfo(name = "receiverName")
    private String receiverName;

    @ColumnInfo(name = "transactionDate")
    private String transactionDate;

    @ColumnInfo(name = "amountSent")
    private double amountSent;

    public TransactionItem() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(double amountSent) {
        this.amountSent = amountSent;
    }
}
