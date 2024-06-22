package com.example.kyshatbmoznodeliverer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kyshatbmoznodeliverer.Adapter.OrderCartAdapter;
import com.example.kyshatbmoznodeliverer.Models.FoodInCart;
import com.example.kyshatbmoznodeliverer.Models.Order;
import com.example.kyshatbmoznodeliverer.Models.Restaurant;
import com.example.kyshatbmoznodeliverer.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OneOrderActivity extends AppCompatActivity {

    String idOrder = "";
    String idRest, idUser;
    TextView tvAddress, tvEntrance, tvFloor, tvFlat, tvDate, tvStatus, tvTotalPrice, tvRestNameOrder, tvNoOrderNow;
    RecyclerView rvCartInOrder;
    Button btnDeliverDoes;
    LinearLayout llfirst;
    FirebaseDatabase db;
    DatabaseReference ordRef;
    DatabaseReference restRef;
    DatabaseReference userRef;
    FirebaseAuth auth;
    FirebaseUser curUser;
    List<FoodInCart> foodInCartList = new ArrayList<>();
    OrderCartAdapter orderCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_order);

        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();
        idUser = curUser.getUid();

        llfirst = findViewById(R.id.llfirst);
        tvNoOrderNow = findViewById(R.id.tvNoOrderNow);
        tvAddress = findViewById(R.id.tvAddress);
        tvEntrance = findViewById(R.id.tvEntrance);
        tvFloor = findViewById(R.id.tvFloor);
        tvFlat = findViewById(R.id.tvFlat);
        btnDeliverDoes = findViewById(R.id.btnDeliverDoes);
        tvRestNameOrder = findViewById(R.id.tvRestNameOrder);
        tvDate = findViewById(R.id.tvDate);
        tvStatus = findViewById(R.id.tvStatus);
        rvCartInOrder = findViewById(R.id.rvCartInOrder);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        rvCartInOrder.setLayoutManager(new LinearLayoutManager(this));
        orderCartAdapter = new OrderCartAdapter(this, foodInCartList);
        rvCartInOrder.setAdapter(orderCartAdapter);

        db = FirebaseDatabase.getInstance();
        ordRef = db.getReference("Orders");
        restRef = db.getReference("Restaurant");
        userRef = db.getReference("Users").child(idUser);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                if (!user.isOnOrder()) {
                    tvNoOrderNow.setVisibility(View.VISIBLE);
                    llfirst.setVisibility(View.GONE);
                }
                else {
                    tvNoOrderNow.setVisibility(View.GONE);
                    llfirst.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (foodInCartList.size()>0) foodInCartList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    assert order != null;
                    if (order.getIdDel()!=null){
                        if (order.getIdDel().equals(curUser.getUid())&&order.getStatus().equals("В пути")) {
                            idRest = order.getIdRest();
                            idOrder = order.getId();
                            tvAddress.setText(order.getAddress());
                            tvDate.setText(order.getDate());
                            tvStatus.setText(order.getStatus());
                            tvTotalPrice.setText(order.getPrice());
                            foodInCartList.addAll(order.getOrderList());
                            tvEntrance.setText(order.getEntrance());
                            tvFloor.setText(order.getFloor());
                            tvFlat.setText(order.getFlat());
                        }
                    }
                }
                orderCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        restRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Restaurant restaurant = ds.getValue(Restaurant.class);
                    assert restaurant!=null;
                    if (restaurant.getId().equals(idRest)) tvRestNameOrder.setText(restaurant.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDeliverDoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSureDelWindow();
            }
        });

    }

    private void showSureDelWindow() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Доставка заказа");
        LayoutInflater inflater = LayoutInflater.from(this);
        View sureDeliver = inflater.inflate(R.layout.sure_deliver, null);
        dialog.setView(sureDeliver);
        List<Order> orders = new ArrayList<>();
        List<User> users = new ArrayList<>();

        ordRef.child(idOrder).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order order = snapshot.getValue(Order.class);
                assert order!=null;
                orders.add(order);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user!=null;
                users.add(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.setPositiveButton("Доставил", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                orders.get(0).setStatus("Завершен");
                users.get(0).setOnOrder(false);
                userRef.setValue(users.get(0));
                ordRef.child(idOrder).setValue(orders.get(0));
            }
        });

        dialog.show();

    }
}