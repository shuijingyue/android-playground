package play.io.repository.entities;

import android.app.Activity;
import android.os.Bundle;

public class Route {
    public String text;
    public Class<? extends Activity> clazz;
    public Bundle extra;
    public Route() {}
    public Route(Class<? extends Activity> clazz) {
        this.clazz = clazz;
    }

    public Route(Class<? extends Activity> clazz, Bundle extra) {
        this.clazz = clazz;
        this.extra = extra;
    }
}
