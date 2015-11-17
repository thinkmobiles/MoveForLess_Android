package com.miami.moveforless.adapters.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**

 * Created by klim on 17.11.15.
 */
public class PhotoAdapter extends ArrayAdapter<Bitmap> {
    private Context mContext;

    public PhotoAdapter(Context context) {
        super(context, 0);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.photo_placeholder, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.ivPhoto.setImageBitmap(getItem(position));
        RxUtils.click(holder.btnDelete, o -> {
            remove(getItem(position));
            notifyDataSetChanged();
        });
        return view;
    }

    static class ViewHolder{
        @Bind(R.id.ivImagePlaceholder_PP)
        ImageView ivPhoto;
        @Bind(R.id.btnDelete_PP)                ImageView btnDelete;

        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
