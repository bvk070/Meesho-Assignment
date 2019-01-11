package com.meesho.meeshoassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meesho.meeshoassignment.R;
import com.meesho.meeshoassignment.activity.CommitsActivity;
import com.meesho.meeshoassignment.model.PullRequest;

import java.util.ArrayList;
import java.util.List;

public class PullRequestAdapter extends BaseAdapter {

    private List<PullRequest> pullRequests = new ArrayList<>();
    private Context mContext;
    private  String username,repo;

    @Override public int getCount() {
        return pullRequests.size();
    }

    @Override public PullRequest getItem(int position) {
        if (position < 0 || position >= pullRequests.size()) {
            return null;
        } else {
            return pullRequests.get(position);
        }
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.setPullRequest(getItem(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PullRequest pullRequest = getItem(position);
                Intent mIntent = new Intent(mContext, CommitsActivity.class);
                mIntent.putExtra("username",username);
                mIntent.putExtra("repo",repo);
                mIntent.putExtra("number",""+pullRequest.getNumber());
                mContext.startActivity(mIntent);
            }
        });

        return view;
    }

    public void setPullRequests(String username,String repo,@Nullable List<PullRequest> repos,Context mContext) {
        if (repos == null) {
            return;
        }
        this.mContext=mContext;
        this.username=username;
        this.repo=repo;
        pullRequests.clear();
        pullRequests.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.pull_request_row, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class ViewHolder {

        private TextView tvTitle;
        private TextView tvDesc;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        }

        public void setPullRequest(PullRequest pullRequest) {
            tvTitle.setText(pullRequest.getTitle());
            tvDesc.setText("#"+pullRequest.getNumber()+" opened by "+pullRequest.getUser().getLogin());
        }
    }

}
