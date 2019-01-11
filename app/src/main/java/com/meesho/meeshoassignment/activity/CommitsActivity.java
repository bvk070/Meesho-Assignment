package com.meesho.meeshoassignment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.meesho.meeshoassignment.R;
import com.meesho.meeshoassignment.adapter.CommitAdapter;
import com.meesho.meeshoassignment.model.Commit;
import com.meesho.meeshoassignment.retrofit.RetrofitClient;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class CommitsActivity extends AppCompatActivity {

    private static final String TAG = CommitsActivity.class.getSimpleName();
    private CommitAdapter mCommitAdapter = new CommitAdapter();
    private Subscription subscription;
    private String username,repo,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            username=extras.getString("username");
            repo=extras.getString("repo");
            number=extras.getString("number");
        }

        final ListView listView = (ListView) findViewById(R.id.lvCommits);
        listView.setAdapter(mCommitAdapter);
        listView.setDivider(null);
        getCommits(username,repo,number);

    }

    @Override protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    public void getCommits(String username,String repo,String number) {

        subscription = RetrofitClient.getInstance()
                .getCommits(username,repo,number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Commit>>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override public void onNext(List<Commit> commits) {
                        Log.d(TAG, "In onNext() "+commits.size());
                        mCommitAdapter.setCommits(commits);
                    }
                });

    }


}
