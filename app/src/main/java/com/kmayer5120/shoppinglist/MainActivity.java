package com.kmayer5120.shoppinglist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private ItemRepository itemRepository;
    private Cart cart;
    private CheckBox cbxIsTaxed;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnEdit;
    private EditText etDescription;
    private EditText etPrice;
    private TextView tvTotal;
    private Spinner spQuantity;
    private ListView lvItems;
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
        itemRepository = new ItemRepository(getApplicationContext());


        //create new cart object to hold Item list
        cart = new Cart();
        //create new custom list adapter
        adapter = new CartListAdapter(this, R.layout.adapter_view_layout, cart.getItems());
        //set adapter to ListView
        lvItems.setAdapter(adapter);
        lvItems.setClickable(true);
        adapter.setNotifyOnChange(true);
        adapter.notifyDataSetChanged();
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
        tvTotal = findViewById(R.id.tvTotal);

        btnRemove.setVisibility(View.GONE);
        btnEdit.setVisibility(View.GONE);
        lvItems.setLongClickable(true);

        tvTotal.setText(cart.toString());

        itemRepository = new ItemRepository(getApplicationContext());
        itemRepository.getTasks().observe(MainActivity.this, new Observer<List<Item>>()
        {
            @Override
            public void onChanged(@Nullable List<Item> items)
            {
                if (items != null)
                {
                    cart.clearItems();
                    adapter.addAll(items);
                    cart.setItems((ArrayList<Item>)items);
                    tvTotal.setText(cart.toString());
                    adapter.notifyDataSetChanged();
                } else
                {
                    Toast.makeText(MainActivity.this, "Error in loadCart()", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnAdd.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //adds item to cart and calculates total
                Item item = getItemFromControls();
                if (item == null)
                {
                    Toast.makeText(getApplicationContext(), "Please enter values for item.", Toast.LENGTH_LONG).show();
                } else
                {
                    //insert Item item into db
                    int index = cart.getItems().size();
                    adapter.insert(item, index);
                    itemRepository.insertTask(item);
                    cart.setItems(adapter.getItems());
                    tvTotal.setText(cart.toString());
                    adapter.notifyDataSetChanged();
                    clearControls();
                }

            }
        });

        btnRemove.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Item item = cart.getItems().get(selectedIndex);
                adapter.remove(item);
                //remove Item from db
                itemRepository.delete(item);
                //set text on form for total price of all items
                tvTotal.setText(cart.toString());
                adapter.notifyDataSetChanged();
                cart.setItems(adapter.getItems());


                //handle visibility of corresponding buttons
                btnRemove.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                btnAdd.setVisibility(View.VISIBLE);

                clearControls();
            }
        });

        btnEdit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //get edited item from the controls
                String description = String.valueOf(etDescription.getText());
                double price = Double.parseDouble(String.valueOf(etPrice.getText().subSequence(1, etPrice.getText().length())));
                int quantity = Integer.parseInt(String.valueOf(spQuantity.getSelectedItemPosition() + 1));
                isTaxed = cbxIsTaxed.isChecked();

                //set info from the form into new Item which will be inserted into list
                Item editedItem = new Item(description, price, quantity, isTaxed);
                Item removedItem = adapter.getItems().get(selectedIndex);
                //edit item from item list at index we want to change
                adapter.remove(removedItem);
                adapter.insert(editedItem, selectedIndex);
                //update Item in db
                itemRepository.delete(removedItem);
                itemRepository.insertTask(editedItem);
                //set text on form for total price of all items
                cart.setItems(adapter.getItems());
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
                Item item = cart.getItems().get(index);
                etDescription.setText(item.getDescription());
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                etPrice.setText(formatter.format(item.getPrice()));
                spQuantity.setSelection(item.getQuantity() - 1);
                cbxIsTaxed.setChecked(item.isTaxed());
                tvTotal.setText(cart.toString());

                selectedIndex = index;
            }
        });

        cbxIsTaxed.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                //get state of checkbox
                isTaxed = b;
            }
        });

    }

    @Nullable
    private Item getItemFromControls()
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
            return new Item(description, price, quantity, isTaxed);

        } catch (NumberFormatException e)
        {
            Toast.makeText(this, "Enter number for for price before adding to list.", Toast.LENGTH_LONG).show();
        } catch (Exception e)
        {
            Toast.makeText(this, "Enter values for item before adding to list.", Toast.LENGTH_LONG).show();
        }
        return null;


    }

    private void clearControls()
    {
        etPrice.setText("");
        etDescription.setText("");
        cbxIsTaxed.setChecked(false);
        spQuantity.setSelection(0);
    }

}
