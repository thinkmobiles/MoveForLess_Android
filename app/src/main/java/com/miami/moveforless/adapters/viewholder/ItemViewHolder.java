package com.miami.moveforless.adapters.viewholder;

import android.view.View;
import android.widget.Button;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.models.ExampleModel;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class ItemViewHolder extends AbstractViewHolder {
//    private final TextView tvText;
    @Bind(R.id.btn_try_again)
    Button sdgefdh;
    @BindString(R.string.action_search)
    String sfgdfg;

    public ItemViewHolder(View itemView) {
        super(itemView);
//        tvText = (TextView) itemView.findViewById(R.id.tvText);
    }

    @Override
    public void bind(ExampleModel model) {
//        tvText.setText(model.mText);
    }
}
