package play.io;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import play.io.adapter.RouteAdapter;
import play.io.repository.entities.Route;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.root);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RouteAdapter adapter = new RouteAdapter();
        adapter.setRoutes(createRoutes());
        recyclerView.setAdapter(adapter);
    }

    private List<Route> createRoutes() {
        List<Route> routes = new ArrayList<>();
        Bundle webviewActivityExtra = new Bundle();
        webviewActivityExtra.putString(WebviewActivity.EXTRA_KEY_URL, "https://www.laohu8.com/news/2104812125?lang=");
        routes.add(new Route(WebviewActivity.class, webviewActivityExtra));
        routes.add(new Route(MediaPlayerActivity.class));
        return routes;
    }
}