package com.gadget_mart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.model.OrderDetails;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsHolder> {

    private Context context;
    private List<OrderDetails> orderDetailsList;

    public OrderDetailsAdapter(Context context, List<OrderDetails> orderDetailsList) {
        this.context = context;
        this.orderDetailsList = orderDetailsList;
    }

    @NonNull
    @Override
    public OrderDetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_custom_layout, parent, false);
        return new OrderDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsHolder holder, int position) {
        holder.bind(orderDetailsList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public static class OrderDetailsHolder extends RecyclerView.ViewHolder {

        private TextView order_item_name_text;
        private TextView item_qty_text;
        private TextView order_item_price_text;

        public OrderDetailsHolder(@NonNull View itemView) {
            super(itemView);
            order_item_name_text = itemView.findViewById(R.id.order_item_name_text);
            item_qty_text = itemView.findViewById(R.id.item_qty_text);
            order_item_price_text = itemView.findViewById(R.id.order_item_price_text);
        }

        public void bind(final OrderDetails orderDetails) {
            order_item_name_text.setText(orderDetails.getProductCode());
            item_qty_text.setText(orderDetails.getQuantity()+"");

            BigDecimal itemTotal = orderDetails.getItemTotal();
            itemTotal = itemTotal.setScale(2, RoundingMode.CEILING);
            order_item_price_text.setText("LKR "+itemTotal);
        }
    }
}
