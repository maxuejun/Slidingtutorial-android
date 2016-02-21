package com.cleveroad.slidingtutorial.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		if (savedInstanceState == null) {
			replaceTutorialFragment();
		}
	}

	private void init(){
		findViewById(R.id.bRetry).setOnClickListener(this);
		findViewById(R.id.bAsyncTask).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.bRetry:
				replaceTutorialFragment();
				break;
			case R.id.bAsyncTask:
                Intent intent = new Intent(MainActivity.this,AsyncTastActivity.class);
                startActivity(intent);
		}
	}

	public void replaceTutorialFragment() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.container, new CustomPresentationPagerFragment());
		fragmentTransaction.commit();
	}
}
