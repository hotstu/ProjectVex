package github.hotstu.vex;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.appfram.navigator.INavigator;
import com.taobao.weex.common.Constants;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/9/19
 */
public class VexNavigator implements INavigator {

    public VexNavigator() {
    }

    @Override
    public boolean push(Activity activity, String param) {
        Uri url = parse(param);
        if (url != null) {
            String scheme = url.getScheme();
            if (TextUtils.isEmpty(scheme)) {
                Uri.Builder builder = url.buildUpon();
                builder.scheme(Constants.Scheme.HTTP);
                url = builder.build();
            }
            Intent i = new Intent(activity, VexActivity.class);
            i.putExtra("url", url.toString());
            activity.startActivity(i);
            return true;

        }
        //we only handle  schema(vex://) others are delegate to others
        return false;
    }

    @Override
    public boolean pop(Activity activity, String param) {
        activity.finish();
        return true;
    }

    private Uri parse(String param) {
        String url = null;
        try {
            JSONObject jsonObject = JSON.parseObject(param);
            url = jsonObject.getString("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(url)) {
            Uri rawUri = Uri.parse(url);
            String scheme = rawUri.getScheme();
            if ("vex".equalsIgnoreCase(scheme)) {
                return rawUri;
            }
        }
        return null;
    }
}
