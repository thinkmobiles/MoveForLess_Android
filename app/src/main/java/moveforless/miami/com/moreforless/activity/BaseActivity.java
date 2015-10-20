package moveforless.miami.com.moreforless.activity;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by SetKrul on 14.07.2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @SuppressWarnings("unchecked")
    public final <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}
