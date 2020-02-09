package com.example.meeteatsave;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdsRecyclerViewAdapter.SelectedUser {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AdsRecyclerViewAdapter adsRecyclerViewAdapter;
    private DatabaseReference mDatabase;
    private ArrayList<Ads> adsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerview);
        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        Intent intent= getIntent();
        if (intent.hasExtra("search")){
            searchViewContent();

        } else if (intent.hasExtra("offers")){
            mDatabase = FirebaseDatabase.getInstance().getReference("Database/Ads/");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adsArrayList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Ads ads = dataSnapshot1.getValue(Ads.class);
                        if (ads.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        adsArrayList.add(ads);
                    }
                    adsRecyclerViewAdapter = new AdsRecyclerViewAdapter(adsArrayList, MainActivity.this);
                    recyclerView.setAdapter(adsRecyclerViewAdapter);


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        } else {
            searchViewContent();
        }




        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.secondaryTextColor));

        navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener((MenuItem item) -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.search:
                    startActivity(new Intent(this, MainActivity.class).putExtra("search", "search"));
                    break;
                case R.id.makeOffer:
                    startActivity(new Intent(this, AdActivity.class));
                    break;
                case R.id.Offers:
                    startActivity(new Intent(this, MainActivity.class).putExtra("offers", "offers"));
                    break;
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
                case R.id.profile:
                    startActivity(new Intent(this, UserActivity.class));
                    break;
                default:
                    return true;
            }
            return true;

        });

    }

    public void searchViewContent(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Database/Ads/");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adsArrayList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Ads ads = dataSnapshot1.getValue(Ads.class);
                    adsArrayList.add(ads);
                }
                adsRecyclerViewAdapter = new AdsRecyclerViewAdapter(adsArrayList, MainActivity.this);
                recyclerView.setAdapter(adsRecyclerViewAdapter);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



    @Override
    public void selectedUser(Ads ads) {
        startActivity(new Intent(MainActivity.this, SelectedUserActivity.class).putExtra("data", adsArrayList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            ImageView closeBtn = (ImageView) searchField.get(searchView);
            assert closeBtn != null;
            closeBtn.setImageResource(R.drawable.ic_close_black_24dp);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adsRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_view) {
            return true;
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

}