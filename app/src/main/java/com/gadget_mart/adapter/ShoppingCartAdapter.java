package com.gadget_mart.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.model.MainOrderDetails;
import com.gadget_mart.ui.cart.ShoppingCartFragment;
import com.gadget_mart.util.Constant;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartHolder> {

    private Context context;
    private List<MainOrderDetails> list;
    private int retailerId;

    public ShoppingCartAdapter(Context context, List<MainOrderDetails> mainOrderDetails, int idRetailer) {
        this.context = context;
        this.list = mainOrderDetails;
        this.retailerId = idRetailer;
    }

    @NonNull
    @Override
    public ShoppingCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new ShoppingCartAdapter.ShoppingCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartHolder holder, int position) {
        holder.bind(list.get(position), position, context, retailerId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ShoppingCartHolder extends RecyclerView.ViewHolder {

        private final TextView cart_item_qty_text, cart_item_name_text, cart_item_total_text, cart_item_remove_text;

        public ShoppingCartHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_qty_text = itemView.findViewById(R.id.cart_item_qty_text);
            cart_item_name_text = itemView.findViewById(R.id.cart_item_name_text);
            cart_item_total_text = itemView.findViewById(R.id.cart_item_total_text);
            cart_item_remove_text = itemView.findViewById(R.id.cart_item_remove_text);
        }

        public void bind(final MainOrderDetails mainOrderDetails, int position, Context context, int idRetailer) {
            cart_item_qty_text.setText(mainOrderDetails.getQuantity() + "");
            cart_item_name_text.setText(mainOrderDetails.getProductName());
            BigDecimal total = mainOrderDetails.getItemTotal();
            total = total.setScale(2, RoundingMode.CEILING);
            cart_item_total_text.setText("LKR " + total);

            cart_item_remove_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constant.mainOrderDetailsList.remove(position);
                    notifyDataSetChanged();
                    Snackbar.make(view, "Item Removed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
                    Bundle args = new Bundle();
                    args.putInt("TYPE",idRetailer);
                    shoppingCartFragment.setArguments(args);
                    FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, shoppingCartFragment);
                    fragmentTransaction.addToBackStack(shoppingCartFragment.getClass().getName());
                    fragmentTransaction.commit();
                }
            });
        }
    }
}
