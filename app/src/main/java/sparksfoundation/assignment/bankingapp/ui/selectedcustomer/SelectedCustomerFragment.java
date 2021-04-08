package sparksfoundation.assignment.bankingapp.ui.selectedcustomer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import sparksfoundation.assignment.bankingapp.R;
import sparksfoundation.assignment.bankingapp.database.UserItem;

public class SelectedCustomerFragment extends Fragment {

    private SelectedCustomerViewModel mViewModel;

    @BindView(R.id.selected_cust_balance)
    MaterialTextView balanceText;

    @BindView(R.id.selected_cust_email)
    MaterialTextView emailText;

    @BindView(R.id.selected_cust_name)
    MaterialTextView  nameText;

    DecimalFormat myFormatter;

    public static SelectedCustomerFragment newInstance() {
        return new SelectedCustomerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(SelectedCustomerViewModel.class);

        View root =  inflater.inflate(R.layout.selected_customer_fragment, container, false);

        ButterKnife.bind(this,root);


        myFormatter = new DecimalFormat("#############.##");

        mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<UserItem>() {
            @Override
            public void onChanged(UserItem userItem) {
                if (null != userItem)
                {
                    nameText.setText(userItem.getName());
                    emailText.setText(userItem.getEmail());
                    balanceText.setText(new StringBuilder(getContext().getResources().getString(R.string.rs_sign))
                            .append(
                                    myFormatter.format((userItem.getCurrentBalance()))
                            ));
                }
            }
        });


        return root;
    }



}