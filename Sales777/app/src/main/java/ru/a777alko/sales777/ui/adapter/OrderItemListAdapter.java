package ru.a777alko.sales777.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ru.a777alko.sales777.R;
import ru.a777alko.sales777.mvp.model.entities.OrderItem;
import ru.a777alko.sales777.mvp.model.image.IImageLoader;
import ru.a777alko.sales777.ui.image.GlideImageLoader;

public class OrderItemListAdapter extends RecyclerView.Adapter<OrderItemListAdapter.ViewHolder> {
    private List<OrderItem> items;
    private IImageLoader loader;

    public OrderItemListAdapter(List<OrderItem> items) {
        this.items = items;
        loader = new GlideImageLoader();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item = items.get(position);
        if (holder.item.getProduct().getPathList() != null)
            loader.loadInto(holder.item.getProduct().getPathList(), holder.ivProductImage);
        holder.txtProductName.setText(holder.item.getProduct().getName());
        if(holder.item.getProduct().getBarcode() != null)
            holder.txtProductBarcode.setText(holder.item.getProduct().getBarcode());
        if(holder.item.getProduct().getVolume() != null)
            holder.txtProductVolume.setText(String.format("%s %s",
                    holder.item.getProduct().getVolume(), holder.item.getProduct().getMeasure()));
        String price = String.format("Цена:\t\t<b>%.2f</b> р.", holder.item.getPriceDate());
        holder.txtProductPrice.setText(Html.fromHtml(price), TextView.BufferType.SPANNABLE);
        String sum = String.format("Сумма:\t<b>%.2f</b> р.", holder.item.getPriceDate() * holder.item.getCount());
        holder.txtProductSum.setText(Html.fromHtml(sum), TextView.BufferType.SPANNABLE);
        holder.txtProductCount.setText(String.valueOf(holder.item.getCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivProductImage;
        private final TextView txtProductName;
        private final TextView txtProductBarcode;
        private final TextView txtProductVolume;
        private final TextView txtProductPrice;
        private final TextView txtProductSum;
        private final Button btnProductAdd;
        private final Button btnProductRemove;
        private final TextView txtProductCount;
        public OrderItem item;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtProductBarcode = itemView.findViewById(R.id.txt_product_barcode);
            txtProductVolume = itemView.findViewById(R.id.txt_product_volume);
            txtProductPrice = itemView.findViewById(R.id.txt_product_price);
            txtProductSum = itemView.findViewById(R.id.txt_product_sum);
            txtProductCount = itemView.findViewById(R.id.txt_count_item);
            btnProductAdd = itemView.findViewById(R.id.btn_add_item);
            btnProductRemove = itemView.findViewById(R.id.btn_remove_item);
        }
    }
}
