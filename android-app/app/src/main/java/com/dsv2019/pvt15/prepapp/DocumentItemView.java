package com.dsv2019.pvt15.prepapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DocumentItemView extends LinearLayout
{
    private TextView documentNameView;
    private String documentName;

    public DocumentItemView(Context context)
    {
        super(context);
    }

    public DocumentItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DocumentItemView(Context context, String fileName)
    {
        super(context);

        inflate(context, R.layout.important_document_view, this);
        documentName = fileName;
        documentNameView = findViewById(R.id.documentFileName);
        documentNameView.append(documentName);

    }

    public String getDocumentName()
    {
        return documentName;
    }
}
