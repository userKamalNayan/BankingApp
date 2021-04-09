package sparksfoundation.assignment.bankingapp.database.transaction;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import sparksfoundation.assignment.bankingapp.database.LocalUserDataSource;
import sparksfoundation.assignment.bankingapp.database.TransactionItem;

public class LocalTransactionDataSource implements TransactionDataSource {

    TransactionDAO transactionDAO;
    public LocalTransactionDataSource(TransactionDAO transactionDAO)
    {
        this.transactionDAO= transactionDAO;
    }


    @Override
    public Flowable<List<TransactionItem>> getUserTransactions(int userId) {
        return transactionDAO.getUserTransactions(userId);
    }

    @Override
    public Flowable<List<TransactionItem>> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    @Override
    public Completable addTransaction(TransactionItem... transactionItems) {
        return transactionDAO.addTransaction(transactionItems);
    }
}
