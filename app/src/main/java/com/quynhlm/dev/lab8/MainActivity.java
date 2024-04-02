package com.quynhlm.dev.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.quynhlm.dev.lab8.Adapter.Adapter_Item_District_Select_GHN;
import com.quynhlm.dev.lab8.Adapter.Adapter_Item_Province_Select_GHN;
import com.quynhlm.dev.lab8.Adapter.Adapter_Item_Ward_Select_GHN;
import com.quynhlm.dev.lab8.Model.District;
import com.quynhlm.dev.lab8.Model.DistrictRequest;
import com.quynhlm.dev.lab8.Model.Province;
import com.quynhlm.dev.lab8.Model.ResponseGHN;
import com.quynhlm.dev.lab8.Model.Ward;
import com.quynhlm.dev.lab8.Service.GHNRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GHNRequest ghnRequest;
    Spinner sp_province,sp_district,sp_ward;
    Adapter_Item_Province_Select_GHN adapterItemProvinceSelectGhn;
    Adapter_Item_District_Select_GHN adapterItemDistrictSelectGhn;
    Adapter_Item_Ward_Select_GHN adapterItemWardSelectGhn;
    ArrayList<District> list_District = new ArrayList<>();
    ArrayList<Province> list_Province = new ArrayList<>();
    EditText ed_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp_province = findViewById(R.id.sp_province);
        sp_district = findViewById(R.id.sp_district);
        sp_ward = findViewById(R.id.sp_ward);
        ed_search = findViewById(R.id.ed_search);
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String search = ed_search.getText().toString().trim();
//                for (Province pro:list_Province) {
//                    if(pro.getProvinceName().contains(search)) {
//                        sp_province.setSelection(pro.getProvinceID());
//                    }
//                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ghnRequest = new GHNRequest();

        Call<ResponseGHN<ArrayList<Province>>> call = ghnRequest.getApiService().getListProvince();
        call.enqueue(new Callback<ResponseGHN<ArrayList<Province>>>() {
            @Override
            public void onResponse(Call<ResponseGHN<ArrayList<Province>>> call, Response<ResponseGHN<ArrayList<Province>>> response) {
                if(response.isSuccessful()){
                    if(response.body().getCode() == 200){
                        list_Province = new ArrayList<>(response.body().getData());
                        Log.e("TAG", "onResponse: "+"Call thành công");
                        adapterItemProvinceSelectGhn = new Adapter_Item_Province_Select_GHN(MainActivity.this,list_Province);
                        sp_province.setAdapter(adapterItemProvinceSelectGhn);
                        Toast.makeText(MainActivity.this, "Call thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e("TAG", "onResponse: "+"Call thất bại");
                    }
                }else{
                    Log.e("TAG", "onResponse: "+"Call thất bại");
                    Log.e("TAG", "onResponse: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseGHN<ArrayList<Province>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lấy dữ liệu bị lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        sp_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int provinceID = ((Province) parent.getAdapter().getItem(position)).getProvinceID();
                Toast.makeText(MainActivity.this, "Province_ID" + provinceID, Toast.LENGTH_SHORT).show();
                DistrictRequest districtRequest = new DistrictRequest(provinceID);
                ghnRequest.getApiService().getListDistrict(districtRequest).enqueue(new Callback<ResponseGHN<ArrayList<District>>>() {
                    @Override
                    public void onResponse(Call<ResponseGHN<ArrayList<District>>> call, Response<ResponseGHN<ArrayList<District>>> response) {
                        if(response.isSuccessful()){
                            list_District = response.body().getData();
                            adapterItemDistrictSelectGhn = new Adapter_Item_District_Select_GHN(MainActivity.this,list_District);
                            sp_district.setAdapter(adapterItemDistrictSelectGhn);
                        }else{
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGHN<ArrayList<District>>> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+t.getMessage());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                District district = list_District.get(position);
                ghnRequest.getApiService().getListWard(district.getDistrictID()).enqueue(new Callback<ResponseGHN<ArrayList<Ward>>>() {
                    @Override
                    public void onResponse(Call<ResponseGHN<ArrayList<Ward>>> call, Response<ResponseGHN<ArrayList<Ward>>> response) {
                        if(response.isSuccessful()){
                            ArrayList<Ward> list = new ArrayList<>(response.body().getData());
                            adapterItemWardSelectGhn = new Adapter_Item_Ward_Select_GHN(MainActivity.this,list);
                            sp_ward.setAdapter(adapterItemWardSelectGhn);
                        }else{
                            Toast.makeText(MainActivity.this, "Xẩy ra lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGHN<ArrayList<Ward>>> call, Throwable t) {
                        Log.e("TAG", "onFailure: "+t.getMessage());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}