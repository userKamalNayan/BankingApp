package sparksfoundation.assignment.bankingapp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "Users", indices = {@Index( value = "userId",unique = true)})
public class UserItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "currentBalance")
    private double currentBalance;


    public UserItem() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
