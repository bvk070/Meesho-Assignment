package com.meesho.meeshoassignment.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meesho.meeshoassignment.R;
import com.meesho.meeshoassignment.model.Commit;

import java.util.ArrayList;
import java.util.List;

public class CommitAdapter extends BaseAdapter {

    private List<Commit> commits = new ArrayList<>();

    @Override
    public int getCount() {
        return commits.size();
    }

    @Override
    public Commit getItem(int position) {
        if (position < 0 || position >= commits.size()) {
            return null;
        } else {
            return commits.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.setCommit(getItem(position));
        return view;
    }

    public void setCommits(@Nullable List<Commit> repos) {
        if (repos == null) {
            return;
        }
        commits.clear();
        commits.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.commit_row, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class ViewHolder {

        private TextView tvTitle;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }

        public void setCommit(Commit commit) {
            tvTitle.setText(commit.getCommit().getMessage());
        }
    }

}
