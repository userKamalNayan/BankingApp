package sparksfoundation.assignment.bankingapp.ui;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sparksfoundation.assignment.bankingapp.R;
import sparksfoundation.assignment.bankingapp.adapter.AllCustomerAdapter;
import sparksfoundation.assignment.bankingapp.common.Common;
import sparksfoundation.assignment.bankingapp.database.UserItem;

public class AllCustomerFragment extends Fragment {

    private AllCustomerViewModel mViewModel;

    @BindView(R.id.all_cust_recycler)
    RecyclerView recyclerView;


    public static AllCustomerFragment newInstance() {
        return new AllCustomerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AllCustomerViewModel.class);

        View root =inflater.inflate(R.layout.all_customer_fragment, container, false);
        ButterKnife.bind(this,root);
        mViewModel.initSource(getContext());

        mViewModel.getUsersList().observe(getViewLifecycleOwner(), new Observer<List<UserItem>>() {
            @Override
            public void onChanged(List<UserItem> userItems) {

                AllCustomerAdapter allCustomerAdapter = new AllCustomerAdapter(userItems,getContext(),true);
                recyclerView.setAdapter(allCustomerAdapter);
                Common.usersList = userItems;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation()));

        return root;
    }



}