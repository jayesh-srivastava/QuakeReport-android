package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>
{
    public EarthquakeAdapter(@NonNull Context context,@NonNull List<Earthquake> objects)
    {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem=convertView;
        if(convertView==null)
        {
            listitem= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_listitem,parent,false);
        }
        Earthquake pos=getItem(position);

        TextView mag=(TextView) listitem.findViewById(R.id.mag);
        DecimalFormat formatter=new DecimalFormat("0.0");
        mag.setText(formatter.format(pos.getMagnitude()));

        TextView loc=(TextView) listitem.findViewById(R.id.location);
        TextView city=(TextView) listitem.findViewById(R.id.city);
        char ch=pos.getLocation().charAt(0);
        if(ch>='0' && ch<='9')
        {
            loc.setText(pos.getLocation().substring(0,(pos.getLocation().indexOf('f'))+1));
            city.setText(pos.getLocation().substring(pos.getLocation().indexOf('f')+2));
        }
        else {
            loc.setText("Near the");
            city.setText(pos.getLocation());
        }



        long timemilli=pos.getTime();
        Date object=new Date(timemilli);

        TextView date=(TextView) listitem.findViewById(R.id.date);
        date.setText(date(object));

        TextView time=(TextView) listitem.findViewById(R.id.time);
        time.setText(time(object));

        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();
        int magnitudeColor = getMagnitudeColor(pos.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);



        return listitem;
    }
    private String date(Date object)
    {
        SimpleDateFormat simpledate=new SimpleDateFormat("MMM dd, yyyy");
        return simpledate.format(object);
    }
    private String time(Date object)
    {
        SimpleDateFormat simpletime=new SimpleDateFormat("h:mm a");
        return simpletime.format(object);
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
