package chamada2.com.br.chamadablueooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    //  private ArrayList<DenteAzul> mDeviceList = new ArrayList<>();
    private BluetoothAdapter mBluetoothAdapter;
    private List<Aluno> listaDeAlunos;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;
    private int alunospresentes = 0;
    private  int aluntosfaltosos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.menu_item);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.menu_item2);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"PRESENTES: "+alunospresentes,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, AdicionarGasto.class);
//                startActivity(intent);
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"FALTOSOS: "+aluntosfaltosos,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, VerGastos.class);
//                startActivity(intent);
            }
        });

        listaDeAlunos = new ArrayList<>();
        listaDeAlunos.add(new Aluno("MATHEUS TITO", "8C:29:37:41:4E:2D"));
        listaDeAlunos.add(new Aluno("CAIO FELIPE", "E8:B4:C8:25:CB:03"));
        listaDeAlunos.add(new Aluno("GABRIEL", "EC:D0:9F:10:56:73"));
        listaDeAlunos.add(new Aluno("L VINICIUS", "F8:95:C7:0F:C6:86"));
        listaDeAlunos.add(new Aluno("J VITOR", "00:CD:FE:95:AD:C2"));
        listaDeAlunos.add(new Aluno("J PEDRO", "F0:D7:AA:22:E2:B9"));

        listaDeAlunos.add(new Aluno("GONÇALO", "E8:91:20:8F:6F:92"));
        listaDeAlunos.add(new Aluno("EVANDRO", "70:4D:7B:CD:82:46"));
        listaDeAlunos.add(new Aluno("LUCAS R", "24:DA:9B:D6:67:C5"));
        listaDeAlunos.add(new Aluno("J MARCUS", "64:DB:43:38:D3:2F"));
        listaDeAlunos.add(new Aluno("PEDRO FONE", "48:49:C7:B8:50:F9"));
        listaDeAlunos.add(new Aluno("PEDRO GEAR", "AC:EE:9E:13:EF:36"));

        listaDeAlunos.add(new Aluno("MAX", "88:79:7E:8F:54:6E"));
        listaDeAlunos.add(new Aluno("PEDRO TRETAS", "14:1A:A3:E7:AF:7E"));
        listaDeAlunos.add(new Aluno("NATASHA", "46:42:62:1F:3B:23"));//
        listaDeAlunos.add(new Aluno("NAO SEI", "7F:7B:E4:C3:40:E5"));

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (!mDeviceList.contains(device.getAddress() + " - " + device.getName())) {

                    boolean presente = false;
                    String nome = "";

                    for (Aluno a : listaDeAlunos) {

                        Log.i("LOG"," >" + a.getMac() + " " +device.getAddress() );

                        if (a.getMac().equals(device.getAddress())) {
                            presente = true;
                            nome = a.getNome();
                            mDeviceList.add(device.getAddress() + " - " + nome + " (PRESENTE)");
                            Log.i("LOG","BREAK");
                            break;
                        } else {
                            presente = false;
                        }

                        Log.i("LOG",""+presente);

                    }

                 //   Log.i("LOG",""+presente);

                    if (presente) {
                       // mDeviceList.add(device.getAddress() + " - " + nome + " (PRESENTE)");
                        alunospresentes++;
                    } else {
                        mDeviceList.add(device.getAddress() + " - " + device.getName());
                        aluntosfaltosos++;
                    }

                } else {
                    Log.i("LOG", " já foi adicionado anteriormente.");
                }

                // mDeviceList.add(device.getName() + "\n" + device.getAddress());
                //  mDeviceList.add(device.getAddress());

                Log.i("BT", device.getName() + "\n" + device.getAddress());

                listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };
}