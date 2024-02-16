package com.goldentech.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.goldentech.R;
import com.shockwave.pdfium.PdfDocument;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PDFViewerFragment extends Fragment
        implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    private int pageNumber = 0;

    private String pdfFileName;
    private PDFView pdfView;
    private View view;
    private static final String TAG = "PDFViewerFragment";

    public PDFViewerFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdfviewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initView();
    }

    private void initView() {
        pdfView = view.findViewById(R.id.pdfView);
        String url = getArguments().getString("URL");
        if (url != null)
            new getPDFWithoutDownLoadUsingInputStream().execute(url.replace(" ", "%20"));
    }

    private class getPDFWithoutDownLoadUsingInputStream extends AsyncTask<String, Void, InputStream> {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait ...");
            progressDialog.show();
        }

        protected InputStream doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: ");

            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (Exception e) {
                return null;

            }
            return inputStream;

        }

        protected void onPostExecute(InputStream inputStream) {

            pdfView.fromStream(inputStream)
                    .defaultPage(pageNumber)
                    .onPageChange(PDFViewerFragment.this)
                    .enableAnnotationRendering(true)
                    .onLoad(PDFViewerFragment.this)
                    .scrollHandle(new DefaultScrollHandle(getActivity()))
                    .spacing(10) // in dp
                    .onPageError(PDFViewerFragment.this)
                    .load();
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        getActivity().setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    public void onPageError(int page, Throwable t) {

    }

}
