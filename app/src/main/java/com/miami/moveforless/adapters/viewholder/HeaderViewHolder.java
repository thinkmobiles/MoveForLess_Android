package com.miami.moveforless.adapters.viewholder;

import android.view.View;

import com.miami.moveforless.adapters.models.ExampleModel;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class HeaderViewHolder extends AbstractViewHolder {

//    private final TextView tvText;

    public HeaderViewHolder(View itemView) {
        super(itemView);
//        tvText = (TextView) itemView.findViewById(R.id.tvText);
    }

    @Override
    public void bind(ExampleModel model) {
//        tvText.setText(model.mText + " @@@@@");
    }
}