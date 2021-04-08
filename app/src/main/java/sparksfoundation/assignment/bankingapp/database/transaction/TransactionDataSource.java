package sparksfoundation.assignment.bankingapp.database.transaction;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sparksfoundation.assignment.bankingapp.database.TransactionItem;

public interface TransactionDataSource {
    Flowable<List<TransactionItem>> getUserTransactions(int userId);

    Completable addTransaction(TransactionItem... transactionItems);
}
