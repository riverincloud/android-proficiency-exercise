package com.riverincloud.androidproficiencyexercise;

import android.content.Context;
import android.util.Log;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Di on 1/04/2015.
 */
public class RowAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private final String NO_IMAGE_ICON =
            "http://img2.wikia.nocookie.net/__cb20130511180903/legendmarielu/images/b/b4/No_image_available.jpg";

    private List<Row> rowList;
    private Context context;

    // Get the ImageLoader through my singleton class.
    ImageLoader mImageLoader = MySingleton.getMySingleton(context).getImageLoader();

    public RowAdapter(Context context, List<Row> rowList) {
        this.rowList = rowList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return rowList.size();
    }

    @Override
    public Object getItem(int i) {
        return rowList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_row, null);
        }

        TextView mTitleView = (TextView) view.findViewById(R.id.row_title);
        TextView mDescriptionView = (TextView) view.findViewById(R.id.row_description);
        // Get the NetworkImageView that will display the image.
        NetworkImageView mNetworkImageView = (NetworkImageView) view.findViewById(R.id.row_image);

        if (rowList.size() != 0) {
            Row row = rowList.get(i);
            Log.d(TAG, "***** To set row " + i + ": " + row.toString());

            if(row.getTitle() != "null") {
                mTitleView.setText(row.getTitle());
            } else {
                mTitleView.setText("");
            }
            if(row.getDescription() != "null") {
                mDescriptionView.setText(row.getDescription());
            } else {
                mDescriptionView.setText("");
            }
            if (row.getImageHref() != "null") {
                Log.d(TAG, "***** row " + i + " has image: " + row.getImageHref());
                mNetworkImageView.setDefaultImageResId(R.drawable.image_loading);
                mNetworkImageView.setErrorImageResId(R.drawable.image_error);
                mNetworkImageView.setImageUrl(row.getImageHref(), mImageLoader);
                Log.d(TAG, "***** row " + i + " image is set");
            } else {
                Log.d(TAG, "***** row \" + i + \": No image!!");
                mNetworkImageView.setImageUrl(NO_IMAGE_ICON, mImageLoader);
            }
        }

        return view;
    }

}
