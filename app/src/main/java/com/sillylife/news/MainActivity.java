package com.sillylife.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.sillylife.news.Adapters.RssFeedListAdapter;
import com.sillylife.news.Database.Modal.RssFeedModel;
import com.sillylife.news.Database.Remote.DBResponse;
import com.sillylife.news.Utils.FetchFeedTask;
import com.sillylife.opinionpoll.NewSettings.Interface.DbCallbacks;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private final static String TAG = "MainActivity";
	private FirebaseAuth mAuth;
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();

		mAuth = FirebaseAuth.getInstance();
		loginAnonymously();
		new DBResponse(new DbCallbacks() {
			@Override
			public void onSuccess(@NotNull DataSnapshot dataSnapshot) {

			}

			@Override
			public void onCancelled(@NotNull DatabaseError databaseError) {

			}
		}).getEnglishHeadlineLinks();
	}

	private void initialize() {
		recyclerView = findViewById(R.id.recycleview);
		recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//		RssFeedListAdapter feedListAdapter = new RssFeedListAdapter(rssFeedLists("http://feeds.feedburner.com/ndtvkhabar-latest", 1));
//		recyclerView.setAdapter(feedListAdapter);
		setAdapter();
	}

	void loginAnonymously() {
		mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()) {
					// Sign in success, update UI with the signed-in user's information
					Log.d(TAG, "signInAnonymously:success");
					FirebaseUser user = mAuth.getCurrentUser();

					if (user != null) {
						Toast.makeText(MainActivity.this, "Authentication Success. " + user.getUid().toString(), Toast.LENGTH_SHORT).show();
					}

				} else {
					// If sign in fails, display a message to the user.
					Log.w(TAG, "signInAnonymously:failure", task.getException());
					Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//mAuth.getCurrentUser().getIdToken(true);
	}

	@Override
	public void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		FirebaseUser currentUser = mAuth.getCurrentUser();
	}

	private URL getURLfromString(String link) {
		URL url = null;
		if (!link.startsWith("http://") && !link.startsWith("https://")) {
			link = "http://" + link;
		}
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}

	private List<RssFeedModel> rssFeedLists(String link, final int interaction) {
		final List<RssFeedModel> tempList = new ArrayList<>();
		new FetchFeedTask(new FetchFeedTask.FetchFeedTaskListener() {
			@Override
			public void onPreExcute() {
				//mSwipeLayout.setRefreshing(true);
			}

			@Override
			public void onSuccess(List<RssFeedModel> rssFeedModels) {
				if (rssFeedModels != null) {
					tempList.addAll(rssFeedModels);
					//setAdapter();
				}
			}
		}).execute(getURLfromString(link));
		return tempList;
	}

	void setAdapter() {
		new FetchFeedTask(new FetchFeedTask.FetchFeedTaskListener() {
			@Override
			public void onPreExcute() {
				//mSwipeLayout.setRefreshing(true);
			}

			@Override
			public void onSuccess(List<RssFeedModel> rssFeedModels) {
				if (rssFeedModels != null) {
					recyclerView.setAdapter(new RssFeedListAdapter(rssFeedModels));
				}
			}
		}).execute(getURLfromString("http://feeds.feedburner.com/ndtvkhabar-latest"));
	}
}
