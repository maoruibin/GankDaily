package com.gudong.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.gudong.gankio.R;

import static com.gudong.gankio.R.string.website;

public class AboutActivity extends MaterialAboutActivity {

    public static void gotoAboutActivity(Context context){
        context.startActivity(new Intent(context,AboutActivity.class));
    }
    @Override protected MaterialAboutList getMaterialAboutList(Context context) {
        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();
        try {
            appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(context, ContextCompat.getDrawable(context, R.drawable.ic_issues),
                    getString(R.string.version), false));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        appCardBuilder.addItem(ConvenienceBuilder.createRateActionItem(context, ContextCompat.getDrawable(context, R.drawable.ic_star_filled),
                getString(R.string.rate_app), null));

        appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.report_issue)
                .subText(R.string.report_issue_here)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_bug))
                .setOnClickListener(ConvenienceBuilder.createWebsiteOnClickAction(AboutActivity.this, Uri.parse("https://github.com/maoruibin/GankDaily/issues")))
                .build());

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title(R.string.author);
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("咕咚")
                .subText("maoruibin")
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                .setOnClickListener(ConvenienceBuilder.createWebsiteOnClickAction(AboutActivity.this, Uri.parse("http://gudong.name/")))
                .build());
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.fork_github)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_github))
                .setOnClickListener(ConvenienceBuilder.createWebsiteOnClickAction(AboutActivity.this, Uri.parse("https://github.com/maoruibin/GankDaily")))
                .build());

        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(context, ContextCompat.getDrawable(context, R.drawable.ic_email),
                getString(R.string.send_email), true, getString(R.string.email_address), getString(R.string.question_concerning_fasthub)));

        MaterialAboutCard.Builder logoAuthor = new MaterialAboutCard.Builder();
        logoAuthor.title(getString(R.string.contact));

        logoAuthor.addItem(new MaterialAboutActionItem.Builder()
                .text(website)
                .subText(R.string.website_sub)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_brower))
                .setOnClickListener(ConvenienceBuilder.createWebsiteOnClickAction(AboutActivity.this, Uri.parse("http://gudong.name/")))
                .build());
        logoAuthor.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.weibo)
                .subText(R.string.weibo_nick)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_profile))
                .setOnClickListener(ConvenienceBuilder.createWebsiteOnClickAction(AboutActivity.this, Uri.parse("http://weibo.com/maoruibin")))
                .build());
        logoAuthor.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.github)
                .subText(R.string.github_id)
                .icon(ContextCompat.getDrawable(context, R.drawable.ic_github))
                .setOnClickListener(ConvenienceBuilder.createWebsiteOnClickAction(AboutActivity.this, Uri.parse("https://github.com/maoruibin")))
                .build());

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), logoAuthor.build());
    }

    @Override protected CharSequence getActivityTitle() {
        return getString(R.string.app_name);
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return false;//override
    }

}