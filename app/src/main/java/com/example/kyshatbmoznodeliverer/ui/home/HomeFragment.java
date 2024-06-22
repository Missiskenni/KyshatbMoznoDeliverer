package com.example.kyshatbmoznodeliverer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kyshatbmoznodeliverer.Adapter.OrdersAdapter;
import com.example.kyshatbmoznodeliverer.Models.Order;
import com.example.kyshatbmoznodeliverer.Models.User;
import com.example.kyshatbmoznodeliverer.R;
import com.example.kyshatbmoznodeliverer.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rvOrders;
    TextView tvAlreadyHave, tvNoOrders;
    OrdersAdapter ordersAdapter;
    List<Order> orderList = new ArrayList<>();

    FirebaseAuth auth;
    FirebaseUser curUser;
    FirebaseDatabase db;
    DatabaseReference ordRef;
    DatabaseReference userRef;
    boolean onOrder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onOrder = false;

        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ordRef = db.getReference("Orders");
        userRef = db.getReference("Users").child(curUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                onOrder = user.isOnOrder();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rvOrders = view.findViewById(R.id.rvOrders);
        tvAlreadyHave = view.findViewById(R.id.tvAlreadyHave);
        tvNoOrders = view.findViewById(R.id.tvNoOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordersAdapter = new OrdersAdapter(getActivity(), orderList);
        rvOrders.setAdapter(ordersAdapter);

        ordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (orderList.size()>0) orderList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Order order = ds.getValue(Order.class);
                    assert order != null;
                    if (order.getIdDel()!=null){
                        if (order.getIdDel().equals(curUser.getUid())&&order.getStatus().equals("В пути")) onOrder=true;
                    }
                    if (order.getStatus().equals("Завершен")||order.getStatus().equals("В пути"));
                    else {
                        orderList.add(order);
                    }
                }
                ordersAdapter.notifyDataSetChanged();
                if (ordersAdapter.getItemCount()==0) tvNoOrders.setVisibility(View.VISIBLE);
                else tvNoOrders.setVisibility(View.GONE);
                if (onOrder) {
                    rvOrders.setVisibility(View.GONE);
                    tvNoOrders.setVisibility(View.GONE);
                    tvAlreadyHave.setVisibility(View.VISIBLE);
                }
                else {
                    rvOrders.setVisibility(View.VISIBLE);
                    tvAlreadyHave.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}