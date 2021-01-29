package com.gadget_mart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.async.ImageLoadTask;
import com.gadget_mart.model.RetailerModel;

import java.util.List;

public class MainRetailerAdapter extends RecyclerView.Adapter<MainRetailerAdapter.RetailersHolder> {

    private List<RetailerModel> retailerModelList;
    private Context context;

    public MainRetailerAdapter(Context context, List<RetailerModel> modelList) {
        this.context = context;
        this.retailerModelList = modelList;
    }

    @NonNull
    @Override
    public RetailersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reatiler_custom_layout, parent, false);
        return new RetailersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailersHolder holder, int position) {
        holder.bind(retailerModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return retailerModelList.size();
    }

    public static class RetailersHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public RetailersHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.retailer_image);
            textView = itemView.findViewById(R.id.retailer_text);
        }

        public void bind(final RetailerModel retailerModel) {
            textView.setText(retailerModel.getRetailerName());
            if(retailerModel.getIdretailers() == 1 || retailerModel.getIdretailers() == 2 || retailerModel.getIdretailers() == 3) {
                new ImageLoadTask(retailerModel.getRetailerImage(), imageView).execute();
            }
        }
    }

}
