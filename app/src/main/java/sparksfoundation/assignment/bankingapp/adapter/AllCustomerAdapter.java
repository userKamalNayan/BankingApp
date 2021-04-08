package sparksfoundation.assignment.bankingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sparksfoundation.assignment.bankingapp.R;
import sparksfoundation.assignment.bankingapp.common.Common;
import sparksfoundation.assignment.bankingapp.database.UserItem;
import sparksfoundation.assignment.bankingapp.events.OpenFragmentEvent;
import sparksfoundation.assignment.bankingapp.ui.selectedcustomer.SelectedCustomerFragment;

public class AllCustomerAdapter extends RecyclerView.Adapter<AllCustomerAdapter.ViewHolder> {

    List<UserItem> users;
    Context context;

    public AllCustomerAdapter(List<UserItem> users, Context context) {
        this.users = users;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllCustomerAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recycler_all_customer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat myFormatter = new DecimalFormat("#############.##");
        holder.name.setText(users.get(position).getName());
        holder.email.setText(users.get(position).getEmail());
        holder.balance.setText(new StringBuilder(context.getResources().getString(R.string.rs_sign))
                .append(
                        myFormatter.format((users.get(position).getCurrentBalance()))
                )
        );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Common.selectedCustomer = users.get(position);
                EventBus.getDefault().post(
                        new OpenFragmentEvent(new SelectedCustomerFragment(),true,Common.TAG_ALL_CUSTOMER_FRAGMENT));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_recycler_all_cust_balance)
        MaterialTextView balance;

        @BindView(R.id.layout_recycler_all_cust_email)
        MaterialTextView email;


        @BindView(R.id.layout_recycler_all_cust_name)
        MaterialTextView name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

}
