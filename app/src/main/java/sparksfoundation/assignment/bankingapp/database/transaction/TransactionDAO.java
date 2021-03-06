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

    @Query("Select * From Transactions where userId like :userId")
    Flowable<List<TransactionItem>> getUserTransactions(int userId);

    @Query("Select * From Transactions ")
    Flowable<List<TransactionItem>> getAllTransactions();


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addTransaction(TransactionItem... transactionItems);


}
