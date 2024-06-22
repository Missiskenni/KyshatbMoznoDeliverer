package com.example.kyshatbmoznodeliverer.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kyshatbmoznodeliverer.Models.Order;
import com.example.kyshatbmoznodeliverer.Models.Restaurant;
import com.example.kyshatbmoznodeliverer.Models.User;
import com.example.kyshatbmoznodeliverer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrderViewHolder>{

    Context context;
    List<Order> orderList;

    public OrdersAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference restRef = db.getReference("Restaurant");
        DatabaseReference orderRef = db.getReference("Orders");
        DatabaseReference userRef = db.getReference("Users").child(user.getUid());

        restRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Restaurant restaurant1 = ds.getValue(Restaurant.class);
                    assert restaurant1 != null;
                    if (orderList.get(position).getIdRest().equals(restaurant1.getId())) holder.tvRestOrder.setText(restaurant1.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.tvPriceOrder.setText(orderList.get(position).getPrice());
        holder.tvAddressOrder.setText(orderList.get(position).getAddress());

        List<User> users = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User curUser = snapshot.getValue(User.class);
                users.add(curUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Взятие заказа");
                LayoutInflater inflater = LayoutInflater.from(context);
                View sureToGetOrder = inflater.inflate(R.layout.sure_order, null);
                dialog.setView(sureToGetOrder);

                dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.setPositiveButton("Взять", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        users.get(0).setOnOrder(true);

                        Order order = orderList.get(position);
                        order.setStatus("В пути");
                        order.setIdDel(user.getUid());
                        orderRef.child(order.getId()).setValue(order);

                        userRef.setValue(users.get(0));

                        Toast.makeText(context, "Заказ взят на доставку!", Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

class OrderViewHolder extends RecyclerView.ViewHolder{

    TextView tvRestOrder, tvPriceOrder, tvAddressOrder;
    RelativeLayout rlOrder;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        tvRestOrder = itemView.findViewById(R.id.tvRestOrder);
        tvPriceOrder = itemView.findViewById(R.id.tvPriceOrder);
        tvAddressOrder = itemView.findViewById(R.id.tvAddressOrder);
        rlOrder = itemView.findViewById(R.id.rlOrder);
    }


}