package com.example.mario.recipebook;

import android.net.Uri;

/**
 * Created by mario on 2016/12/1.
 */

public class MyProviderContract  {
    public static final String AUTHORITY="com.example.mario.recipebook.CProvider";

    public static final Uri RECIPE_URI = Uri.parse("content://"+AUTHORITY+"/recipe");

    public static final String _ID="_id";
    public static final String TITLE="title";
    public static final String INTRODUCTION="introduction";
}
