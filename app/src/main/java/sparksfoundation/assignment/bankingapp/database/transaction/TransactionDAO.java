package sparksfoundation.assignment.bankingapp.database.transaction;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sparksfoundation.assignment.bankingapp.database.TransactionItem;

@Dao
public interface TransactionDAO {

    @Query("Select * From Transactions where transactionId like :userId")
    Flowable<List<TransactionItem>> getUserTransactions(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addTransaction(TransactionItem... transactionItems);


}
