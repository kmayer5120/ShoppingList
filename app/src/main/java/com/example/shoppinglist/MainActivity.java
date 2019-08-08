package com.example.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity
{
    private Cart cart;
    private LinearLayout hboxItem;
    private LinearLayout hboxButtons;
    private CheckBox cbxIsTaxed;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnEdit;
    private EditText etDescription;
    private EditText etPrice;
    private TextView tvTotal;
    private Spinner spQuantity;
    private ListView lvItems;
    private static final String TAG = "MainActivity";
    private CartListAdapter adapter;
    private int selectedIndex;
    private boolean isTaxed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Started");
        lvItems = findViewById(R.id.lvItems);

        cart = new Cart();
        //just some default values to start off with during testing
        cart.addItem(new Item("apples", 1.99, 3, false));
        cart.addItem(new Item("bread", 1.89, 2, false));
        cart.addItem(new Item("ground beef", 11.99, 1, true));
        adapter = new CartListAdapter(this, R.layout.adapter_view_layout, cart.getItems());
        lvItems.setAdapter(adapter);
        lvItems.setClickable(true);
        adapter.setNotifyOnChange(true);
        initComponents();
    }


    private void initComponents()
    {

        btnAdd = findViewById(R.id.btnAddItem);
        btnRemove = findViewById(R.id.btnRemoveItem);
        btnEdit = findViewById(R.id.btnEditItem);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        spQuantity = findViewById(R.id.spQuantity);
        cbxIsTaxed = findViewById(R.id.cbxIsTaxed);
        hboxItem = findViewById(R.id.hboxItem);
        hboxButtons = findViewById(R.id.hboxButtons);
        tvTotal = findViewById(R.id.tvTotal);

        btnRemove.setVisibility(View.GONE);
        lvItems.setLongClickable(true);

        tvTotal.setText(cart.toString());

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //adds item to cart and calculates total
                getItemFromControls();
                tvTotal.setText(cart.toString());
                adapter.notifyDataSetChanged();
                clearControls();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //remove selected item at index
                cart.removeItemAtIndex(selectedIndex);
                //set text on form for total price of all items
                tvTotal.setText(cart.toString());
                adapter.notifyDataSetChanged();


                //handle visibility of corresponding buttons
                btnRemove.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                btnAdd.setVisibility(View.VISIBLE);

                clearControls();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //DEBUG
                //Toast.makeText(adapter.getContext(), item.getDescription(), Toast.LENGTH_LONG).show();
                //get edited item from the controls
                String description = String.valueOf(etDescription.getText());
                double price = Double.parseDouble(String.valueOf(etPrice.getText()));
                int quantity = Integer.parseInt(String.valueOf(spQuantity.getSelectedItemPosition() + 1));
                isTaxed = cbxIsTaxed.isChecked();

                //set info from the form into new Item which will be inserted into list
                Item editedItem = new Item(description, price, quantity, isTaxed);

                //edit item from item list at index we want to change
                cart.editItemAtIndex(editedItem, selectedIndex);
                //set text on form for total price of all items
                adapter.notifyDataSetChanged();
                //refresh total calculated price of items
                tvTotal.setText(cart.toString());



                clearControls();
                btnRemove.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3)
            {
                btnAdd.setVisibility(View.GONE);
                btnRemove.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
                //set fields of form with item fields from the cart object's list
                etDescription.setText(cart.getItems().get(index).getDescription());
                etPrice.setText(String.valueOf(cart.getItems().get(index).getPrice()));
                spQuantity.setSelection(cart.getItems().get(index).getQuantity() - 1);
                cbxIsTaxed.setChecked(isTaxed);

                selectedIndex = index;
            }
        });

        cbxIsTaxed.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                isTaxed = b;
            }
        });
    }



    private void getItemFromControls()
    {
        String description;
        double price;
        int quantity;

        try
        {
            description = String.valueOf(String.valueOf(etDescription.getText()));
            price = Double.parseDouble(String.valueOf(etPrice.getText()));
            quantity = Integer.parseInt(String.valueOf(spQuantity.getSelectedItem()));
            isTaxed = cbxIsTaxed.isChecked();
            //add a new item to cart
            cart.addItem(new Item(description, price, quantity, isTaxed));

        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this, "Enter number for for price before adding to list.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Enter values for item before adding to list.", Toast.LENGTH_LONG).show();
        }


    }
    private void clearControls()
    {
        etPrice.setText("");
        etDescription.setText("");
        cbxIsTaxed.setChecked(false);
        spQuantity.setSelection(0);
    }

}
