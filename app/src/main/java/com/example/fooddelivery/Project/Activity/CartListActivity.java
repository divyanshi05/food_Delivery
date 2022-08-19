package com.example.fooddelivery.Project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fooddelivery.Project.Adaptor.CartListAdaptor;
import com.example.fooddelivery.Project.Helper.ManagementCart;
import com.example.fooddelivery.Project.Interface.ChangeNumberItemsListener;
import com.example.fooddelivery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    TextView totalFeeTxt, taxTxt, deliveryTxt,totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        
       managementCart=new ManagementCart(this);
        
        initView();
        initList();
        CalculateCart();
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton=findViewById(R.id.cartBtn);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList=findViewById(R.id.recyclerView);
        recyclerViewList=findViewById(R.id.cartView);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        taxTxt=findViewById(R.id.taxTxt);
        deliveryTxt=findViewById(R.id.deliveryTxt);
        emptyTxt=findViewById(R.id.emptyTxt);
        totalTxt=findViewById(R.id.totalTxt);
        scrollView=findViewById(R.id.scrollView3);

    }

    private void initList(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        adapter= new CartListAdaptor( managementCart.getListCart(),this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
               CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if(managementCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
        else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void CalculateCart(){
        double percentTax=0.02;
        double delivery=10;

        tax=Math.round((managementCart.getTotalFee()*percentTax)*100)/100;
        double total=Math.round((managementCart.getTotalFee()+tax +delivery)*100)/100;
        double itemTotal=Math.round(managementCart.getTotalFee()*100)/100;

        totalFeeTxt.setText("Rs."+itemTotal);
        taxTxt.setText("Rs."+tax);
        deliveryTxt.setText("Rs."+delivery);
        totalTxt.setText("Rs."+total);
    }
}