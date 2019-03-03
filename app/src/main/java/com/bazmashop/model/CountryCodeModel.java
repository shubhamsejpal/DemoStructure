
package com.bazmashop.model;

import com.google.gson.annotations.SerializedName;

public class CountryCodeModel {

    @SerializedName("list")
    private java.util.ArrayList<CountryCode> mCountryCode;

    public java.util.ArrayList<CountryCode> getList() {
        return mCountryCode;
    }

    public void setList(java.util.ArrayList<CountryCode> countryCode) {
        mCountryCode = countryCode;
    }

}
