package sparksfoundation.assignment.bankingapp.ui.selectedcustomer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sparksfoundation.assignment.bankingapp.R;
import sparksfoundation.assignment.bankingapp.adapter.AllCustomerAdapter;
import sparksfoundation.assignment.bankingapp.adapter.TransactionAdapter;
import sparksfoundation.assignment.bankingapp.common.Common;
import sparksfoundation.assignment.bankingapp.database.Database;
import sparksfoundation.assignment.bankingapp.database.LocalUserDataSource;
import sparksfoundation.assignment.bankingapp.database.TransactionItem;
import sparksfoundation.assignment.bankingapp.database.UserDataSource;
import sparksfoundation.assignment.bankingapp.database.UserItem;
import sparksfoundation.assignment.bankingapp.database.transaction.LocalTransactionDataSource;
import sparksfoundation.assignment.bankingapp.database.transaction.TransactionDataSource;

public class SelectedCustomerFragment extends Fragment {

    private SelectedCustomerViewModel mViewModel;

    @BindView(R.id.selected_cust_balance)
    MaterialTextView balanceText;

    @BindView(R.id.selected_cust_email)
    MaterialTextView emailText;

    @BindView(R.id.selected_cust_name)
    MaterialTextView  nameText;

    @BindView(R.id.selected_cust_empty_transaction)
    TextView empty_transaction;

    @BindView(R.id.selected_cust_add_new_transaction)
    MaterialTextView add_new_transaction;


    @BindView(R.id.selected_transaction_recycler)
    RecyclerView transactionRecycler;


    DecimalFormat myFormatter;
    CompositeDisposable compositeDisposable;
    TransactionDataSource transactionDataSource;
    UserDataSource userDataSource;

    public static SelectedCustomerFragment newInstance() {
        return new SelectedCustomerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(SelectedCustomerViewModel.class);

        View root =  inflater.inflate(R.layout.selected_customer_fragment, container, false);

        ButterKnife.bind(this,root);

        compositeDisposable = new CompositeDisposable();
        transactionDataSource = new LocalTransactionDataSource(Database.getInstance(getContext()).transactionDAO());
        userDataSource = new LocalUserDataSource(Database.getInstance(getContext()).userDAO());
        myFormatter = new DecimalFormat("#############.##");

        mViewModel.initDataSource(getContext(), Common.selectedCustomer.getUserId());

        mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem userItem) {
                if (null != userItem)
                {
                    nameText.setText(userItem.getName());
                    emailText.setText(userItem.getEmail());
                    balanceText.setText(new StringBuilder(getContext().getResources().getString(R.string.rs_sign))
                            .append("  ")
                            .append(
                                    myFormatter.format((userItem.getCurrentBalance()))
                            ));
                }
            }
        });


        mViewModel.getTransactionsList().observe(getViewLifecycleOwner(), new Observer<List<TransactionItem>>() {
            @Override
            public void onChanged(List<TransactionItem> transactionItems) {

                if (transactionItems.size()>0) {
                    empty_transaction.setVisibility(View.GONE);
                    transactionRecycler.setVisibility(View.VISIBLE);
                    transactionRecycler.addItemDecoration(new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation()));
                    TransactionAdapter transactionAdapter = new TransactionAdapter(transactionItems, getContext());
                    transactionRecycler.setAdapter(transactionAdapter);
                }
                else
                {
                    empty_transaction.setVisibility(View.VISIBLE);
                    transactionRecycler.setVisibility(View.GONE);
                }

            }
        });

        transactionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        add_new_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
        return root;
    }

    private void showBottomSheet() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottomsheet_show_customers);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        MaterialButton transfer = bottomSheetDialog.findViewById(R.id.bts_show_cust_btn_transfer);
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.bts_show_cust_recycler);
        TextInputLayout inputLayout = bottomSheetDialog.findViewById(R.id.bts_show_cust_inputLayout);

        AllCustomerAdapter allCustomerAdapter = new AllCustomerAdapter(Common.usersList,getContext(),false);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(allCustomerAdapter);

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null ==Common.userToTransferMoney)
                {
                    Toast.makeText(getContext(), "Kindly select a user to transfer.", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if( inputLayout.getEditText().getText().toString().isEmpty())
                {
                    Toast.makeText(getContext(), "Kindly Enter a valid amount to transfer.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YY hh:mm  a");
                    Double amount = Double.parseDouble( inputLayout.getEditText().getText().toString());

                    TransactionItem senderTransaction = new TransactionItem();
                    senderTransaction.setAmountSent(Double.parseDouble( inputLayout.getEditText().getText().toString()));
                    senderTransaction.setCredit(false);
                    senderTransaction.setTransactionDate(simpleDateFormat.format(date.getTime()));
                    senderTransaction.setReceiverName(Common.userToTransferMoney.getName());
                    senderTransaction.setSenderName(Common.selectedCustomer.getName());
                    senderTransaction.setUserId(Common.selectedCustomer.getUserId());
                    System.out.println("sender tid = "+senderTransaction.getTransactionId());

                    TransactionItem receiverTransaction = new TransactionItem();
                    receiverTransaction.setAmountSent(Double.parseDouble( inputLayout.getEditText().getText().toString()));
                    receiverTransaction.setCredit(true);
                    receiverTransaction.setTransactionDate(simpleDateFormat.format(date.getTime()));
                    receiverTransaction.setReceiverName(Common.userToTransferMoney.getName());
                    receiverTransaction.setSenderName(Common.selectedCustomer.getName());
                    receiverTransaction.setUserId(Common.userToTransferMoney.getUserId());

                    System.out.println("receiver tid = "+receiverTransaction.getTransactionId());


                    compositeDisposable.add(transactionDataSource.addTransaction(senderTransaction)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()-> {

                                compositeDisposable.add(transactionDataSource
                                        .addTransaction(receiverTransaction)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(()->
                                                {
                                            updateUsersData(amount);
                                            bottomSheetDialog.dismiss();
                                                }
                                                ,throwable -> {
                                                    System.out.println("Selected Add Trans  "+throwable.getMessage());
                                                }));
                    }
                    ,throwable -> {
                        System.out.println("Selected Add Trans  "+throwable.getMessage());
                            }));

                }
            }
        });

        bottomSheetDialog.show();
    }

    private void updateUsersData(Double amount) {

        UserItem receiver =Common.userToTransferMoney;
        receiver.setCurrentBalance((receiver.getCurrentBalance()+amount));

        UserItem sender =Common.selectedCustomer;

        sender.setCurrentBalance((sender.getCurrentBalance()-amount));
        System.out.println("rec = "+receiver.getCurrentBalance()+" sender = "+sender.getCurrentBalance());

        userDataSource.updateUser(receiver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        userDataSource.updateUser(sender)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(getContext(), "Transaction Successful", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                        System.out.println("sender user error "+e.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        System.out.println("receiver user error "+e.getMessage());
                    }
                });

    }


}