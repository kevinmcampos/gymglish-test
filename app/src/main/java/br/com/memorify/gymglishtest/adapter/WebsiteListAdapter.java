package br.com.memorify.gymglishtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.memorify.gymglishtest.R;
import br.com.memorify.gymglishtest.model.Website;

public class WebsiteListAdapter extends RecyclerView.Adapter<WebsiteListAdapter.ViewHolder>  {

    private Context context;
    private List<Website> websites;
    private WebsiteClickListener websiteClickListener;

    public WebsiteListAdapter(Context context, List<Website> websites) {
        this.context = context;
        this.websites = websites;
    }

    public void setWebsiteClickListener(WebsiteClickListener websiteClickListener) {
        this.websiteClickListener = websiteClickListener;
    }

    @Override
    public WebsiteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_website, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WebsiteListAdapter.ViewHolder holder, int position) {
        final Website website = websites.get(position);
        holder.mTitle.setText(website.getTitle());

        Picasso.with(context)
                .load(website.getImageUrl())
                .into(holder.backgroundImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (websiteClickListener != null) {
                    websiteClickListener.onWebsiteClick(view, website);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return websites.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface WebsiteClickListener {
        void onWebsiteClick(View view, Website website);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView mTitle;
        public ImageView backgroundImage;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            backgroundImage = (ImageView) itemView.findViewById(R.id.background_image);
        }
    }
}
