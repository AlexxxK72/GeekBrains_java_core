package ru.a777alko.sales777.ui.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ru.a777alko.sales777.App;
import ru.a777alko.sales777.R;
import ru.a777alko.sales777.mvp.model.entities.Order;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private List<Order> orders;
    private OnItemClickListener listener;

    public OrderListAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item = orders.get(position);
        holder.txtOrderDate.setText(convertDateToString(holder.item.getDateOrder()));
        holder.txtOrderStatus.setText(getStatusOrderRu(holder.item.getStatus()));
        holder.txtOrderStatus.setBackgroundColor(Color.parseColor(getColor(holder.item.getStatus())));
        holder.txtClientName.setText(holder.item.getName());
        holder.txtClientPhone.setText(holder.item.getPhone());
        holder.txtOrderComment.setText(holder.item.getComment());
        holder.itemView.setOnClickListener(v -> {
            if(listener != null) listener.onClickListener(holder.item);
        });
    }

    private String convertDateToString(Date date){
        return DateUtils.formatDateTime(App.getInstance(),
                date.getTime(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_NUMERIC_DATE
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);
    }

    private String getStatusOrderRu(String status){
        for (Order.Status s : Order.Status.values()) {
            if(s.toString().equals(status))
                return s.getRu();
        }
        return "";
    }

    private String getColor(String status) {
        for (Order.Status s : Order.Status.values()) {
            if(s.toString().equals(status))
                return s.getColor();
        }
        return "#ffffff";
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onClickListener(Order item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtOrderDate;
        private final TextView txtOrderStatus;
        private final TextView txtClientName;
        private final TextView txtClientPhone;
        private final TextView txtOrderComment;
        public Order item;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderDate = itemView.findViewById(R.id.txt_order_date);
            txtOrderStatus = itemView.findViewById(R.id.txt_order_status);
            txtClientName = itemView.findViewById(R.id.txt_client_name);
            txtClientPhone = itemView.findViewById(R.id.txt_client_phone);
            txtOrderComment = itemView.findViewById(R.id.txt_order_comment);
        }
    }
}
