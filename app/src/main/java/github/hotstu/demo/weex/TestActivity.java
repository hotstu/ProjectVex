package github.hotstu.demo.weex;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import github.hotstu.vex.VexFragment;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/19/19
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, VexFragment.newInstance(Uri.parse("file://assets/index.js")))
                .commit();
    }
}
