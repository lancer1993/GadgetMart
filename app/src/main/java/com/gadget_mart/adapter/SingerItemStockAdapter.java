package com.gadget_mart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.model.MainOrderDetails;
import com.gadget_mart.model.singer.ItemStock;
import com.gadget_mart.util.Constant;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SingerItemStockAdapter extends RecyclerView.Adapter<SingerItemStockAdapter.SingerItemAHolder> {

    private Context context;
    private List<ItemStock> list;

    public SingerItemStockAdapter(Context context, List<ItemStock> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SingerItemAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_layout, parent, false);
        return new SingerItemStockAdapter.SingerItemAHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingerItemAHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SingerItemAHolder extends RecyclerView.ViewHolder {

        private final ImageView custom_item_imageView;
        private final TextView custom_item_name_text, custom_item_code_text, custom_item_act_price_text, custom_item_sell_price_text,
                custom_totals_text, custom_item_description_text, custom_item_qty_text, custom_discount_text;
        private final ImageButton custom_item_qty_reduce_imageButton, custom_item_increase_imageButton, custom_item_cart_imageButton;
        private int count;

        public SingerItemAHolder(@NonNull View itemView) {
            super(itemView);
            custom_item_imageView = itemView.findViewById(R.id.custom_item_imageView);
            custom_item_name_text = itemView.findViewById(R.id.custom_item_name_text);
            custom_item_code_text = itemView.findViewById(R.id.custom_item_code_text);
            custom_item_act_price_text = itemView.findViewById(R.id.custom_item_act_price_text);
            custom_item_sell_price_text = itemView.findViewById(R.id.custom_item_sell_price_text);
            custom_totals_text = itemView.findViewById(R.id.custom_totals_text);
            custom_item_description_text = itemView.findViewById(R.id.custom_item_description_text);
            custom_item_qty_text = itemView.findViewById(R.id.custom_item_qty_text);
            custom_discount_text = itemView.findViewById(R.id.custom_discount_text);
            custom_item_qty_reduce_imageButton = itemView.findViewById(R.id.custom_item_qty_reduce_imageButton);
            custom_item_increase_imageButton = itemView.findViewById(R.id.custom_item_increase_imageButton);
            custom_item_cart_imageButton = itemView.findViewById(R.id.custom_item_cart_imageButton);
        }

        public void bind(final ItemStock item) {
            custom_item_name_text.setText(item.getItemStockName());
            custom_item_code_text.setText(item.getProductCode());
            BigDecimal actualPrice = item.getItemPrice();
            actualPrice = actualPrice.setScale(2, RoundingMode.CEILING);
            custom_item_act_price_text.setText("Actual Price : LKR " + actualPrice);
            BigDecimal sellingPrice = item.getItemPrice();
            sellingPrice = sellingPrice.setScale(2, RoundingMode.CEILING);
            custom_item_sell_price_text.setText("Selling Price : LKR" + sellingPrice);
            BigDecimal discount = item.getDiscount();
            discount = discount.setScale(2, RoundingMode.CEILING);
            custom_discount_text.setText("Discount : " + discount);
            if (item.isAvailability() == false) {
                custom_item_cart_imageButton.setClickable(false);
            } else {
                custom_item_cart_imageButton.setClickable(false);
            }
            custom_item_description_text.setText(item.getItemDescription()+"\nWarranty Period - "+item.getWarrantyPeriod()+"\nDelivery - "+item.getDeliveryTimePeriod());
            count = Integer.parseInt(custom_item_qty_text.getText().toString());
            Double tot = item.getSellingPrice().doubleValue() * count;
            custom_totals_text.setText("Total : LKR"+tot);

            custom_item_qty_reduce_imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count > 1) {
                        count = count - 1;
                        custom_item_qty_text.setText(count + "");
                        Double total = item.getSellingPrice().doubleValue() * count;
                        custom_totals_text.setText("Total : LKR"+total);
                    }
                }
            });

            custom_item_increase_imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    count = count + 1;
                    custom_item_qty_text.setText(count + "");
                    Double total = item.getSellingPrice().doubleValue() * count;
                    custom_totals_text.setText("Total : LKR"+total);
                }
            });

            custom_item_cart_imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isAdded = false;
                    for(MainOrderDetails orderDetails : Constant.mainOrderDetailsList){
                        if(orderDetails.getProductCode() == item.getProductCode()){
                            isAdded = true;
                            break;
                        }
                    }

                    if(isAdded == true){
                        Snackbar.make(view, "Item Already Added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        MainOrderDetails mainOrderDetails = new MainOrderDetails();
                        mainOrderDetails.setProductName(item.getItemStockName());
                        mainOrderDetails.setProductCode(item.getProductCode());
                        mainOrderDetails.setProductImage(item.getImageItem());
                        mainOrderDetails.setQuantity(count);
                        Double total = item.getSellingPrice().doubleValue() * count;
                        mainOrderDetails.setItemTotal(new BigDecimal(total));
                        mainOrderDetails.setProductWarrentyPeriod(item.getWarrantyPeriod());
                        mainOrderDetails.setDeliveryPeriod(item.getDeliveryTimePeriod());
                        Constant.mainOrderDetailsList.add(mainOrderDetails);
                        Snackbar.make(view, "Item Added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

    }
}
