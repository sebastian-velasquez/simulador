package co.edu.usbcali.simulador.database.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import co.edu.usbcali.simulador.database.account.Account;

/**
 * Created by eiso-10 on 2/12/18.
 */

public class AccountCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Account> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // convertView = LayoutInflater.from(context).inflate(R.layout.)
        }
        return  null;
    }
}
