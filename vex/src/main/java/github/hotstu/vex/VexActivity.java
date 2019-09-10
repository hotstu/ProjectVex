package github.hotstu.vex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

import github.hotstu.naiue.util.MOStatusBarHelper;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/9/19
 */
public class VexActivity extends VexBaseActivity implements IWXRenderListener {
    private static final String TAG = "VEX";
    private WXSDKInstance mWXSDKInstance;
    private Uri mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MOStatusBarHelper.translucent(this);
        setContentView(R.layout.activity_vex);

        mUri = getIntent().getData();
        if (mUri == null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            String url = bundle.getString("url");
            if (!TextUtils.isEmpty(url)) {
                mUri = Uri.parse(url);
            }
        }

        if (mUri == null) {
            mUri = Uri.parse("file://assets/index.js");
        }

        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        final String pageName = "WXSample";
        //String bundleUrl = "http://dotwe.org/raw/dist/38e202c16bdfefbdb88a8754f975454c.bundle.wx";
        if (WXSDKEngine.isInitialized()) {
            Log.d(TAG, "init-->1");
            mWXSDKInstance.renderByUrl(pageName, mUri.toString(), null, null, WXRenderStrategy.APPEND_ASYNC);
        } else {
            Log.d(TAG, "init-->2");
            final Handler handler = new Handler();
            final long[] delayed = new long[1];
            delayed[0] = 100;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (WXSDKEngine.isInitialized()) {
                        Log.d(TAG, "init-->3:" + delayed[0]);
                        mWXSDKInstance.renderByUrl(pageName, mUri.toString(), null, null, WXRenderStrategy.APPEND_ASYNC);
                    } else {
                        delayed[0] += 100;
                        handler.postDelayed(this, 100);
                    }
                }
            }, 100);
        }
    }


    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        Log.e(TAG, "onViewCreated");
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.e(TAG, "onRenderSuccess");
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Log.e(TAG, "onRefreshSuccess");
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.e(TAG, msg + errCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mWXSDKInstance != null && mWXSDKInstance.onCreateOptionsMenu(menu)) {
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mWXSDKInstance != null && mWXSDKInstance.onBackPressed()) {
            //NOOP
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
