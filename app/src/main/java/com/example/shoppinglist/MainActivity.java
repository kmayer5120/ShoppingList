package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    private Cart cart = new Cart();
    private ArrayList<TextView> tvItems;
    private ArrayList<TextView> tvPrices;
    private ArrayList<Spinner> spQuantities;
    private LinearLayout linearLayoutV;
    private LinearLayout linearLayoutH;
    private Button btnAdd;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutV = findViewById(R.id.linearLayoutV);
        linearLayoutH = findViewById(R.id.linearLayoutH);

        initComponents();
    }

    private void initComponents()
    {
        tvItems = new ArrayList<>();
        tvPrices = new ArrayList<>();
        spQuantities = new ArrayList<>();


        TextView tvItem = findViewById(R.id.tvItem);
        TextView tvPrice = findViewById(R.id.tvPrice);
        Spinner spQuantity = findViewById(R.id.spQuantity);

        tvItems.add(tvItem);
        tvPrices.add(tvPrice);
        spQuantities.add(spQuantity);

        btnAdd = findViewById(R.id.btnAddItem);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                int count = tvItems.size() - 1;
                try
                {
                    if (linearLayoutH != null)
                    {
                        tvItems.add(createNewItemTextView());
                        tvPrices.add(createNewPriceTextView());
                        spQuantities.add(createNewQuantitySpinner());

                        ((LinearLayout)findViewById(R.id.linearLayoutH)).addView(tvItems.get(count));
                        ((LinearLayout)findViewById(R.id.linearLayoutH)).addView(tvPrices.get(count));
                        ((LinearLayout)findViewById(R.id.linearLayoutH)).addView(spQuantities.get(count));
                    }

                }
                catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int count = tvItems.size() - 1;

                for(int i = 0; i < count; i++)
                {
                    String description = (String) tvItems.get(i).getText();
                    double price = Double.parseDouble((String) tvPrices.get(i).getText());
                    Item item = new Item(description, price);
                    cart.addItem(item);
                }

                TextView tvTotal = findViewById(R.id.tvTotal);
                tvTotal.setText(cart.toString());
            }

        });

    }

    private TextView createNewItemTextView()
    {
        final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = findViewById(R.id.tvItem);
        textView.setLayoutParams(params);
        textView.setHint("Item");
        return textView;
    }

    private TextView createNewPriceTextView()
    {
        final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = findViewById(R.id.tvPrice);
        textView.setLayoutParams(params);
        textView.setHint("$0.00");
        return textView;
    }

    private Spinner createNewQuantitySpinner()
    {
        final LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final Spinner spinner = findViewById(R.id.spQuantity);
        spinner.setLayoutParams(params);
        return spinner;
    }

}
