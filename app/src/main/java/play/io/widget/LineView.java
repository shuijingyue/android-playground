package play.io.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineView extends View {
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.GREEN, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        Path path = new Path();
        paint.setColor(0xFFFF0000);
        path.moveTo(0, 0);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);
    }
}
