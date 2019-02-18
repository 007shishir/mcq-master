package com.mcqmaster.saif_win10.mcqmaster;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicy extends Fragment {


    public PrivacyPolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        String url = "file:///android_asset/privacy_policy.html";
        WebView v = (WebView) view.findViewById(R.id.webV_privacy);
        v.getSettings().setJavaScriptEnabled(true);
        v.getSettings().setBuiltInZoomControls(true);

        v.loadUrl(url);
        v.setWebViewClient(new MyBrowser());
        return view;
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView v, String url) {
            v.loadUrl(url);
            return true;
        }
    }


}
