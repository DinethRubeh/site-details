package com.example.android.sitedetails.models;

/**
 * Created by Venura Pussella on 26/12/2017.
 */

public class SiteDetailsModel { //this model is used to store the retrieved siteName and siteId.

    private String siteId;
    private String siteName;

    public SiteDetailsModel(String siteId, String siteName) {
        this.siteId = siteId;
        this.siteName = siteName;
    }

    public SiteDetailsModel() {
    }



    public String getSiteId() {
        return siteId;
    }


    public String getSiteName() {
        return siteName;
    }
}
