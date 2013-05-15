package com.example.nauval;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button mapasButton;
	private Button listadoButton;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         listadoButton=(Button)findViewById(R.id.listado_button);
         mapasButton=(Button)findViewById(R.id.mapa_button);
        listadoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent intent1 = new Intent(MainActivity.this, ListadoPuertosActivity.class);
		          startActivity(intent1);
			}
		});
        
        mapasButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		          Intent intent2 = new Intent(MainActivity.this, MapaPuertosActivity.class);
		          startActivity(intent2);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.MenuOpc1:
          Intent intent1 = new Intent(MainActivity.this, ListadoPuertosActivity.class);
          startActivity(intent1);
            return true;
        case R.id.MenuOpc2:
          Intent intent2 = new Intent(MainActivity.this, MapaPuertosActivity.class);
          startActivity(intent2);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    
    }
}
