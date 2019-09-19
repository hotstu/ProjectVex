package github.hotstu.vex;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/19/19
 */
public class VexLayout extends FrameLayout {
    public VexLayout(@NonNull Context context) {
        super(context);
    }

    public VexLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VexLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
