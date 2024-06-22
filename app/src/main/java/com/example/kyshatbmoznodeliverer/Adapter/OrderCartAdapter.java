package com.example.kyshatbmoznodeliverer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kyshatbmoznodeliverer.Models.FoodInCart;
import com.example.kyshatbmoznodeliverer.R;

import java.util.List;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartViewHolder>{

    Context context;
    List<FoodInCart> foodInCartList;

    public OrderCartAdapter(Context context, List<FoodInCart> foodInCartList) {
        this.context = context;
        this.foodInCartList = foodInCartList;
    }

    @NonNull
    @Override
    public OrderCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderCartViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item_cart_in_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderCartViewHolder holder, int position) {

        holder.nameOrderCart.setText(foodInCartList.get(position).getName());
        holder.cartQuanOrderCart.setText(foodInCartList.get(position).getQuantity());
        holder.cartPrice.setText(foodInCartList.get(position).getPrice());

        int pr = Integer.parseInt(foodInCartList.get(position).getPrice());
        int q = Integer.parseInt(foodInCartList.get(position).getQuantity());
        pr = pr * q;
        String totalPrice = String.valueOf(pr);
        holder.cartOnePrice.setText(totalPrice);

    }

    @Override
    public int getItemCount() {
        return foodInCartList.size();
    }
}

class OrderCartViewHolder extends RecyclerView.ViewHolder{

    TextView nameOrderCart, cartQuanOrderCart, cartPrice, cartOnePrice;

    public OrderCartViewHolder(@NonNull View itemView) {
        super(itemView);

        nameOrderCart = itemView.findViewById(R.id.nameOrderCart);
        cartQuanOrderCart = itemView.findViewById(R.id.cartQuanOrderCart);
        cartPrice = itemView.findViewById(R.id.cartPrice);
        cartOnePrice = itemView.findViewById(R.id.cartOnePrice);
    }
}
