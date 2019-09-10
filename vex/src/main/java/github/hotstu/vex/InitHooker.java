package github.hotstu.vex;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;

import github.hotstu.vex.adapter.PicassoBasedDrawableLoader;
import github.hotstu.vex.adapter.PicassoImageAdapter;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/9/19
 */
public class InitHooker extends ContentProvider {
    @Override
    public boolean onCreate() {
        Application application = (Application) getContext();
        WXEnvironment.setOpenDebugLog(true);
        WXEnvironment.setApkDebugable(true);
        WXSDKEngine.addCustomOptions("appName", "WXSample");
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");

        //Fresco.initialize(application);
        InitConfig config = new InitConfig.Builder()
                //TODO 留出接口
                .setImgAdapter(new PicassoImageAdapter())
                .setDrawableLoader(new PicassoBasedDrawableLoader(application))
                //.setJSExceptionAdapter(new JSExceptionAdapter())
                //.setApmGenerater(new ApmGenerator())
                .build();


        WXSDKEngine.initialize(application, config);
        WXSDKManager.getInstance().setNavigator(new VexNavigator());
//        try {
//            WXSDKEngine.registerComponent("mycomponent",WXMask.class);
//            WXSDKEngine.registerModule("myModule", MyModule.class);
//        } catch (WXException e) {
//            e.printStackTrace();
//        }
        return false;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
