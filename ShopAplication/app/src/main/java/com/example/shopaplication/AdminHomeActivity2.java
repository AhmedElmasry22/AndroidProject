package com.example.shopaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity2 extends AppCompatActivity {
    private Button btn_Order,btn_logout,btn_maintian,btn_check_approve_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);
        btn_Order=findViewById(R.id.btn_show_Order);
        btn_logout=findViewById(R.id.log_out_admin);
        btn_maintian=findViewById(R.id.maintain_admin);
        btn_check_approve_product=findViewById(R.id.btn_check_Product_Approve);

        btn_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity2.this,AdminOrders.class);
                startActivity(intent);


            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity2.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_maintian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity2.this,Home.class);
                intent.putExtra("Admin","Admins");
                startActivity(intent);
            }
        });
        btn_check_approve_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(AdminHomeActivity2.this,ProductsApproveActivity.class);
              startActivity(intent);

            }
        });

    }
}