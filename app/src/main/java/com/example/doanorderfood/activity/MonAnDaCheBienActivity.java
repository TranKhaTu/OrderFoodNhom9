package com.example.doanorderfood.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.doanorderfood.R;
import com.example.doanorderfood.adapter.ListViewMenuCookerAdapter;
import com.example.doanorderfood.model.HistoryBill;
import com.example.doanorderfood.util.Const;
import com.example.doanorderfood.util.SharePreferenceUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonAnDaCheBienActivity extends AppCompatActivity {

    @BindView(R.id.btnPushBill)
    ImageView btnPushBill;
    @BindView(R.id.btnAddItem)
    ImageView btnAddItem;
    @BindView(R.id.btnPrintBill)
    ImageView btnPrintBill;
    @BindView(R.id.toolbarBill)
    Toolbar toolbarBill;
    @BindView(R.id.rcv_cooker)
    RecyclerView rcvCooker;
    @BindView(R.id.btnShowListChuaCheBien)
    Button btnShowListChuaCheBien;
    @BindView(R.id.line1Manager)
    View line1Manager;
    @BindView(R.id.btnShowListDaCheBien)
    Button btnShowListDaCheBien;
    @BindView(R.id.line2Manager)
    View line2Manager;
    @BindView(R.id.btnShowAllCooker)
    Button btnShowAllCooker;
    @BindView(R.id.line3Manager)
    View line3Manager;
    private ListViewMenuCookerAdapter adap;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;
    private ArrayList<HistoryBill> historyBills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an_da_che_bien);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    private void initData() {

    }

    private void initViews() {
        historyBills = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("HoaDon");
        databaseReference = mDatabase.child("HoaDonChildren");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                    //  Table table = noteSnapshot.getValue(Table.class);
                    HistoryBill historyBill = noteSnapshot.getValue(HistoryBill.class);
                    historyBills.add(historyBill);
//                    arrTable.add(table);
                    adap.notifyDataSetChanged();

//                    String number=noteSnapshot.child("check").getValue(String.class);
//                    Log.d("dong", "onDataChange: "+number);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        line3Manager.setVisibility(View.VISIBLE);
        btnShowAllCooker.setTextColor(Color.parseColor("#ef4b4c"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvCooker.setLayoutManager(layoutManager);
        adap = new ListViewMenuCookerAdapter(historyBills, this, new ListViewMenuCookerAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(int position) {
            }
        });
        rcvCooker.setAdapter(adap);
        adap.notifyDataSetChanged();
        setLine(line3Manager, btnShowAllCooker);
    }

    private void setLine(View v, Button b) {
        line1Manager.setVisibility(View.INVISIBLE);
        line2Manager.setVisibility(View.INVISIBLE);
        line3Manager.setVisibility(View.INVISIBLE);

        btnShowAllCooker.setTextColor(Color.parseColor("#90000000"));
        btnShowListChuaCheBien.setTextColor(Color.parseColor("#90000000"));
        btnShowListDaCheBien.setTextColor(Color.parseColor("#90000000"));

        v.setVisibility(View.VISIBLE);
        b.setTextColor(Color.parseColor("#ef4b4c"));

    }

    @OnClick({R.id.btnShowListChuaCheBien, R.id.btnShowListDaCheBien, R.id.btnShowAllCooker})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnShowListChuaCheBien:
                ArrayList<HistoryBill> cookers1 = new ArrayList<>();
                for (HistoryBill t : historyBills) {
                    if (t.getType().equals("đẩy cho đầu bếp")) {
                        cookers1.add(t);
                    }
                }
                adap = new ListViewMenuCookerAdapter(cookers1, this, new ListViewMenuCookerAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int position) {

                    }
                });
                rcvCooker.setAdapter(adap);
                adap.notifyDataSetChanged();
                setLine(line1Manager, btnShowListChuaCheBien);
                break;
            case R.id.btnShowListDaCheBien:
                ArrayList<HistoryBill> cookers2 = new ArrayList<>();
                for (HistoryBill t : historyBills) {
                    if (t.getType().equals("đã chế biến")) {
                        cookers2.add(t);
                    }
                }
                adap = new ListViewMenuCookerAdapter(cookers2, this, new ListViewMenuCookerAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int position) {

                    }
                });
                rcvCooker.setAdapter(adap);
                adap.notifyDataSetChanged();
                setLine(line2Manager, btnShowListDaCheBien);
                break;
            case R.id.btnShowAllCooker:
                adap = new ListViewMenuCookerAdapter(historyBills, this, new ListViewMenuCookerAdapter.OnItemClickListener() {
                    @Override
                    public void onClickItem(int position) {

                    }
                });
                rcvCooker.setAdapter(adap);
                adap.notifyDataSetChanged();
                setLine(line3Manager, btnShowAllCooker);
                break;


        }
    }
}
