package com.bazmashop.helper.custom;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bazmashop.R;
import com.bazmashop.databinding.RowCountryCodeBinding;
import com.bazmashop.main.base.BaseAct;
import com.bazmashop.model.CountryCode;
import com.bazmashop.model.CountryCodeModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryCodeActivity extends BaseAct {

    public static final String INTENT_EXTRA_COUNTRY_CODE = "EXTRA_COUNTRY_CODE";
    public static final String INTENT_EXTRA_COUNTRY_NAME = "EXTRA_COUNTRY_NAME";
    private static ArrayList<CountryCode> countryCodes;
    TextView tvTitle;
    RecyclerView rvCodes;
    ImageView ivLeft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);

        getSupportActionBar().hide();

        if (countryCodes == null)
            countryCodes = getCountryCodes();

        DividerItemDecoration dividerDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        dividerDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        rvCodes.addItemDecoration(dividerDecoration);
        rvCodes.setAdapter(new CountryCodeAdapter(this));

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private ArrayList<CountryCode> getCountryCodes() {
        String json = null;
        try {
            InputStream is = getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (json != null) {
            CountryCodeModel countryModel = new Gson().fromJson(json, CountryCodeModel.class);
            if (countryModel != null) {
                Collections.sort(countryModel.getList(), new Comparator<CountryCode>() {
                    @Override
                    public int compare(CountryCode countryCode, CountryCode t1) {
                        return countryCode.getName().compareTo(t1.getName());
                    }
                });
                return countryModel.getList();
            }
        }
        return new ArrayList<>();
    }

    @NotNull
    @Override
    protected String getClassName() {
        return this.getLocalClassName();
    }

    private class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.ViewHolder> {
        private final LayoutInflater inflater;
        private Context context;

        public CountryCodeAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RowCountryCodeBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.row_country_code, parent, false);
            return new ViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mBinding.setCountry(countryCodes.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(INTENT_EXTRA_COUNTRY_CODE, countryCodes.get(position).getDialCode());
                    intent.putExtra(INTENT_EXTRA_COUNTRY_NAME, countryCodes.get(position).getName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return countryCodes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final RowCountryCodeBinding mBinding;

            public ViewHolder(RowCountryCodeBinding mBinding) {
                super(mBinding.getRoot());
                this.mBinding = mBinding;
            }
        }
    }
}
