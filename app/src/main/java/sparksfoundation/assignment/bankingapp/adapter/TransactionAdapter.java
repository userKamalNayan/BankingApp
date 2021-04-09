package sparksfoundation.assignment.bankingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sparksfoundation.assignment.bankingapp.R;
import sparksfoundation.assignment.bankingapp.database.TransactionItem;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    List<TransactionItem> transactionsList;
    Context  context;

    public TransactionAdapter(List<TransactionItem> transactionsList, Context context) {
        this.transactionsList = transactionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_recycler_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat myFormatter = new DecimalFormat("#############.##");
System.out.println("adapter tid = "+transactionsList.get(position).getTransactionId());
        holder.date.setText(transactionsList.get(position).getTransactionDate());
        holder.amount.setText(new StringBuilder(context.getResources().getString(R.string.rs_sign))
                .append("  ")
                .append(myFormatter.format((transactionsList.get(position).getAmountSent()))
                ));

        if (transactionsList.get(position).isCredit())
                holder.img_credit_debit.setImageDrawable(ResourcesCompat.
                        getDrawable(context.getResources(),
                                R.drawable.ic_baseline_credit,null));
        else
            holder.img_credit_debit.setImageDrawable(
                    ResourcesCompat.getDrawable(context.getResources(),
                            R.drawable.ic_baseline_debit,null));

        holder.senderName.setText(transactionsList.get(position).getSenderName());
        holder.receiverName.setText(transactionsList.get(position).getReceiverName());
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_trans_date)
        TextView date;

        @BindView(R.id.layout_trans_sender)
        TextView senderName;
        @BindView(R.id.layout_trans_receiver)
        TextView receiverName;
        @BindView(R.id.layout_trans_amount)
        TextView amount;
        @BindView(R.id.layout_trans_img_cr_bd)
        ImageView img_credit_debit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
