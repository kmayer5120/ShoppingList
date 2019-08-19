package com.kmayer5120.shoppinglist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

public class CartListAdapter extends ArrayAdapter<Item>
{
    private static final String TAG = "CartListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private Item item;
    private ArrayList<Item> items;

    /**
     * Default constructor for the PersonListAdapter
     *
     * @param context
     * @param resource
     * @param items
     */
    public CartListAdapter(Context context, int resource, ArrayList<Item> items)
    {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
        this.items = items;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //get the persons information
        String description = Objects.requireNonNull(getItem(position)).getDescription();
        double price = Objects.requireNonNull(getItem(position)).getPrice();
        int quantity = Objects.requireNonNull(getItem(position)).getQuantity();
        boolean isTaxed = Objects.requireNonNull(getItem(position)).isTaxed();

        //Create the item object with the information
        item = new Item(description, price, quantity, isTaxed);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();

            holder.tvDescription = convertView.findViewById(R.id.tvDescription);
            holder.tvPrice = convertView.findViewById(R.id.tvPrice);
            holder.tvQuantity = convertView.findViewById(R.id.tvQuantity);
            holder.tvIsTaxed = convertView.findViewById(R.id.tvIsTaxed);
            holder.tvDateCreated = convertView.findViewById(R.id.tvDateCreated);

            result = convertView;

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        //Animation animation = AnimationUtils.loadAnimation(mContext,
        //        (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        //result.startAnimation(animation);
        //lastPosition = position;

        holder.tvDescription.setText(item.getDescription());
        holder.tvPrice.setText(item.getFormattedTotalPrice());
        holder.tvQuantity.setText(item.getFormattedQuantity());
        holder.tvDateCreated.setText((CharSequence) item.getCreatedAt());
        if (item.isTaxed())
        {
            holder.tvIsTaxed.setText(String.format("Tax: %.2f%%", item.getTaxAmount() * 100));
        } else
        {
            holder.tvIsTaxed.setText(String.format("Tax: %.2f%%", 0.00));
        }
        notifyDataSetChanged();

        return convertView;
    }
    public ArrayList<Item> getItems()
    {
        return this.items;
    }
    /**
     * Holds variables in a View
     */
    private static class ViewHolder
    {
        TextView tvDescription;
        TextView tvPrice;
        TextView tvQuantity;
        TextView tvIsTaxed;
        TextView tvDateCreated;
    }

}
