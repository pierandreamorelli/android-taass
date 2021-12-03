package com.example.project.Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.project.Activity.LoginActivity;
import com.example.project.Interface.ChangeNumberItemsListener;
import com.example.project.Model.ProductOrder;
import com.example.project.Model.ProductOrders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertProductOrder(ProductOrder item) {
        ArrayList<ProductOrder> listproduct = getListCard();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listproduct.size(); i++) {
            if (listproduct.get(i).getProduct().getId().equals(item.getProduct().getId())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listproduct.get(n).setNum(item.getNum());
        } else {
            listproduct.add(item);
        }

        tinyDB.putListObject("CardList", listproduct);
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<ProductOrder> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    public void plusNumberFood(ArrayList<ProductOrder> listproduct, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listproduct.get(position).setNum(listproduct.get(position).getNum() + 1);
        tinyDB.putListObject("CardList", listproduct);
        changeNumberItemsListener.changed();
    }

    public void MinusNumerFood(ArrayList<ProductOrder> listproduct, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listproduct.get(position).getNum() == 1) {
            listproduct.remove(position);
        } else {
            listproduct.get(position).setNum(listproduct.get(position).getNum()-1);
        }
        tinyDB.putListObject("CardList", listproduct);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<ProductOrder> listp = getListCard();
        double fee = 0;
        for (int i = 0; i < listp.size(); i++) {
            fee = fee + (listp.get(i).getProduct().getPrice() * listp.get(i).getNum());
        }
        return fee;
    }

    public void payment() throws IOException {
        if(!getListCard().isEmpty()){new PostDataTask().execute();}
        else {System.out.println("Errore");}
    }
    private class PostDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ProductOrders po = new ProductOrders(getListCard());
            Gson gson = new Gson();
            HttpHandler httpHandler = new HttpHandler();
            httpHandler.sendHTTPData("http://192.168.1.107:8080/api/orders", po);
            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            //parse the JSON data and then display
        }

    }

}
