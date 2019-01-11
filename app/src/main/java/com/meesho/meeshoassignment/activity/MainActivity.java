package com.meesho.meeshoassignment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.meesho.meeshoassignment.R;
import com.meesho.meeshoassignment.adapter.PullRequestAdapter;
import com.meesho.meeshoassignment.model.PullRequest;
import com.meesho.meeshoassignment.retrofit.RetrofitClient;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private  PullRequestAdapter mPullRequestAdapter = new PullRequestAdapter();
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.lvPullRequests);
        listView.setAdapter(mPullRequestAdapter);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etRepo = (EditText) findViewById(R.id.etRepo);
        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String repo = etRepo.getText().toString();
                if (isValid(username,repo)) {
                    getPullRequests(username,repo);
                }
            }
        });

    }

    @Override protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    public void getPullRequests(final String username, final String repo) {

        subscription = RetrofitClient.getInstance()
                .getPullRequests(username,repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PullRequest>>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override public void onNext(List<PullRequest> pullRequests) {
                        Log.d(TAG, "In onNext() "+pullRequests.size());
                        mPullRequestAdapter.setPullRequests(username,repo,pullRequests,MainActivity.this);
                    }
                });

    }

    private boolean isValid(String username,String repo){

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(),"please enter username",Toast.LENGTH_SHORT).show();
            return  false;
        }

        if (TextUtils.isEmpty(repo)) {
            Toast.makeText(getApplicationContext(),"please enter repo name",Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }

}
