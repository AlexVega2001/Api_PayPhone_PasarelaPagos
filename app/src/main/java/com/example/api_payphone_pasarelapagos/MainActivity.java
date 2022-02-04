package com.example.api_payphone_pasarelapagos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.api_payphone_pasarelapagos.Adaptador.AdapterEquipos;
import com.example.api_payphone_pasarelapagos.Model.EquiposElectronicos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<EquiposElectronicos> lstEquipos;

    //Variables para ingresar datos al pago
    String numeroCelular = "0997502548";
    String idCodePais = "593";
    String cedula = "1729798882";
    double monto = 0;
    double montoConIva = 0;
    double montoSinIva = 0;
    int iva = 0;
    int idClienteTransaccion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rcvEquipos = findViewById(R.id.rcvEquiposElec);
        rcvEquipos.setLayoutManager(new LinearLayoutManager(this));

        AdapterEquipos rcvAdapter = new AdapterEquipos(this, CargarEquipos());
        rcvAdapter.OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Comprar(position);
            }
        });
        rcvEquipos.setAdapter(rcvAdapter);

    }

    public List<EquiposElectronicos> CargarEquipos() {
        lstEquipos = new ArrayList<>();

        lstEquipos.add(new EquiposElectronicos("Xiaomi Redmi 9c 64gb",
                400, 12, R.drawable.xiami));
        lstEquipos.add(new EquiposElectronicos("Samsung Galaxy S21 Ultra S21",
                850, 12, R.drawable.galaxys21));
        lstEquipos.add(new EquiposElectronicos("Audifonos Inalambrico I12",
                10, 12, R.drawable.audifonos));
        lstEquipos.add(new EquiposElectronicos("Dell Alienware M15r6 Core I5-11800h",
                655, 12, R.drawable.laptop));
        lstEquipos.add(new EquiposElectronicos("Teclado Mecanico Reddragon K530",
                85, 12, R.drawable.teclado));
        lstEquipos.add(new EquiposElectronicos("Mouse Gamer A7 Rgb 7 Botones Inalambrico",
                20, 12, R.drawable.mouse));
        lstEquipos.add(new EquiposElectronicos("Oculus Quest 2 128gb Gafas Realidad Virtual",
                450, 12, R.drawable.gafasvirtuales));

        return lstEquipos;
    }

    public void Comprar(int pos) {

        // Calculos del iva, monto, monto con iva y el idClienteTransacción generado aleatoriamente
        iva = (int) (lstEquipos.get(pos).getIva() * lstEquipos.get(pos).getPrecio());
        //Toast.makeText(this, "Iva: " + iva, Toast.LENGTH_SHORT).show();
        monto = lstEquipos.get(pos).getPrecio() * (lstEquipos.get(pos).getIva() + 100);
        //Toast.makeText(this, "Monto: " + monto, Toast.LENGTH_SHORT).show();
        montoConIva = (lstEquipos.get(pos).getPrecio() * 100);
        //Toast.makeText(this, "MontoConIva: " + montoConIva, Toast.LENGTH_SHORT).show();
        Random rn = new Random();
        idClienteTransaccion = rn.nextInt(20000);
        //Toast.makeText(this, "idClienteTrans: " + idClienteTransaccion, Toast.LENGTH_SHORT).show();

        SendPurchase();

        //Llamado a la interfaz de PayPhone
        Intent intent = new Intent("payPhone_Android.PayPhone_Android.Purchase");
        startActivity(intent);
    }

    private void SendPurchase() {
        String url = "https://pay.payphonetodoesposible.com/api/Sale";
        RequestQueue requestQue = Volley.newRequestQueue(this);
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        getIdTransaction(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getApplicationContext(), "Error Response: " + error.getMessage()
                , Toast.LENGTH_LONG).show()) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phoneNumber", numeroCelular);
                params.put("countryCode", idCodePais);
                params.put("clientUserId", cedula);
                params.put("reference", "none");
                params.put("responseUrl", "http://paystoreCZ.com/confirm.php");
                params.put("amount", "" + (int) monto + "");
                params.put("amountWithTax", "" + (int) montoConIva + "");
                params.put("amountWithoutTax", "" + (int) montoSinIva + "");
                params.put("tax", "" + iva + "");
                params.put("clientTransactionId", "" + idClienteTransaccion + "");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String authorization = "Bearer qI9lLZah0skaLES5R2mCgu1gEAbKwxtKFT0jACkB4KtMX1d4sHt" +
                        "PF6ejHZccBB2qyAHX0iWNwxmatSFT0rbhkvUliK31u2utv4rn3asW_Rn53O29qgWshlf92KNy" +
                        "7pVQDlY8ztkjhtB_NMxAw175aD7-qoLwMQrLVIdG0Asy5c2jr6BQMfa0k5OYXAikVY6rSY4C9" +
                        "oOn-pXhiaOvW6ciCQmTerJSrLuo9StG57CmaWaSnciP4xiHdK4Uh1GuhqPcvYWncXtUdpq4Mt" +
                        "_lb3rYuRIz9lW_ZcUoBcKTvRC160FPU6D-KO80lHp-_LEhSTrRtaF6IdEyAnBR9jBlhLx0fIe_xs0";
                headers.put("Authorization", authorization);

                return headers;

            }
        };
        requestQue.add(strRequest);
    }

    private void getIdTransaction(String strResponse) throws JSONException {
        //Se envia al objeto json los datos de el primer usuario
        JSONObject obJson = new JSONObject(strResponse);
        Toast.makeText(getApplicationContext(), "Transaction ID: " + obJson.get("transactionId")
                , Toast.LENGTH_LONG).show();
    }

    public void getStatusTransaction(int idTransaction) {
        String url = "https://pay.payphonetodoesposible.com/api/Sale/" + idTransaction;
        RequestQueue requestQue = Volley.newRequestQueue(this);

        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        StatusTransaction(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Error Response:"
                        + error.getMessage(), Toast.LENGTH_LONG).show()

        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String authorization = "Bearer qI9lLZah0skaLES5R2mCgu1gEAbKwxtKFT0jACkB4KtMX1d4sHt" +
                        "PF6ejHZccBB2qyAHX0iWNwxmatSFT0rbhkvUliK31u2utv4rn3asW_Rn53O29qgWshlf92KNy" +
                        "7pVQDlY8ztkjhtB_NMxAw175aD7-qoLwMQrLVIdG0Asy5c2jr6BQMfa0k5OYXAikVY6rSY4C9" +
                        "oOn-pXhiaOvW6ciCQmTerJSrLuo9StG57CmaWaSnciP4xiHdK4Uh1GuhqPcvYWncXtUdpq4Mt" +
                        "_lb3rYuRIz9lW_ZcUoBcKTvRC160FPU6D-KO80lHp-_LEhSTrRtaF6IdEyAnBR9jBlhLx0fIe_xs0";

                headers.put("Authorization", authorization);
                return headers;
            }
        };
        requestQue.add(requestJson);
    }

    public void StatusTransaction(JSONObject objt) throws JSONException {
        try {
            System.out.println(objt.get("transactionStatus"));
        } catch (JSONException e) {
            Toast.makeText(this, "Error al cargar los datos al objeto: "
                    + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}