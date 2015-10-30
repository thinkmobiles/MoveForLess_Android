package com.miami.moveforless.adapters.models;

import java.util.List;

/**
 * Created by SetKrul on 30.10.2015.
 */
public class ExampleModel  {

    public final String mText;
    public Boolean mExpand;
    public final List<ExampleModel> child;

    public ExampleModel(String _text, Boolean _expand, List<ExampleModel> _child) {
        this.mText = _text;
        this.mExpand = _expand;
        this.child = _child;
    }

    public int getExpandableItams() {
        if (child == null) return 0;
        for (int i = 0; i < child.size(); i++) {
            if (child.get(i).mExpand){
                return i;
            }
        }
        return 0;
    }
}