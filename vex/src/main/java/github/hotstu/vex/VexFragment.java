package github.hotstu.vex;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc 与VexActivity主要用于混合开发多页应用不同，VexFragment的使用场景为在现有的原生界面中添加动态内容
 * 注意: VexFragment目前没有绑定各种 Activity life cycle生命周期回调
 * @since 9/19/19
 */
public class VexFragment extends Fragment implements IWXRenderListener {

    private static final String TAG = "VexFragment";
    private WXSDKInstance mWXSDKInstance;
    private Uri uri;
    private VexLayout vexLayout;

    public static VexFragment newInstance(Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("uri", uri);
        VexFragment fragment = new VexFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            uri = arguments.getParcelable("uri");
        }

        if (uri == null) {
            return;
        }

        mWXSDKInstance = new WXSDKInstance(getContext());
        mWXSDKInstance.registerRenderListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vexLayout = new VexLayout(requireContext());
        return vexLayout;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String pageName = "VexFragment";
        if (WXSDKEngine.isInitialized()) {
            Log.d(TAG, "init-->1");
            mWXSDKInstance.renderByUrl(pageName, uri.toString(), null, null, WXRenderStrategy.APPEND_ASYNC);
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
                        mWXSDKInstance.renderByUrl(pageName, uri.toString(), null, null, WXRenderStrategy.APPEND_ASYNC);
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
        if (vexLayout != null) {
            vexLayout.addView(view);
        } else {
            Log.e(TAG, "vexLayout == null");
        }
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
        Log.e(TAG, "onException:" + msg + "," + errCode);
    }
}
