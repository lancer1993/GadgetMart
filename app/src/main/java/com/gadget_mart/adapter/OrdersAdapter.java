package com.gadget_mart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.model.Orders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersHolder> {

    private Context context;
    private List<Orders> ordersList;
    private final OnItemClickListener listener;

    public OrdersAdapter(Context context, List<Orders> ordersList, OnItemClickListener listener) {
        this.context = context;
        this.ordersList = ordersList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_custom_layout, parent, false);
        return new OrdersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersHolder holder, int position) {
        holder.bind(ordersList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Orders orders);
    }

    public static class OrdersHolder extends RecyclerView.ViewHolder {

        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        private TextView order_date_text;
        private TextView order_total_text;
        private TextView order_retailer_text;

        public OrdersHolder(@NonNull View itemView) {
            super(itemView);
            order_date_text = itemView.findViewById(R.id.order_date_text);
            order_total_text = itemView.findViewById(R.id.order_total_text);
            order_retailer_text = itemView.findViewById(R.id.order_retailer_text);
        }

        public void bind(final Orders orders, final OrdersAdapter.OnItemClickListener listener) {
            Long orderDate = orders.getOrderDate();
            Date d = new Date(orderDate);
            String formattedDate = simpleDateFormat.format(d);
            order_date_text.setText(formattedDate);

            String retailerName = orders.getRetailers().getRetailerName();
            order_retailer_text.setText(retailerName);

            BigDecimal orderAmount = orders.getOrderAmount();
            orderAmount = orderAmount.setScale(2, RoundingMode.CEILING);
            order_total_text.setText("LKR " + orderAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(orders);
                }
            });
        }
    }
}
